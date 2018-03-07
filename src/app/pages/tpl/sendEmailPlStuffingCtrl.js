(function () {
    'use strict';

    angular.module('BlurAdmin')
            .controller('SendEmailPlStuffingCtrl', SendEmailPlStuffingCtrl)
    function SendEmailPlStuffingCtrl($scope, $http, $uibModalInstance, param, $timeout) {
        $scope.selected = {};
        console.log('SendEmailPlStuffingCtrl --> param', param)
        $scope.listData = [];
        $scope.emkl = param.emkl;
        $scope.email = param.email;
        $scope.ex = param.ex;
        $scope.idStuffing = param.idStuffing;
        $scope.listStuffing = param.listStuffing;
        $scope.subjek = "";
        $scope.isi = "";
        $scope.kirimEmail = function () {
            $http.get("api/report/kirim-email/per-stuffing" + '?id=' + $scope.idStuffing + '&ex=' + $scope.ex + "&email=" + $scope.email + "&subjek=" + $scope.subjek + "&isi=" + $scope.isi).success(function (data) {
                console.log('kirim email ', data);
//                alert('Berhasil kirim email');
                $scope.selected = data;
                $scope.modalSelected();
            }).error(function (e) {
                $scope.modalSelected(e);
//                alert('Ada masalah saat kirim email');
//                $scope.cancel();
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