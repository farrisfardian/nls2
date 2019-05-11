(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .controller('LaporanPricelistPelayaranCtrl', LaporanPricelistPelayaranCtrl)
    function LaporanPricelistPelayaranCtrl($scope, $http, $uibModal, $log, toastr, KotaService, EnumService) {
        $scope.param = {kota: {id: 7,
                kodeNota: "SBY",
                masukPricelist: null,
                nama: "SURABAYA",
                urutanPricelist: null}
            , ukuranKontainer: null};
        $scope.listSelectedToko = [];
        $scope.opened = false;
        $scope.format = 'dd-MM-yyyy';
        $scope.tgl1 = new Date();
        $scope.options = {
            showWeeks: false
        };

        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        EnumService.getUkuranKontainer().success(function (data) {
            $scope.listUkuranKontainer = data;
            console.log('$scope.listUkuranKontainer', $scope.listUkuranKontainer);
        });
        $scope.cetak = function (tipe) {
            console.log('ukuranKontainer', $scope.param.ukuranKontainer);
            console.log('$scope.kota', $scope.param.kota);
//            var idKotaAsal = angular.isDefined($scope.param.kota) && angular.isDefined($scope.param.kota) && $scope.param.kota != null && $scope.param.kota.id != null ? $scope.param.kota.id : 7;
            var link = 'api/report/pricelist-pelayaran.' + tipe + '?idKotaAsal=' + $scope.param.kota.id + '&ukuranKontainer=' + $scope.param.ukuranKontainer+'&namaKotaAsal='+$scope.param.kota.nama;
            if (tipe == 'pdf') {
                window.open(link, '_blank', 'width=1024, height=768');
            } else if (tipe == 'xlsx') {
                location.href = link;
            }
        };
    }
})();