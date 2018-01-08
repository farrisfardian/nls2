(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('UserCtrl', UserCtrl)
            .controller('UserModalController', UserModalController);

    /** @ngInject */
    function UserCtrl($scope, $uibModal, toastr, $log, UserService, RoleService, PegawaiService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };

        PegawaiService.cariSemua().success(function (data) {
            $scope.listPegawai = data;
        });

        RoleService.cariSemua().success(function (data) {
            $scope.listRole = data;
        });

        $scope.reloadData = function () {
            $scope.dataPage = UserService.query($scope.search, $scope.paging.currentPage, function () {
                $scope.paging.maxSize = ($scope.dataPage.size);
                $scope.paging.totalItems = $scope.dataPage.totalElements;
                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                $scope.paging.maxPage = $scope.dataPage.totalPages;
            });

        };

        $scope.reloadData();

        $scope.baru = function () {
            console.log('Baru');
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/setting/user/user-modal.html',
                controller: 'UserModalController',
                size: 'md',
                resolve: {
                    data: function () {
                        return {};
                    }
                }
            });
            modalInstance.result.then(function (selectedItem) {
                $scope.reloadData();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.edit = function (x, page, size) {
            console.log('Open modal');
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: page,
                controller: 'UserModalController',
                size: size,
                resolve: {
                    data: function () {
                        return x;
                    }
                }
            });
            modalInstance.result.then(function (selectedItem) {
                $scope.reloadData();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.uploadComplete = function (content, completed) {
            if (completed) {
                $scope.currentUser.uploadError = content.msg + "  [" + content.status + "]";
                if (content.status == "200") {
                    $scope.simpan();
                }
            }
        };

        $scope.hapus = function (x) {
            if (x.id == null) {
                return;
            }
            UserService.remove(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.users = UserService.query();
            });

        };

        $scope.isClean = function () {
            return angular.equals($scope.original, $scope.currentUser);
        };

        $scope.isUsernameAvailable = function (value) {
            if ($scope.currentUser != null && $scope.currentUser.id != null) {
                return true;
            }
            for (var i = 0; i < $scope.users.length; i++) {
                var u = $scope.users[i];
                if (u.username === value) {
                    return false;
                }
            }
            return true;
        };
    }

    function UserModalController($uibModalInstance, toastr, $scope, UserService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit User";
        console.log('edit', data);
        $scope.vm = angular.copy(data);


        $scope.simpan = function () {
            UserService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            });
        };
    }
})();

