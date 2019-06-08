(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .factory('DashboardService', DashboardService);

    /** @ngInject */
    DashboardService.inject = ['$http', '$resource'];

    function DashboardService($http, $resource) {
        var url = 'api/report';
        return {
            rekapColiKubikasi: rekapColiKubikasi,
            rekapTerbayarTagihan: rekapTerbayarTagihan,
            historyTagihanToko: historyTagihanToko,
        }

        ;
        function rekapColiKubikasi(options) {
            return $http.get(url + '/rekap-coli-kubikasi?tgl1=' + options.tgl1 + '&tgl2=' + options.tgl2 + '&grup=' + options.grup + '&limit=' + options.limit + '&order=' + options.order);
        }
        function rekapTerbayarTagihan(options) {
            return $http.get(url + '/rekap-terbayar-tagihan?tgl1=' + options.tgl1 + '&tgl2=' + options.tgl2 + '&limit=' + options.limit + '&order=' + options.order);
        }
        function historyTagihanToko(options) {
            return $http.get(url + '/history-tagihan-toko?limit=' + options.limit + '&th_bln2=' + options.thBln2 + '&idtoko=' + options.toko.id);
        }
    }

})();

