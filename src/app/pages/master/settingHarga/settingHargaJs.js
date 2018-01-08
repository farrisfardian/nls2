(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('SettingHargaCtrl', SettingHargaCtrl)
            .controller('SettingHargaModalController', SettingHargaModalController);

    /** @ngInject */
    function SettingHargaCtrl($scope, $uibModal, $log, $filter, toastr, SettingHargaService, KotaService, TokoService, KondisiService, SatuanKirimService, JenisItemService, KategoriHargaService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.deskKapalBerangkat = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglBerlaku: new Date(), kota: null, toko: null, kondisi: null};
        $scope.reloadData = function () {
            $scope.dataPage = SettingHargaService.queryComposite(
                    $scope.param.kota === null ? 'null' : $scope.param.kota.id,
                    $scope.param.toko === null ? 'null' : $scope.param.toko.id,
                    $scope.param.kondisi === null ? 'null' : $scope.param.kondisi.id,
                    $filter('date')(new Date($scope.param.tglBerlaku), 'yyyy-MM-dd'),
                    $scope.paging.currentPage - 1, function () {
                        console.log('$scope.dataPage', $scope.dataPage);
                        $scope.paging.maxSize = ($scope.dataPage.size);
                        $scope.paging.totalItems = $scope.dataPage.totalElements;
                        $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                        $scope.paging.maxPage = $scope.dataPage.totalPages;
                        for (var i = 0; i < $scope.dataPage.content.length; i++) {
                            var arr = $scope.dataPage.content[i].detail !== null && $scope.dataPage.content[i].detail !== undefined && (typeof $scope.dataPage.content[i].detail === 'string' || $scope.dataPage.content[i].detail instanceof String) ? $scope.dataPage.content[i].detail.split(";") : [];
//                            console.log('arr', arr);
                            $scope.dataPage.content[i].detail = []
                            for (var j = 0; j < arr.length; j++) {
                                $scope.dataPage.content[i].detail.push(
                                        {
                                            id: j + 1,
                                            desc: arr[j]
                                        })
                            }
                        }
                    });
//            $scope.dataPage = SettingHargaService.query($scope.search, $scope.paging.currentPage - 1, function () {
//                $scope.paging.maxSize = ($scope.dataPage.size);
//                $scope.paging.totalItems = $scope.dataPage.totalElements;
//                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
//                $scope.paging.maxPage = $scope.dataPage.totalPages;
//            });
        };

        $scope.clear = function () {
            $scope.modalTitle = "";
            $scope.vm = {};
            $scope.ori = {};
            $scope.reloadData();
        };

        $scope.clear();

        $scope.baru = function () {
            $scope.vm = {listDetail: []};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Harga";
            console.log('Baru');
        };

        $scope.update = function (x) {
            $scope.vm = {};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Harga";
            SettingHargaService.cariSatu("kode", x.id).success(function (data) {
                $scope.vm = angular.copy(data);
                $scope.vm.id = null;
                $scope.vm.tglBerlaku = null;
                for (var i = 0; i < $scope.vm.listDetail.length; i++) {
                    $scope.vm.listDetail[i].id = null;
                    $scope.vm.listDetail[i].settingHarga = null;
                }
            });

            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Setting Harga";
            console.log('edit', x);
            SettingHargaService.cariSatu("kode", x.id).success(function (data) {
                if (data.tglBerlaku !== null && data.tglBerlaku !== undefined) {
                    data.tglBerlaku = new Date(data.tglBerlaku);
                }
                $scope.vm = angular.copy(data);
            });
        };

        $scope.hapus = function (x) {
            SettingHargaService.hapus(x.id).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            }).error(function () {
                toastr.error('Maaf Anda tidak dapat menghapus SettingHarga yg sudah dibuatkan Surat Jalannya!');
            });
        };
        $scope.opened = false;
        $scope.openDate = openDate;
        function openDate() {
            $scope.opened = true;
        }
        $scope.baruDetail = function () {
            if ($scope.vm.listDetail === undefined || $scope.vm.listDetail === null) {
                $scope.vm.listDetail = [];
            }
            $scope.vm.listDetail.push(
                    {
                        kategoriHarga: null,
                        harga: 0
                    }
            );
            console.log('Detail Baru');
        };
        $scope.hapusDetail = function (idx) {
            $scope.vm.listDetail.splice(idx, 1);
        };

        $scope.resetKapalBerangkat = function () {
            $scope.vm.kapalBerangkat = null;
            $scope.deskKapalBerangkat = '';
        };
        $scope.lookupToko = function (mode) {
            console.log('lookupToko', $scope.vm);
            console.log('kota', 0);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/lookup-toko.html',
                controller: 'LookupTokoCtrl',
                size: 'lg',
                resolve: {
                    param: function () {
                        return {
                            cari: '',
                        }
                    },
                }
            });
            modalInstance.result.then(function (sd) {
                TokoService.cariSatu("kode", sd.id).success(function (data) {
                    if (mode === 'list') {
                        $scope.param.toko = data;
                        $scope.param.merk = null;
                        console.log('selectedToko', $scope.param.toko);
                    } else if (mode === 'save') {
                        $scope.vm.toko = data;
                        $scope.vm.kotaTujuan = $scope.vm.toko.kota;
                        console.log('selectedToko', $scope.vm.toko);
                    }
                });
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
            console.log('$scope.listKota', $scope.listKota);
        });
        KategoriHargaService.cariSemua().success(function (data) {
            $scope.listKategoriHarga = data;
            console.log('$scope.listKategoriHarga', $scope.listKategoriHarga);
        });
        KondisiService.cariSemua().success(function (data) {
            $scope.listKondisi = data;
            console.log('$scope.listKondisi', $scope.listKondisi);
        });
