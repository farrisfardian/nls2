(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('PelayaranCtrl', PelayaranCtrl)
            .controller('PelayaranModalController', PelayaranModalController);

    /** @ngInject */
    function PelayaranCtrl($scope, $uibModal, $log, $filter, toastr, PelayaranService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.modalTitle = "Tambah Pelayaran";
        $scope.opened = false;
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.opened = false;
        $scope.openedAwal = false;
        $scope.openedAkhir = false;        
        $scope.dateOptions = {format: 'DD/MM/YYYY', showClear: false};        
        $scope.vm = {};
        $scope.ori = {};
        $scope.param = {cari: ""};

        $scope.reloadData = function () {
            $scope.dataPage = PelayaranService.query($scope.param.cari, $scope.paging.currentPage - 1, function () {
                $scope.paging.maxSize = ($scope.dataPage.size);
                $scope.paging.totalItems = $scope.dataPage.totalElements;
                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                $scope.paging.maxPage = $scope.dataPage.totalPages;
            });
        };

        $scope.clear = function () {
            $scope.modalTitle = "";
            $scope.vm = {};
            $scope.ori = {};
            $scope.reloadData();
        };

        $scope.clear();

        $scope.baru = function () {
            $scope.modalTitle = "Tambah Pelayaran";
            $scope.vm = {};
            $scope.ori = {};
            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Pelayaran";
            console.log('edit', x);
            PelayaranService.cariSatu("kode", x.id).success(function (data) {
                $scope.vm = angular.copy(data);
            });
        };

        $scope.hapus = function (x) {
            PelayaranService.hapus(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            });
        };

        $scope.simpan = function () {
            PelayaranService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            });
        };
    }

    function PelayaranModalController($uibModalInstance, toastr, $scope, PelayaranService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit Pelayaran";
        console.log('edit', data);
        $scope.vm = angular.copy(data);


        $scope.simpan = function () {
            PelayaranService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            })
        }
    }
})();

