(function() {
    'use strict';

    angular
        .module('BlurAdmin.theme.components')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'RoleService'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, RoleService) {
        var vm = this;

        vm.authorities = ['ROLE_ADMIN', 'ROLE_PL', 'ROLE_MANAJEMEN', 'ROLE_KEUANGAN', 'ROLE_EMKL'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;

        RoleService.cariSemua().success(function (data) {
            vm.listRole = data;
        });


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
