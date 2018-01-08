(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('lookupKapalBerangkatTokoMerkCtrl', LookupKapalBerangkatTokoMerkCtrl)
    function LookupKapalBerangkatTokoMerkCtrl($scope, param, KapalBerangkatService, $timeout, $uibModalInstance) {
        $scope.selected = {};
        $scope.valid = false;
        console.log('LookupKapalBerangkatTokoMerkCtrl --> param', param);
        $scope.listData = [];
        $scope.listSelected = [];
        $scope.reloadData = function () {
            KapalBerangkatService.filterByTokoMerkTujuan(param.idToko, param.idMerk).success(function (d) {
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
        };

        $scope.modalSelected = function () {
            $uibModalInstance.close($scope.selected);
        };

        $scope.updateStatus = function () {
            $scope.valid = false;
            for (var i = 0; i < $scope.listData.length; i++) {
                if ($scope.listData[i].selected === true) {
                    $scope.valid = true;
                    break;
                }
            }
        };
        $scope.tampilkan = function () {
            alert($scope.query);
        };

        $scope.simpan = function () {
            $scope.listSelected = [];
            for (var i = 0; i < $scope.listData.length; i++) {
                if ($scope.listData[i].selected === true) {
                    $scope.listSelected.push($scope.listData[i]);
                }
            }
            $scope.selected = $scope.listSelected;
            $scope.modalSelected();
        };
    }
})();