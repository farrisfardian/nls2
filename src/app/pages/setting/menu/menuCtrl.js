(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('MenuController', MenuController)
            .controller('MenuModalController', MenuModalController);

    /** @ngInject */
    function MenuController($scope, $uibModal, $log, toastr, MenuService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0,
        };
//        MenuService.cariSemua().success(function (data) {
//            $scope.listParent = data;
//            console.log(data);
//        });
        $scope.reloadData = function () {
            $scope.dataPage = MenuService.query($scope.search, $scope.paging.currentPage - 1, function () {
                console.log('$scope.dataPage', $scope.dataPage);
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
                templateUrl: 'app/pages/setting/menu/menu-modal.html',
                controller: 'MenuModalController',
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
                controller: 'MenuModalController',
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

        $scope.hapus = function (x) {
            MenuService.hapus(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            });
        }
    }

    function MenuModalController($uibModalInstance, toastr, $scope, MenuService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Menu";
        console.log('edit', data);
        $scope.vm = angular.copy(data);
        if (data.id == undefined) {
            $scope.modalTitle = "Tambah Menu";
        }

        MenuService.cariSemuaParent().success(function (d) {
            $scope.listParent = d;
        })

        $scope.simpan = function () {
            MenuService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            })
        }
    }
})();

