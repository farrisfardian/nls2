(function() {
    'use strict';

    angular
        .module('BlurAdmin')
        .factory('LoginService', LoginService);

    LoginService.$inject = ['$uibModal', '$state'];

    function LoginService ($uibModal, $state) {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open () {
            $state.go('login');
//            if (modalInstance !== null) return;
//            modalInstance = $uibModal.open({
//                animation: true,
//                templateUrl: 'app/theme/components/login/login.html',
//                controller: 'LoginController',
//                controllerAs: 'vm'
//            });
//            modalInstance.result.then(
//                resetModal,
//                resetModal
//            );
        }
    }
})();
