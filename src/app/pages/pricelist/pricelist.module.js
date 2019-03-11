/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.pricelist', ['ui.select', 'ngSanitize'])
            .config(routeConfig);
    
//    routeConfig.$inject = ['$stateProvider'];

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('pricelist', {
                    parent: 'app',
                    url: '/pricelist',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'Pricelist',
                    sidebarMeta: {
                        icon: 'ion-compose',
                        order: 100,
                    },
                })
                .state('pricelist.pricelist-pelayaran', {
                    url: '/pricelist-pelayaran',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/pricelist/pricelistPelayaran/pricelist-pelayaran.html',
                            controller: 'PricelistPelayaranCtrl',
                        }
                    },
                    title: 'Pricelist Pelayaran',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('pricelist.dooring', {
                    url: '/dooring',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/pricelist/dooring/dooring.html',
                            controller: 'DooringCtrl',
                        }
                    },
                    title: 'Setting Dooring',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('pricelist.provit', {
                    url: '/provit',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/pricelist/provit/provit.html',
                            controller: 'ProvitCtrl',
                        }
                    },
                    title: 'Setting Provit',
                    sidebarMeta: {
                        order: 0,
                    },
                })
    }
})();
