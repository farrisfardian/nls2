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
        $scope.param = {tglAwal: new Date(), tglAkhir: new Date(), cari: "", tokoTujuan: null, merkTujuan: null, kapal: null, kota: null};
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

        $scope.cetak = function (tipe) {
            var link = 'api/report/get-rekap-pembayaran.' + tipe
                    + '?'
                    + "tglAwal=" + $filter('date')(new Date($scope.param.tglAwal), 'yyyy-MM-dd')
                    + "&tglAkhir=" + $filter('date')(new Date($scope.param.tglAkhir), 'yyyy-MM-dd');
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        };

    }

})();

