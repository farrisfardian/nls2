(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('DooringCtrl', DooringCtrl)
            .controller('DooringModalController', DooringModalController);

    /** @ngInject */
    function DooringCtrl($scope, $uibModal, $log, $filter, toastr, DooringService, KotaService, PelayaranService, EnumService, SatuanKirimService, KategoriBarangService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.deskKapalBerangkat = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglBerlaku: new Date(), kotaAsal: null, kotaTujuan: null, pelayaran: null};
        $scope.reloadData = function () {
            $scope.dataPage = DooringService.queryComposite(
                    $scope.param.kotaAsal === null ? 'null' : $scope.param.kotaAsal.id,
                    $scope.param.kotaTujuan === null ? 'null' : $scope.param.kotaTujuan.id,
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
//            $scope.dataPage = DooringService.query($scope.search, $scope.paging.currentPage - 1, function () {
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
            $scope.vm = {listDetail: [],tglBerlaku:new Date()};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Dooring";
            console.log('Baru');
        };
        
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
        $scope.update = function (x) {
            $scope.vm = {};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Dooring";
            DooringService.cariSatu("kode", x.id).success(function (data) {
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
            $scope.modalTitle = "Edit Setting Dooring";
            console.log('edit', x);
            DooringService.cariSatu("kode", x.id).success(function (data) {
                if (data.tglBerlaku !== null && data.tglBerlaku !== undefined) {
                    data.tglBerlaku = new Date(data.tglBerlaku);
                }
                $scope.vm = angular.copy(data);
            });
        };

        $scope.hapus = function (x) {
            DooringService.hapus(x.id).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            }).error(function () {
                toastr.error('Maaf Anda tidak dapat menghapus Setting Dooring ini!');
            });
        };
        $scope.opened = false;
        $scope.openDate = openDate;
        function openDate() {
            $scope.opened = true;
        }
        $scope.resetKapalBerangkat = function () {
            $scope.vm.kapalBerangkat = null;
            $scope.deskKapalBerangkat = '';
        };

        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
            console.log('$scope.listKota', $scope.listKota);
        });
        KategoriBarangService.cariSemua().success(function (data) {
            $scope.listKategoriBarang = data;
            console.log('$scope.listKategoriBarang', $scope.listKategoriBarang);
        });
        PelayaranService.cariSemua().success(function (data) {
            $scope.listPelayaran = data;
            console.log('$scope.listPelayaran', $scope.listPelayaran);
        });
        EnumService.getJenisStuffing().success(function (data) {
            $scope.listJenisStuffing = data;
            console.log('$scope.listJenisStuffing', $scope.listJenisStuffing);
        });
        EnumService.getUkuranKontainer().success(function (data) {
            $scope.listUkuranKontainer = data;
            console.log('$scope.listUkuranKontainer', $scope.listUkuranKontainer);
        });
        SatuanKirimService.cariSemua().success(function (data) {
            $scope.listSatuanKirim = data;
            console.log('$scope.listSatuanKirim', $scope.listSatuanKirim);
        });

        $scope.simpan = function () {
            DooringService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            }).error(function (e) {
                toastr.error('Gagal simpan Setting Dooring! Pastikan tanggal, asal & tujuan tidak sama persis dengan setting yang lalu.');
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

    function DooringModalController($uibModalInstance, toastr, $scope, DooringService, KotaService, PelayaranService, KapalService, EnumService, SatuanKirimService, JenisItemService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Setting Dooring";
        console.log('edit', data);
        $scope.vm = angular.copy(data);

        if ($scope.vm.id === undefined || $scope.vm.id === null) {
            $scope.modalTitle = "Tambah Setting Dooring";
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
        EnumService.cariSemua().success(function (data) {
            $scope.listJenisStufing = data;
            console.log('$scope.listJenisStufing', $scope.listJenisStufing);
        });
        PelayaranService.cariSemua().success(function (data) {
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
            DooringService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            });
        }
    }
})();

