(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('LookupKategoriHargaCtrl', LookupKategoriHargaCtrl)
    function LookupKategoriHargaCtrl($scope, $uibModalInstance, KategoriHargaService, $timeout) {
        $scope.selected = {};
        $scope.listData = [];
        $scope.cari = '';
        $scope.reloadData = function () {
            KategoriHargaService.lookup($scope.cari === '' ? 'null' : $scope.cari).success(function (d) {
                console.log('data', d);
                $scope.listData = d;
                $timeout(function () {
                    $('#cari').focus();
                }, 500);
            });
        };
        $scope.reloadData();

        $scope.totalDisplayed = 12;

        $scope.loadMore = function () {
            $scope.totalDisplayed += 10;
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }

        $scope.modalSelected = function () {
            $uibModalInstance.close($scope.selected);
        };

        $scope.tampilkan = function () {
            alert($scope.query);
        }

        $scope.pilih = function (x) {
            $scope.selected = x;
            $scope.modalSelected();
        }
    }
})();