//        SatuanKirimService.cariSemua().success(function (data) {
//            $scope.listSatuanKirim = data;
//            console.log('$scope.listSatuanKirim', $scope.listSatuanKirim);
//        });
        JenisItemService.cariSemua().success(function (data) {
            $scope.listJenisItem = data;
            console.log('$scope.listJenisItem', $scope.listJenisItem);
        });

        $scope.simpan = function () {
            SettingHargaService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            }).error(function(e){
                toastr.error('Gagal simpan setting harga! Pastikan tanggal, toko, kondisi, asal & tujuan tidak sama persis dengan setting yang lalu.');
            });
        };

        $scope.cetak = function (c, ex, tipe) {
            var link = 'api/report/per-stuffing.' + tipe + '?id=' + c.id + '&ex=' + ex;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        }
    }

    function SettingHargaModalController($uibModalInstance, toastr, $scope, SettingHargaService, KotaService, TokoService, KapalService, KondisiService, SatuanKirimService, JenisItemService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Setting Harga";
        console.log('edit', data);
        $scope.vm = angular.copy(data);

        if ($scope.vm.id === undefined || $scope.vm.id === null) {
            $scope.modalTitle = "Tambah Setting Harga";
        }

        $scope.opened = false;
        $scope.dateOptions = {
            showWeeks: false
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.openDate = openDate;
        function openDate() {
            $scope.opened = true;
        }

        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
            console.log('$scope.listKota', $scope.listKota);
        });
        KapalService.cariSemua().success(function (data) {
            $scope.listKapalBerangkat = data;
            console.log('$scope.listKapalBerangkat', $scope.listKapalBerangkat);
        });
        KondisiService.cariSemua().success(function (data) {
            $scope.listKondisi = data;
            console.log('$scope.listKondisi', $scope.listKondisi);
        });
        TokoService.cariSemua().success(function (data) {
            $scope.listToko = data;
            console.log('$scope.listToko', $scope.listToko);
        });
        SatuanKirimService.cariSemua().success(function (data) {
            $scope.listSatuanKirim = data;
            console.log('$scope.listSatuanKirim', $scope.listSatuanKirim);
        });
        JenisItemService.cariSemua().success(function (data) {
            $scope.listJenisItem = data;
            console.log('$scope.listJenisItem', $scope.listJenisItem);
        });

        $scope.simpan = function () {
            SettingHargaService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            });
        }
    }
})();

