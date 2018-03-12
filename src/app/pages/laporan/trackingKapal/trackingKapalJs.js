(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi')
            .controller('TrackingKapalCtrl', TrackingKapalCtrl);

    function TrackingKapalCtrl($scope, $window, $uibModal, $filter, $sce, SuratJalanService, TokoService, toastr) {
        $scope.paging = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.options = {format: 'DD/MM/YYYY', showClear: false};
        $scope.vm = {tglAwal: new Date(), tglAkhir: new Date(), toko: null, cari: ""};
        $scope.urlTracking = "https://www.marinetraffic.com/en/ais/home/centerx:120.5/centery:-3.6/zoom:5";
//        $scope.urlTracking = "https://www.marinetraffic.com/en/data/?asset_type=vessels&columns=flag,shipname,show_on_live_map,time_of_latest_position,area,lat_of_latest_position,lon_of_latest_position,imo,mmsi,photo,reported_destination,reported_eta,recognized_next_port,current_port,ship_type&flag_in|in|Indonesia|flag_in=ID&ship_type_in|in|Cargo%20Vessels|ship_type_in=7";
        $scope.trustSrc = function (src) {
            return $sce.trustAsResourceUrl(src);
        }

        $scope.cetak = function (c, ex, tipe) {
            var link = 'api/report/per-stuffing.' + tipe + '?id=' + c.id_stuffing + '&ex=' + ex;
            if (tipe == 'pdf') {
//                    window.open(link, '_blank', 'width=screen.width, height=screen.height');
                window.open(link, '_blank', 'width=1024, height=768');
            } else {
                location.href = link;
            }
        };

    }
}
)();