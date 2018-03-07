(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('SendEmailRincianNotaCtrl', SendEmailRincianNotaCtrl)
    function SendEmailRincianNotaCtrl($scope, $http, $uibModalInstance, param, $timeout) {
        $scope.selected = {};
        console.log('SendEmailRincianNotaCtrl --> param', param)
        $scope.listData = [];
        $scope.toko = param.toko;
        $scope.email = param.email;
        $scope.kapal = param.kapal;
        $scope.idMerks = param.idMerks;
        $scope.listToko = param.listToko;
        $scope.subjek = "";
        $scope.isi = "";
        $scope.kirimEmail = function () {
            $http.get("api/report/kirim-email/per-merk-toko-pisah-emkl" + '?id=' + $scope.kapal + '&it=' + $scope.idMerks + "&email=" + $scope.email + "&subjek=" + $scope.subjek + "&isi=" + $scope.isi).success(function (data) {
                console.log('kirim email ', data);
//                alert('Berhasil kirim email');
                $scope.selected = data;
                $scope.modalSelected();
            }).error(function (e) {
//                alert('Ada masalah saat kirim email');
                $scope.cancel();
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.modalSelected = function () {
            $uibModalInstance.close($scope.selected);
        };

    }
})();