(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi')
            .controller('RekapPembayaranCtrl', RekapPembayaranCtrl)
            ;

    /** @ngInject */
    function RekapPembayaranCtrl($scope, $uibModal, $log, $filter, toastr, NotaService, EnumService, TokoService, KotaService, KapalService) {
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
        KapalService.cariSemua().success(function (data) {
            $scope.listKapal = data;
        });
        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglAwal: new Date(), tglAkhir: new Date(), cari: "", tokoTujuan: null, merkTujuan: null, kapal: null, kota: null, kapalBerangkat: null};
        $scope.resetKapalBerangkat = function () {
            $scope.param.kapalBerangkat = null;
            $scope.deskKapalBerangkat = '';
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
                    $scope.param.tokoTujuan = data;
                    $scope.param.merkTujuan = null;
                    $scope.param.kota = data.kota;
                    $scope.listMerk = $scope.param.tokoTujuan.listMerk;
                    toastr.success("Ambil toko sukses");
                    console.log('toko', $scope.param.tokoTujuan);
                }).error(function (e) {
                    toastr.error("Ambil toko gagal");
                });
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.lookupKapalBerangkat = function () {
            console.log('lookupKapalBerangkat', $scope.vm);
            console.log('kota', $scope.param.kota);
            if ($scope.param.kota === null) {
                toastr.error('Pilih Kota Tujuan dahulu!!');
            } else {

                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'app/pages/tpl/lookup-kapal-berangkat.html',
                    controller: 'lookupKapalBerangkatCtrl',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return {
                                kota: $scope.param.kota,
                            }
                        },
                    }
                });
                modalInstance.result.then(function (sd) {
                    console.log('selectedStuffing', sd);
                    $scope.param.kapalBerangkat = sd;
                    $scope.deskKapalBerangkat = $scope.param.kapalBerangkat.kapal.nama + ' Tgl ' + $filter('date')(new Date(sd.tglBerangkat), "dd/MM/yyyy");
                    console.log('param.kapalBerangkat', $scope.param.kapalBerangkat);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }
        };

        $scope.cetak = function (tipe) {
            var link = 'api/report/get-rekap-pembayaran.' + tipe
                    + '?'
                    + "tglAwal=" + $filter('date')(new Date($scope.param.tglAwal), 'yyyy-MM-dd')
                    + "&tglAkhir=" + $filter('date')(new Date($scope.param.tglAkhir), 'yyyy-MM-dd')
                    + "&idKapalBerangkat=" + ($scope.param.kapalBerangkat === null ? 'null' : $scope.param.kapalBerangkat.id);
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        };

    }

})();

