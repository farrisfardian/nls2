(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('SettingAplikasiCtrl', SettingAplikasiCtrl);

    /** @ngInject */
    function SettingAplikasiCtrl($scope, $uibModal, $log, $filter, toastr, SettingAplikasiService) {
        $scope.search = "";
        $scope.oldSearch = "";
        $scope.deskKapalBerangkat = "";
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.param = {tglBerlaku: new Date(), kota: null, toko: null, kondisi: null};
        $scope.reloadData = function () {
            $scope.dataPage = SettingAplikasiService.query($scope.search, $scope.paging.currentPage, function () {
                console.log("data : ", $scope.dataPage);
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
            $scope.vm = {};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Aplikasi";
            console.log('Baru');
        };

        $scope.update = function (x) {
            $scope.vm = {};
            $scope.ori = {};
            $scope.modalTitle = "Tambah Setting Aplikasi";
            SettingAplikasiService.cariSatu("id", x.id).success(function (data) {
                $scope.vm = angular.copy(data);
                $scope.vm.id = null;
                $scope.vm.tmtBerlaku = null;                
            });

            console.log('Baru');
        };

        $scope.edit = function (x) {
            $scope.ori = angular.copy(x);
            $scope.modalTitle = "Edit Setting Aplikasi";
            console.log('edit', x);
            SettingAplikasiService.cariSatu("id", x.id).success(function (data) {
                if (data.tglBerlaku !== null && data.tglBerlaku !== undefined) {
                    data.tglBerlaku = new Date(data.tglBerlaku);
                }
                $scope.vm = angular.copy(data);
            });
        };

        $scope.hapus = function (x) {
            SettingAplikasiService.hapus(x.id).success(function () {
                toastr.success('Hapus data sukses!');
                $scope.reloadData();
            }).error(function () {
                toastr.error('Maaf Anda tidak dapat menghapus SettingAplikasi');
            });
        };

        $scope.simpan = function () {
            SettingAplikasiService.simpan($scope.vm, $scope.ori).success(function (d) {
                toastr.success('Simpan data sukses!');
                $scope.clear();
            }).error(function (e) {
                toastr.error('Gagal simpan setting harga! Pastikan tanggal, toko, kondisi, asal & tujuan tidak sama persis dengan setting yang lalu.');
            });
        };

        $scope.cetak = function (c, ex, tipe) {
            var link = 'api/report/per-stuffing.' + tipe + '?id=' + c.id + '&ex=' + ex;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        }
    }

})();

