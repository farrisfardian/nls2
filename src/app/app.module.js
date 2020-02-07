'use strict';

angular.module('BlurAdmin', [
    'ngAnimate',
    'ui.bootstrap',
    'ui.sortable',
    'ui.router',
    'ui.select',
    'ngSanitize',
    'ngTouch',
    'toastr',
    'smart-table',
    'xeditable',
    'ui.slimscroll',
    'ngJsTree',
    'angular-progress-button-styles',
    'ngResource',
    'ngBootbox',
    'ae-datetimepicker',
    'ngStorage',
    'ngCookies',
    'ngCacheBuster',
    'BlurAdmin.theme',
    'BlurAdmin.pages',
    'hc.marked',
    'ntt.TreeDnD'
]).directive('ngConfirm', ['$uibModal', function ($uibModal) {
        return {
            restrict: 'A',
            scope: {
                ngConfirmMessage: '@',
                ngConfirm: '&',
                ngNotConfirm: '&'
            },
            link: function (scope, element) {
                element.bind('click', function () {
                    var modalInstance = $uibModal.open({
                        template: '<div class="modal-header"><h4 class="modal-title">{{confirmMessage}}</h3></div><div class="modal-footer"><button class="btn btn-primary" type="button" ng-click="ok()">Ya</button><button class="btn btn-warning" type="button" ng-click="cancel()">Tidak</button></div>',
                        controller: 'ModalConfirmCtrl',
                        size: 'md',
                        windowClass: 'confirm-window',
                        resolve: {
                            confirmClick: function () {
                                return scope.ngConfirm;
                            },
                            notConfirmClick: function () {
                                return scope.ngNotConfirm;
                            },
                            confirmMessge: function () {
                                return scope.ngConfirmMessage;
                            }
                        }
                    });
                });
            }
        }
    }])
        .controller('ModalConfirmCtrl', ['$scope', '$uibModalInstance', 'notConfirmClick', 'confirmClick', 'confirmMessge',
            function ($scope, $uibModalInstance, notConfirmClick, confirmClick, confirmMessge) {
                $scope.confirmMessage = confirmMessge;
                function closeModal() {
                    $uibModalInstance.dismiss('cancel');
                }

                $scope.ok = function () {
                    confirmClick();
                    closeModal();
                }

                $scope.cancel = function () {
                    notConfirmClick();
                    closeModal();
                }
            }])
        .run(['$rootScope','$location', function ($rootScope,$location) {
                $rootScope.$on('$stateChangeStart', function (e, toState, toParams, fromState, fromParams, option) {
                    console.log(e);
                    console.log(toState);
                    console.log(toParams);
                    console.log(fromState);
                    console.log(fromParams);
                    console.log(option);
                    $rootScope.title=toState.title;
                    var exists = false;

                    if ($rootScope.currentLogin !== undefined) {
                        for (var i = 0; i < $rootScope.currentLogin.role.menuSet.length; i++) {
                            if ($rootScope.currentLogin.role.menuSet[i].menu.stateRef == toState.name) {
                                exists = true;
                            }
                        }
//                        console.log('exists', exists);
                        if (!exists) {
                            $location.path('/');
                        }
                    }
                })
            }]);