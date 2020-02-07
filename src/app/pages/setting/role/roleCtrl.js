(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('RoleController', RoleController)
            .controller('RoleModalController', RoleModalController);

    /** @ngInject */
    function RoleController($scope, $uibModal, $log, toastr, RoleService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0,
        };
        RoleService.cariSemua().success(function (data) {
            $scope.listParent = data;
            console.log(data);
        });
        $scope.reloadData = function () {
            $scope.dataPage = RoleService.query($scope.search, $scope.paging.currentPage - 1, function () {
//                console.log('$scope.dataPage', $scope.dataPage);
                $scope.paging.maxSize = ($scope.dataPage.size);
                $scope.paging.totalItems = $scope.dataPage.totalElements;
                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                $scope.paging.maxPage = $scope.dataPage.totalPages;

            });
        };

        $scope.clear = function () {
            $scope.modalTitle = "";
            $scope.vm = {menuSet: []};
            $scope.ori = {menuSet: []};
            $scope.reloadData();
        };

        $scope.clear();

        $scope.baru = function () {
            $scope.modalTitle = "Tambah Role";
            $scope.vm = {menuSet: []};
            $scope.ori = {menuSet: []};
            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Role";
            RoleService.cariSatu("kode", x.id).success(function (data) {
                console.log('edit', data);
                $scope.vm = angular.copy(data);
            });
        };

        $scope.simpan = function () {
            RoleService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            });
        };

        $scope.hapus = function (x) {
            RoleService.hapus(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            });
        };

        $scope.hapusMenu = function (i) {
            $scope.vm.menuSet.splice(i, 1);
        };

        $scope.lookupMenu = function () {
            console.log('lookupMenu', $scope.vm);
            var ids = "null";
            if ($scope.vm.menuSet.length > 0) {
                ids = "";
                for (var i = 0; i < $scope.vm.menuSet.length; i++) {
                    if (i > 0) {
                        ids = ids + ",";
                    }
                    ids = ids + $scope.vm.menuSet[i].menu.id;
                }
            }
            console.log('ids', ids);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/lookup-menu.html',
                controller: 'lookupMenuCtrl',
                size: 'lg',
                resolve: {
                    param: function () {
                        return {
                            mode: 'not-in',
                            ids: ids,
                        }
                    },
                }
            });
            modalInstance.result.then(function (sd) {
                console.log('selectedMenus', sd);
                for (var i = 0; i < sd.length; i++) {
                    $scope.vm.menuSet.push({menu: sd[i]});
                }
                console.log('vm.jadwal', $scope.vm.menuSet);
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
    }

    function RoleModalController($uibModalInstance, toastr, $scope, RoleService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Role";
        console.log('edit', data);
        $scope.vm = angular.copy(data);
        if (data.id == undefined) {
            $scope.modalTitle = "Tambah Role";
        }

        RoleService.cariSemuaParent().success(function (d) {
            $scope.listParent = d;
        })

        $scope.simpan = function () {
            RoleService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            })
        }
    }
})();

