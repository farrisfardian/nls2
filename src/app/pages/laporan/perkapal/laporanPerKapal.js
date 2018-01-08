(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .controller('LaporanPerKapalCtrl', LaporanPackingListCtrl)
    function LaporanPackingListCtrl($scope, KotaService, StuffingService, KapalBerangkatService) {
        $scope.kota = {};
        $scope.kapal = {};
        $scope.open = open;
        $scope.opened = false;
        $scope.format = 'dd-MM-yyyy';
        $scope.tgl1 = new Date();
        $scope.options = {
            showWeeks: false
        };

        function open() {
            $scope.opened = true;
        }
        KotaService.cariSemua().success(function (data) {
            $scope.listKota = data;
        });
        $scope.reloadKapal = function () {
            KapalBerangkatService.filterByIdKotaAndAktif($scope.kota.selected.id).success(function (data) {
                $scope.listKapal = data;
                console.log('listKapal', data);
            });
        };
        $scope.reloadKontainer = function () {
            $scope.listKontainer = [];
            if (!angular.isDefined($scope.kota)||$scope.kota==null||$scope.kota.selected==null) {
                return;
            } else {
                var kapal = angular.isDefined($scope.kapal) && angular.isDefined($scope.kapal.selected) && $scope.kapal.selected!=null&& $scope.kapal.selected.id!=null ? $scope.kapal.selected.id : 0;
                var kota = angular.isDefined($scope.kota) && angular.isDefined($scope.kota.selected) && $scope.kota.selected!=null&& $scope.kota.selected.id!=null ? $scope.kota.selected.id : 0;
                console.log('kota', kota);
                StuffingService.listStuffingPerKapalBerangkat(kota, kapal).success(function (data) {
                    $scope.listKontainer = data;
                    console.log('listKontainer', data);
                });
            }
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
        $scope.$watch('kota.selected', function () {
            $scope.kapal.selected = null;
            $scope.listKapal = [];
            if ($scope.kota.selected != null) {
                $scope.reloadKapal();
                $scope.reloadKontainer();
            }
        });

        $scope.$watch('kapal.selected', function () {
            $scope.reloadKontainer();
        });

    }
})();