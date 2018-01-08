(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi')
            .controller('NotaCtrl', NotaCtrl)
            .controller('NotaModalController', NotaModalController);

    /** @ngInject */
    function NotaCtrl($scope, $uibModal, $log, $filter, toastr, NotaService, KapalBerangkatService, EnumService, TokoService, TambahanBiayaService, KotaService) {
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
        TambahanBiayaService.cariSemua().success(function (data) {
            $scope.listTambahanBiaya = data;
        });
        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglAwal: new Date(), tglAkhir: new Date(), cari: ""};
        $scope.reloadData = function () {
            $scope.dataPage = NotaService.queryComposite($filter('date')(new Date($scope.param.tglAwal), 'yyyy-MM-dd'), $filter('date')(new Date($scope.param.tglAkhir), 'yyyy-MM-dd'), ($scope.param.cari == '' ? 'null' : $scope.param.cari), $scope.paging.currentPage - 1, function () {
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
            $scope.vm = {nomorManual: false};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Nota";
            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Nota";
            console.log('edit', x);
            NotaService.cariSatu("kode", x.id).success(function (data) {
                console.log('edit', data);
                if (data.tanggal !== null && data.tanggal !== undefined) {
                    data.tanggal = new Date(data.tanggal);
                }
                $scope.vm = angular.copy(data);
                $scope.hitungGrandTotal();
                $scope.hitungGrandTotalTambahan();
                if ($scope.vm.kapalBerangkat !== null && $scope.vm.kapalBerangkat !== undefined) {
                    $scope.deskKapalBerangkat = $scope.vm.kapalBerangkat.kapal.nama + ' Tgl ' + $filter('date')(new Date($scope.vm.kapalBerangkat.tglBerangkat), "dd/MM/yyyy");
                }
            });
        };

        $scope.hapus = function (x) {
            NotaService.hapus(x.id).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            }).error(function () {
                toastr.error('Maaf Anda tidak dapat menghapus Nota yg sudah dibuatkan Surat Jalannya!');
            });
        };
        $scope.opened = false;
        $scope.openDate = openDate;
        function openDate() {
            $scope.opened = true;
        }

        $scope.fillDetailNota = function (idTokoTujuan, idMerkTujuan, idKapalBerangkat) {
            if (idKapalBerangkat === '') {
                $scope.vm.listDetail = [];
            } else {
                NotaService.listDetailNotaTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat).success(function (data) {
                    $scope.vm.listDetail = data;
                    $scope.grandTotal = 0;
                    $scope.hitungGrandTotal();
                    console.log('$scope.vm.listDetail', $scope.vm.listDetail);
                    if ($scope.vm.minBayar === true) {
                        NotaService.listSubtotalDetailNotaTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat).success(function (data) {
                            $scope.listSubtotal = data;
                            console.log('$scope.listSubtotal ', $scope.listSubtotal);
                            for (var i = 0; i < $scope.listSubtotal.length; i++) {
                                for (var k = 0; k < $scope.vm.listDetail.length; k++) {
                                    if ($scope.listSubtotal[i].id_kapal_berangkat === $scope.vm.listDetail[k].kapalBerangkat.id && $scope.listSubtotal[i].nomor_kontainer === $scope.vm.listDetail[k].noKontainer) {
                                        $scope.vm.listDetail[k].tambahanMinBayar = $scope.vm.jmlMinBayar > $scope.listSubtotal[i].subtotal ? $scope.vm.jmlMinBayar - $scope.listSubtotal[i].subtotal : 0;
                                        break;
                                    }
                                }
                            }
                        });
                    }
                });
            }
        };

        $scope.resetMerk = function () {
            $scope.vm.merk = null;
        };
        $scope.lookupKapalBerangkatTokoMerk = function () {
            console.log('lookupKapalBerangkatTokoMerk', $scope.vm);
            console.log('tokoTujuan', $scope.vm.tokoTujuan);
            console.log('merkTujuan', $scope.vm.merkTujuan);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/lookup-kapal-berangkat-toko-merk.html',
                controller: 'lookupKapalBerangkatTokoMerkCtrl',
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
                console.log('selectedNota', sd);
                $scope.listkapalBerangkat = sd;
                if ($scope.vm.listKapalBerangkat === null || $scope.vm.listKapalBerangkat === undefined) {
                    $scope.vm.listKapalBerangkat = [];
                }
                var exist = false;
                for (var i = 0; i < $scope.listkapalBerangkat.length; i++) {
                    var exist = false;
                    for (var j = 0; j < $scope.vm.listKapalBerangkat.length; j++) {
                        if ($scope.listkapalBerangkat[i].id_kapal_berangkat === $scope.vm.listKapalBerangkat[j].kapalBerangkat.id) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        $scope.vm.listKapalBerangkat.push({
                            kapalBerangkat: {id: $scope.listkapalBerangkat[i].id_kapal_berangkat, tglBerangkat: new Date($scope.listkapalBerangkat[i].tgl_berangkat), kapal: {nama: $scope.listkapalBerangkat[i].kapal}}
                        });
                    }
                }
                $scope.refreshDataDetail();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.refreshDataDetail = function () {
            var strKapalBerangkat = $scope.susunKapalBerangkat();
            if ($scope.vm.tokoTujuan !== null && $scope.vm.tokoTujuan !== undefined && strKapalBerangkat !== '') {
                $scope.fillDetailNota($scope.vm.tokoTujuan.id, $scope.vm.merkTujuan === null || $scope.vm.merkTujuan === undefined ? 'null' : $scope.vm.merkTujuan.id, strKapalBerangkat);
            } else {
//                toastr.error('Pastikan Toko dan Kapal Berangkat diisi dengan benar');
            }
        };

        $scope.susunKapalBerangkat = function () {
            var strKapalBerangkat = '';
            var idx = 0;
            if (!($scope.vm.listKapalBerangkat === undefined || $scope.vm.listKapalBerangkat === null)) {
                for (var j = 0; j < $scope.vm.listKapalBerangkat.length; j++) {
                    if (idx > 0) {
                        strKapalBerangkat = strKapalBerangkat + ',';
                    }
                    strKapalBerangkat = strKapalBerangkat + $scope.vm.listKapalBerangkat[j].kapalBerangkat.id;
                    idx = idx + 1;
                }
            }
            return strKapalBerangkat;
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
        $scope.hitungGrandTotalTambahan = function () {
            $scope.grandTotalTambahan = 0;
            for (var i = 0; i < $scope.vm.listTambahanBiaya.length; i++) {
                $scope.grandTotalTambahan = $scope.grandTotalTambahan + ($scope.vm.listTambahanBiaya[i].harga * $scope.vm.listTambahanBiaya[i].jumlah);
            }
        };
        $scope.hitungGrandTotal = function () {
            $scope.grandTotal = 0;
            for (var i = 0; i < $scope.vm.listDetail.length; i++) {
                $scope.grandTotal = $scope.grandTotal + ($scope.vm.listDetail[i].volume * $scope.vm.listDetail[i].harga);
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
            $scope.fillDetailNota($scope.vm.tokoTujuan.id, $scope.vm.merkTujuan === null || $scope.vm.merkTujuan === undefined ? 'null' : $scope.vm.merkTujuan.id, strKapalBerangkat);
        };

        $scope.validasi = function () {
            if ($scope.vm.listKapalBerangkat == null || $scope.vm.listKapalBerangkat == undefined || $scope.vm.listKapalBerangkat.length == 0) {
                toastr.error('Kapal Berangkat tidak boleh kosong!!');
                return false;
            }
            if ($scope.vm.listDetail == null || $scope.vm.listDetail == undefined || $scope.vm.listDetail.length == 0) {
                toastr.error('Kapal Berangkat tidak boleh kosong!!');
                return false;
            } else {
                for (var i = 0; i < $scope.vm.listDetail.length; i++) {
                    if ($scope.vm.listDetail[i].harga == null) {
                        toastr.error('Kolom Harga tidak boleh kosong!! Pastikan Setting Harga sudah benar');
                        break;
                        return false;
                    }
                }
            }
            return true;
        };

        $scope.simpan = function () {
            if ($scope.validasi()) {
                $scope.vm.totalTagihan = $scope.grandTotal + $scope.grandTotalTambahan;
                NotaService.simpan($scope.vm, $scope.ori).success(function (d) {
                    toastr.success('Simpan data sukses!');
                    $scope.clear();
                });
            }
        };

        $scope.cetak = function (c, tipe) {
            var link = 'api/report/get-nota.' + tipe + '?idNota=' + c.id ;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        };

        $scope.$watch('vm.minBayar', function () {
            $scope.refreshDataDetail();
        });

    }

    function NotaModalController($uibModalInstance, toastr, $scope, NotaService, KotaService, KontainerService, KapalBerangkatService, EmklService, SatuanKirimService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Nota";
        console.log('edit', data);
        $scope.vm = angular.copy(data);

        if ($scope.vm.id === undefined || $scope.vm.id === null) {
            $scope.modalTitle = "Tambah Nota";
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
            NotaService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            });
        }
    }
})();

