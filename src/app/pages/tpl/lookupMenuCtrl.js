(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('lookupMenuCtrl', LookupMenuCtrl)
    function LookupMenuCtrl($scope, param, MenuService, $timeout, $uibModalInstance) {
        $scope.selected = {};
        $scope.valid = false;
        $scope.idx = 1;
        console.log('LookupMenuCtrl --> param', param);
        $scope.listData = [];
        $scope.listSelected = [];
        $scope.reloadData = function () {
            if (param.mode == 'not-in') {
                MenuService.findByIdNotIn(param.ids).success(function (d) {
                    console.log('data', d);
                    $scope.listData = d;
                    $timeout(function () {
                        $('#cari').focus();
                    }, 500);
                });
            } else if (param.mode == 'all') {
                MenuService.cariSemua().success(function (d) {
                    console.log('data', d);
                    $scope.listData = d;
                    $timeout(function () {
                        $('#cari').focus();
                    }, 500);
                });
            }
        };

        $scope.reloadData();

        $scope.totalDisplayed = 12;

        $scope.loadMore = function () {
            $scope.totalDisplayed += 10;
        };
        $scope.toggleAll = function () {
            for (var i = 0; i < $scope.listData.length; i++) {
                $scope.listData[i].selected = $scope.allSelected;
            }
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