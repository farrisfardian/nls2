'use strict';

/**
 * @ngdoc function
 * @name belajarYeomanApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the belajarYhaeomanApp
 */
angular.module('BlurAdmin')
        .controller('SettingAplikasiCtrlOld', function ($scope, SettingAplikasiService) {
            $scope.search = "";
            $scope.oldSearch = "";
            $scope.paging = {
                currentPage: 1,
                totalItems: 0
            };
            $scope.currentRecord = {tmtBerlaku: new Date()};
            $scope.dateOptions = {format: 'DD/MM/YYYY', showClear: false};
            $scope.timeOptions = {format: 'HH:mm', showClear: false};
            $scope.reloadData = function () {
//                $scope.paging.currentPage = $scope.search != $scope.oldSearch ? 1 : $scope.currentPage;
                $scope.dataPage = SettingAplikasiService.query($scope.search, $scope.paging.currentPage, function () {
                    $scope.paging.maxSize = ($scope.dataPage.size);
                    $scope.paging.totalItems = $scope.dataPage.totalElements;
                    $scope.paging.currentPage = parseInt($scope.dataPage.number) + 1;
                    $scope.paging.maxPage = $scope.dataPage.totalPages;
                });
                console.log("data : ", $scope.dataPage);
            };

            $scope.reloadData();
//            $scope.$watch('paging.currentPage', $scope.reloadData, true);
            $scope.edit = function (x) {
                $scope.formTitle = "Edit Setting";
                $scope.modeForm = "Edit";

                if (x.id) {
                    SettingAplikasiService.get("id", x.id).success(function (data) {
                        console.log('edit', data);
                        $scope.currentRecord = data;
                        if ($scope.currentRecord.jadwalBatalIjin !== null && $scope.currentRecord.jadwalBatalIjin !== undefined) {
                            $scope.currentRecord.jadwalBatalIjin = new Date().format('yyyy-mm-dd') + ' ' + $scope.currentRecord.jadwalBatalIjin;
                        }
                        $scope.original = angular.copy($scope.currentRecord);
                        $('#formModal').modal('show');
                        $('#formModal').on('shown.bs.modal', function () {
                            $('#kode').focus();
                        });
                    });
                }
            };

            $scope.isClean = function () {
                return angular.equals($scope.original, $scope.currentRecord);
            };

            $scope.clear = function () {
                $scope.formTitle = "Tambah Store";
                $scope.modeForm = "Tambah";
                $scope.original = null;
                $scope.isNameExists = false;
                $scope.currentRecord = {};
                $scope.reloadData();
                $('#formModal').on('shown.bs.modal', function () {
                    $('#kode').focus();
                });
            };


            $scope.remove = function (x) {
                bootbox.confirm('Anda yakin untuk mengahapus Setting [' + x.tmtBerlaku + '] ?', function (result) {
                    if (result) {
                        SettingAplikasiService.remove(x).success(function () {
                            $scope.clear();
                        });
                    }
                });
            };

            $scope.save = function () {
                console.log('save',$scope.currentRecord);
                SettingAplikasiService.save($scope.currentRecord, $scope.modeForm).success(function () {
                    $('#formModal').modal('hide');
                    bootbox.alert('Simpan Setting sukses ');
                    $scope.clear();
                });
            };

        });
