(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('ChPassController', ChPassController);

    /** @ngInject */
    function ChPassController($scope, $uibModal, $log, $window, toastr, UserService) {
        $scope.userinfo = {};
        $scope.currentUser = {};

        UserService.currentUser().success(function (data) {
            $scope.userinfo = data;
        });
        $scope.oldPasswordNotValid = false;

        $scope.update = function () {
            UserService.cekPassword($scope.userinfo.user, $scope.currentUser.oldPass).success(function (valid) {
                if (valid === 'false') {
//                        bootbox.alert('Password lama tidak valid!');
                    toastr.success('Password lama tidak valid!');
//                            angular.element('.currPassword').trigger('focus');
//                        $("oldPass").trigger("focus");
                    $('#oldPass').focus();
//                            document.getElementById("formEdit.currPassword").focus();
                    return;
                } else {
                    if ($scope.currentUser.newPass != $scope.currentUser.confirmPass) {
                    } else {
                        UserService.changePassword($scope.userinfo.user, $scope.currentUser.newPass).success(function () {
                            toastr.success('Konfirmasi password tidak sesuai!');
                            $window.history.back();
                        });

                    }
                }


            });
        };

        $scope.batal = function () {
            $window.history.back();
        }
    }
})();

