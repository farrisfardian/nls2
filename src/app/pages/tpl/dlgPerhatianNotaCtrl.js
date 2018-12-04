(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('DlgPerhatianNotaCtrl', DlgPerhatianNotaCtrl)
    function DlgPerhatianNotaCtrl($scope, $timeout, $uibModalInstance) {
        $scope.selected = {};
        $scope.listData = [
            ''
        ];
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        }

        $scope.modalSelected = function () {
            $uibModalInstance.close($scope.selected);
        };

    }
})();