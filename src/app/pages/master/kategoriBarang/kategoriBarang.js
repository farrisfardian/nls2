(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('KategoriBarangCtrl', KategoriBarangCtrl)
            .controller('KategoriBarangModalController', KategoriBarangModalController);

    /** @ngInject */
    function KategoriBarangCtrl($scope, $uibModal, $log, $filter, toastr, KategoriBarangService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.modalTitle = "Tambah KategoriBarang";
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
            $scope.dataPage = KategoriBarangService.query($scope.param.cari, $scope.paging.currentPage - 1, function () {
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
            $scope.modalTitle = "Tambah KategoriBarang";
            $scope.vm = {};
            $scope.ori = {};
            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit KategoriBarang";
            console.log('edit', x);
            KategoriBarangService.cariSatu("kode", x.id).success(function (data) {
                $scope.vm = angular.copy(data);
            });
        };

        $scope.hapus = function (x) {
            KategoriBarangService.hapus(x).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            });
        };

        $scope.simpan = function () {
            KategoriBarangService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            });
        };
    }

    function KategoriBarangModalController($uibModalInstance, toastr, $scope, KategoriBarangService, data) {
        $scope.ori = angular.copy(data);
        $scope.modalTitle = "Edit KategoriBarang";
        console.log('edit', data);
        $scope.vm = angular.copy(data);


        $scope.simpan = function () {
            KategoriBarangService.simpan($scope.vm, $scope.ori).success(function (d) {
                $uibModalInstance.close($scope.vm);
                toastr.success('Simpan data sukses!');
            })
        }
    }
})();

