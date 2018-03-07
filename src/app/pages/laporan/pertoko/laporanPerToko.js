(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .controller('LaporanPerMerkCtrl', LaporanPackingListCtrl)
    function LaporanPackingListCtrl($scope, $http, $uibModal, $log, toastr, KotaService, TokoService, KapalBerangkatService) {
        $scope.kota = {};
        $scope.kapal = {};
        $scope.open = open;
        $scope.listSelectedToko = [];
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
            var listToko = [];
            for (var i = 0; i < $scope.listToko.length; i++) {
                if ($scope.listToko[i].terpilih == true) {
                    if (idMerks == "") {
                        idMerks += $scope.listToko[i].id_merk;
                    } else {
                        idMerks += "," + $scope.listToko[i].id_merk;
                    }
                    listToko.push($scope.listToko[i]);
                }
            }
            console.log('idMerks', idMerks);
            var kapal = angular.isDefined($scope.kapal) && angular.isDefined($scope.kapal.selected) && $scope.kapal.selected != null && $scope.kapal.selected.id != null ? $scope.kapal.selected.id : 0;
            var link = 'api/report/' + (isPisahEmkl === true ? 'per-merk-toko-pisah-emkl.' : 'per-merk-toko.') + tipe + '?id=' + kapal + '&it=' + idMerks;
            if (tipe == 'pdf') {
                window.open(link, '_blank', 'width=1024, height=768');
            } else if (tipe == 'xlsx') {
                location.href = link;
            } else if (tipe == 'email') {

                $scope.kirimEmail({
                    toko: $scope.listSelectedToko[0].nama,
                    email: $scope.listSelectedToko[0].email,
                    kapal: kapal,
                    idMerks: idMerks,
                    listToko: listToko,
                });
//                $http.get("api/report/kirim-email/per-merk-toko-pisah-emkl" + '?id=' + kapal + '&it=' + idMerks + "&subjek=tes&isi=TES KIRIM EMAIL").success(function (data) {
//                    console.log('kirim email ', data);
//                    alert('Berhasil kirim email');
//                }).error(function (e) {
//                    alert('Ada masalah saat kirim email');
//                });
            }
        };
        $scope.kirimEmail = function (x) {
            console.log('kirimEmail', x);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/pages/tpl/send-email-pl-toko.html',
                controller: 'SendEmailPlTokoCtrl',
                size: 'lg',
                resolve: {
                    param: function () {
                        return {
                            email: x.email,
                            kapal: x.kapal,
                            idMerks: x.idMerks,
                            listToko: x.listToko,
                        }
                    },
                }
            });
            modalInstance.result.then(function (sd) {
                toastr.success(sd.message);
                console.log('Kirim Email', sd);
            }, function () {
                toastr.success('Kirim email gagal');
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.klikToko = function (x, idx) {
            if (x.terpilih === true) {
                var exists = false;
                for (var i = 0; i < $scope.listSelectedToko.length; i++) {
                    if (x.id === $scope.listSelectedToko[i].id) {
                        exists = true;
                    }
                }
                if (exists === false) {
                    $scope.listSelectedToko.push(x);
                }
            } else {
                var hapus = true;
                for (var i = 0; i < $scope.listToko.length; i++) {
                    if ($scope.listToko[i].terpilih === true && i !== idx && $scope.listToko[i].id === x.id) {
                        hapus = false;
                    }
                }
                if (hapus === true) {
                    for (var i = 0; i < $scope.listSelectedToko.length; i++) {
                        if (x.id === $scope.listSelectedToko[i].id) {
                            $scope.listSelectedToko.splice(i, 1);
                        }
                    }
                }
            }
        }
        $scope.$watch('kota.selected', function () {
            $scope.kapal.selected = null;
            $scope.listSelectedToko = [];
            $scope.listKapal = [];
            if ($scope.kota.selected != null) {
                $scope.reloadKapal();
                $scope.reloadToko();
            }
        })

        $scope.$watch('kapal.selected', function () {
            $scope.reloadToko();
            $scope.listSelectedToko = [];
        })

    }
})();