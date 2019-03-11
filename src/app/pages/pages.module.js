/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages', [
//    'BlurAdmin.pages.dashboard',
//    'BlurAdmin.pages.ui',
//    'BlurAdmin.pages.components',
//        'BlurAdmin.pages.form',
//        'BlurAdmin.pages.tables',
//    'BlurAdmin.pages.charts',
//    'BlurAdmin.pages.maps',
//    'BlurAdmin.pages.profile',
//        'BlurAdmin',
        'BlurAdmin.pages.pricelist',
        'BlurAdmin.pages.master',
        'BlurAdmin.pages.transaksi',
        'BlurAdmin.pages.laporan',
        'BlurAdmin.pages.setting',
    ])
            .directive('ngEnter', ngEnter)
            .directive('whenScrolled', whenScrolled)
            .config(routeConfig);
    
    /** @ngInject */
    function routeConfig($urlRouterProvider, baSidebarServiceProvider) {
        $urlRouterProvider.otherwise('/laporan/dashboard');
    }


    function ngEnter() {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.ngEnter);
                    });

                    event.preventDefault();
                }
            });
        }
    }
    function whenScrolled() {
        return function (scope, elm, attr) {
            var raw = elm[0];
            elm.bind('scroll', function () {
                if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                    scope.$apply(attr.whenScrolled);
                }
            });
        };
    }


})();
