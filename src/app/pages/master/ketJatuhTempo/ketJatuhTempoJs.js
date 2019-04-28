(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('KetJatuhTempoCtrl', KetJatuhTempoCtrl)
            .controller('KetJatuhTempoModalController', KetJatuhTempoModalController);

    /** @ngInject */
    function KetJatuhTempoCtrl($scope, $uibModal, toastr, $log, KetJatuhTempoService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };

        $scope.reloadData = function () {
            $scope.dataPage = KetJatuhTempoService.query($scope.search, $scope.paging.currentPage-1, function () {
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
                templateUrl: 'app/pages/master/ketJatuhTempo/ket-jatuh-tempo-modal.html',
                controller: 'KetJatuhTempoModalController',
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
                controller: 'KetJatuhTempoModalController',
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
            KetJatuhTempoService.hapus(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            });
        }
    }

    function KetJatuhTempoModalController($uibModalInstance, toastr, $scope, KetJatuhTempoService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit KetJatuhTempo";
        console.log('edit', data);
        $scope.vm = angular.copy(data);


        $scope.simpan = function () {
            KetJatuhTempoService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            })
        }
    }
})();

