(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi')
            .controller('PembayaranCtrl', PembayaranCtrl)
            .controller('PembayaranModalController', PembayaranModalController);

    /** @ngInject */
    function PembayaranCtrl($scope, $uibModal, $log, $filter, $stateParams, toastr, PembayaranService, EnumService, TokoService, KotaService, NotaService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.deskKapalBerangkat = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.listKapalBerangkat = [];
        $scope.listMerk = [];
        EnumService.getUkuranKontainer().success(function (data) {
            $scope.listUkuranKontainer = data;
        });
        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglAwal: new Date(), tglAkhir: new Date(), cari: ""};
        $scope.reloadData = function () {
            $scope.dataPage = PembayaranService.queryComposite($filter('date')(new Date($scope.param.tglAwal), 'yyyy-MM-dd'), $filter('date')(new Date($scope.param.tglAkhir), 'yyyy-MM-dd'), ($scope.param.cari == '' ? 'null' : $scope.param.cari), $scope.paging.currentPage - 1, function () {
                $scope.paging.maxSize = ($scope.dataPage.size);
                $scope.paging.totalItems = $scope.dataPage.totalElements;
                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                $scope.paging.maxPage = $scope.dataPage.totalPages;
            });
        };

        $scope.clear = function () {
            $scope.modalTitle = "";
            $scope.vm = {tokoTujuan: null, merkTujuan: null, nomorManual: false};
            $scope.ori = {};
            $scope.grandTotal = 0;
            $scope.grandTotalTambahan = 0;
            $scope.reloadData();
        };

        $scope.clear();

        $scope.baru = function () {
            $scope.vm = {nomorManual: false, listDetail: []};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Pembayaran";
            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Pembayaran";
            console.log('edit', x);
            PembayaranService.cariSatu("kode", x.id).success(function (data) {
                console.log('edit', data);
                if (data.tanggal !== null && data.tanggal !== undefined) {
                    data.tanggal = new Date(data.tanggal);
                }
                $scope.vm = angular.copy(data);
                $scope.hitungGrandTotal();
                if ($scope.vm.kapalBerangkat !== null && $scope.vm.kapalBerangkat !== undefined) {
                    $scope.deskKapalBerangkat = $scope.vm.kapalBerangkat.kapal.nama + ' Tgl ' + $filter('date')(new Date($scope.vm.kapalBerangkat.tglBerangkat), "dd/MM/yyyy");
                }
            });
        };

        $scope.hapus = function (x) {
            PembayaranService.hapus(x.id).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            }).error(function () {
                toastr.error('Maaf Anda tidak dapat menghapus Pembayaran yg sudah dibuatkan Surat Jalannya!');
            });
        };
        $scope.opened = false;
        $scope.openDate = openDate;
        function openDate() {
            $scope.opened = true;
        }

        $scope.resetMerk = function () {
            $scope.vm.merk = null;
        };
        $scope.lookupTagihanTerbayarTokoMerk = function () {
            console.log('lookupTagihanTerbayarTokoMerk', $scope.vm);
            console.log('tokoTujuan', $scope.vm.tokoTujuan);
            console.log('merkTujuan', $scope.vm.merkTujuan);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/lookup-tagihan-terbayar.html',
                controller: 'lookupTagihanTerbayarCtrl',
                size: 'lg',
                resolve: {
                    param: function () {
                        return {
                            idToko: $scope.vm.tokoTujuan.id,
                            idMerk: $scope.vm.merkTujuan === null || $scope.vm.merkTujuan === undefined ? 'null' : $scope.vm.merkTujuan.id,
                        }
                    },
                }
            });
            modalInstance.result.then(function (sd) {
                console.log('selectedPembayaran', sd);
                $scope.listDetail = sd;
                if ($scope.vm.listDetail === null || $scope.vm.listDetail === undefined) {
                    $scope.vm.listDetail = [];
                }
                var exist = false;
                for (var i = 0; i < $scope.listDetail.length; i++) {
                    var exist = false;
                    for (var j = 0; j < $scope.vm.listDetail.length; j++) {
                        if ($scope.listDetail[i].id === $scope.vm.listDetail[j].nota.id) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        $scope.vm.listDetail.push($scope.listDetail[i]);
                    }
                }
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.lookupToko = function () {
            console.log('lookupToko', $scope.vm);
            console.log('kota', $scope.kota);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/lookup-toko.html',
                controller: 'LookupTokoCtrl',
                size: 'md',
                resolve: {
                    param: function () {
                        return {
                            cari: '',
                        }
                    },
                }
            });
            modalInstance.result.then(function (sd) {
                console.log('selectedToko', sd);
                TokoService.cariSatu("kode", sd.id).success(function (data) {
                    $scope.vm.tokoTujuan = data;
                    $scope.vm.merkTujuan = null;
                    $scope.listkapalBerangkat = null;
                    $scope.listMerk = $scope.vm.tokoTujuan.listMerk;
                    toastr.success("Ambil toko sukses");
                    console.log('toko', $scope.vm.tokoTujuan);
                }).error(function (e) {
                    toastr.error("Ambil toko gagal");
                });
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.hitungGrandTotal = function () {
            $scope.grandTotal = 0;
            for (var i = 0; i < $scope.vm.listDetail.length; i++) {
                $scope.grandTotal = $scope.grandTotal + ($scope.vm.listDetail[i].bayar);
            }
        };
        $scope.baruTambahanBiaya = function () {
            if ($scope.vm.listTambahanBiaya === undefined || $scope.vm.listDetail === null) {
                $scope.vm.listTambahanBiaya = [];
            }
            $scope.vm.listTambahanBiaya.push(
                    {
                        tambahanBiaya: null,
                        harga: 0,
                        jumlah: 0
                    }
            );
            console.log('Detail Baru');
        };
        $scope.hapusTambahanBiaya = function (idx) {
            $scope.vm.listTambahanBiaya.splice(idx, 1);
        };

        $scope.hapusKapalBerangkat = function (idx) {
            $scope.vm.listKapalBerangkat.splice(idx, 1);
            var strKapalBerangkat = $scope.susunKapalBerangkat();
            $scope.fillDetailPembayaran($scope.vm.tokoTujuan.id, $scope.vm.merkTujuan === null || $scope.vm.merkTujuan === undefined ? 'null' : $scope.vm.merkTujuan.id, strKapalBerangkat);
        };

        $scope.validasi = function () {
            if ($scope.vm.listDetail == null || $scope.vm.listDetail == undefined || $scope.vm.listDetail.length == 0) {
                toastr.error('Kapal Berangkat tidak boleh kosong!!');
                return false;
            } else {
                for (var i = 0; i < $scope.vm.listDetail.length; i++) {
                    if ($scope.vm.listDetail[i].bayar == null) {
                        toastr.error('Kolom Harga tidak boleh kosong!! Pastikan Setting Harga sudah benar');
                        break;
                        return false;
                    } else if ($scope.vm.listDetail[i].bayar > ($scope.vm.listDetail[i].tagihan - $scope.vm.listDetail[i].terbayar)) {
                        $scope.vm.listDetail[i].bayar = $scope.vm.listDetail[i].tagihan - $scope.vm.listDetail[i].terbayar;
                    }
                }
            }
            return true;
        };

        $scope.simpan = function () {
            if ($scope.validasi()) {
                $scope.vm.totalBayar = $scope.grandTotal;
                PembayaranService.simpan($scope.vm, $scope.ori).success(function (d) {
                    toastr.success('Simpan data sukses!');
                    $scope.clear();
                });
            }
        };

        $scope.cetak = function (c, tipe, tampilkanKapalBerangkat) {
            var link = 'api/report/get-pembayaran-nota.' + tipe + '?idPembayaranNota=' + c.id + "&tampilkanKapalBerangkat=" + tampilkanKapalBerangkat;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        };

        $scope.initForm = function (idToko, idMerk, idNotas) {
            TokoService.cariSatu("kode", idToko).success(function (data) {
                $scope.vm.tokoTujuan = data;
                $scope.listkapalBerangkat = [];
                $scope.listMerk = $scope.vm.tokoTujuan.listMerk;
                for (var i = 0; i < $scope.listMerk.length; i++) {
                    if ($scope.listMerk[i].id === parseInt(idMerk)) {
                        $scope.vm.merkTujuan = $scope.listMerk[i];
                    }
                }
                toastr.success("Ambil toko sukses");
                console.log('toko', $scope.vm.tokoTujuan);
                NotaService.getTagihanTerbayarMulti(idToko, idMerk, "Belum Lunas", idNotas).success(function (data) {
                    $scope.listDetail = data;
                    $scope.vm.listDetail = [];
                    for (var i = 0; i < $scope.listDetail.length; i++) {
                        $scope.vm.listDetail.push(
                                {
                                    tagihan: $scope.listDetail[i].tagihan,
                                    terbayar: $scope.listDetail[i].terbayar,
                                    nota: {id: $scope.listDetail[i].id, nomorInvoice: $scope.listDetail[i].nomor, tanggal: $scope.listDetail[i].tanggal},
                                }
                        );
                    }
                    
                });
            }).error(function (e) {
                toastr.error("Ambil toko gagal");
            });
        };
        console.log('data edit', $stateParams);
        if ($stateParams.idToko === null || $stateParams.idToko === undefined || $stateParams.idToko === 0 || $stateParams.idToko === '') {
            $scope.clear();
        } else {
            $scope.baru();
            $scope.initForm($stateParams.idToko, $stateParams.idMerk, $stateParams.idNotas);
        }

    }

    function PembayaranModalController($uibModalInstance, toastr, $scope, PembayaranService, KotaService, KontainerService, KapalBerangkatService, EmklService, SatuanKirimService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Pembayaran";
        console.log('edit', data);
        $scope.vm = angular.copy(data);

        if ($scope.vm.id === undefined || $scope.vm.id === null) {
            $scope.modalTitle = "Tambah Pembayaran";
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
        KapalBerangkatService.cariSemua().success(function (data) {
            $scope.listKapalBerangkat = data;
            console.log('$scope.listKapalBerangkat', $scope.listKapalBerangkat);
        });
        EmklService.cariSemua().success(function (data) {
            $scope.listEmkl = data;
            console.log('$scope.listEmkl', $scope.listEmkl);
        });
        KontainerService.cariSemua().success(function (data) {
            $scope.listKontainer = data;
            console.log('$scope.listKontainer', $scope.listKontainer);
        });
        SatuanKirimService.cariSemua().success(function (data) {
            $scope.listSatuanKirim = data;
            console.log('$scope.listSatuanKirim', $scope.listSatuanKirim);
        });

        $scope.simpan = function () {
            PembayaranService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            });
        }
    }
})();

