(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('UgMdHtmlCtrl', UgMdHtmlCtrl);

    /** @ngInject */
    function UgMdHtmlCtrl($scope, $uibModal, $log, toastr) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.modalTitle = "User Guide";
        
    }

})();

