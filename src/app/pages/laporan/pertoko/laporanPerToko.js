(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .controller('LaporanPerMerkCtrl', LaporanPackingListCtrl)
    function LaporanPackingListCtrl($scope, KotaService, TokoService, KapalBerangkatService) {
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
        }
        $scope.reloadToko = function () {
            $scope.listToko = [];
            if (!angular.isDefined($scope.kota) || $scope.kota == null || $scope.kota.selected == null) {
                return;
            } else {
                var kapal = angular.isDefined($scope.kapal) && angular.isDefined($scope.kapal.selected) && $scope.kapal.selected != null && $scope.kapal.selected.id != null ? $scope.kapal.selected.id : 0;
                var kota = angular.isDefined($scope.kota) && angular.isDefined($scope.kota.selected) && $scope.kota.selected != null && $scope.kota.selected.id != null ? $scope.kota.selected.id : 0;
                TokoService.listMerkKapalBerangkat(kota, kapal).success(function (data) {
                    $scope.listToko = data;
                    console.log('listToko', data);
                });
            }
        }
        $scope.cetak = function (tipe, isPisahEmkl) {
            var idMerks = "";
            for (var i = 0; i < $scope.listToko.length; i++) {
                if ($scope.listToko[i].terpilih == true) {
                    if (idMerks == "") {
                        idMerks += $scope.listToko[i].id_merk;
                    } else {
                        idMerks += "," + $scope.listToko[i].id_merk;
                    }
                }
            }
            console.log('idMerks', idMerks);
            var kapal = angular.isDefined($scope.kapal) && angular.isDefined($scope.kapal.selected) && $scope.kapal.selected != null && $scope.kapal.selected.id != null ? $scope.kapal.selected.id : 0;
            var link = 'api/report/' + (isPisahEmkl === true ? 'per-merk-toko-pisah-emkl.' : 'per-merk-toko.') + tipe + '?id=' + kapal + '&it=' + idMerks;
            if (tipe == 'pdf') {
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
                $scope.reloadToko();
            }
        })

        $scope.$watch('kapal.selected', function () {
            $scope.reloadToko();
        })

    }
})();