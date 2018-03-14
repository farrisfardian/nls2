(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi')
            .controller('ListSuratJalanCtrl', ListSuratJalanCtrl);

    function ListSuratJalanCtrl($scope, $window, $uibModal, $filter, SuratJalanService, KotaService, toastr) {
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.vm = {tglAwal: new Date(), tglAkhir: new Date(), kota: null, cari: "", statusNota: 'Semua'};
        $scope.listStatusNota = ['Semua', 'Sudah', 'Belum'];
        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        $scope.reloadData = function () {
            console.log('vm', $scope.vm);
            console.log('tglAwal', $filter('date')(new Date($scope.vm.tglAwal), 'yyyy-MM-dd'));
            $scope.dataPage = SuratJalanService.queryComposite($filter('date')(new Date($scope.vm.tglAwal), 'yyyy-MM-dd'), $filter('date')(new Date($scope.vm.tglAkhir), 'yyyy-MM-dd'), ($scope.vm.kota == null || $scope.vm.kota.id == undefined || $scope.vm.kota.id == null ? 0 : $scope.vm.kota.id), ($scope.vm.cari == '' ? 'null' : $scope.vm.cari), $scope.vm.statusNota, $scope.paging.currentPage - 1, function () {
                console.log('reloadData', $scope.dataPage);
                $scope.paging.maxSize = ($scope.dataPage.size);
                $scope.paging.totalItems = $scope.dataPage.totalElements;
                $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                $scope.paging.maxPage = $scope.dataPage.totalPages;
            });
        };
        $scope.reloadData();

        $scope.edit = function (x) {
            $window.open('#/transaksi/suratjalan/' + x.id, '_blank');
        };

        $scope.buatNota = function (x) {
            $window.open('#/transaksi/nota/' + x.id_toko_tujuan+'/'+x.id_merk_tujuan+'/'+x.id_kapal_berangkat, '_blank');
        };
        
        $scope.baru = function (x) {
            window.location.href = '#/transaksi/suratjalan';
        };

        $scope.hapus = function (x) {
            console.log('hapus', x);
            SuratJalanService.hapus(x.id).success(function (d) {
                toastr.success('Hapus data sukses!');
            });
        };

        $scope.cetak = function (c, ex, tipe) {
            var link = 'api/report/per-stuffing.' + tipe + '?id=' + c.id_stuffing + '&ex=' + ex;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        }


    }
}
)();