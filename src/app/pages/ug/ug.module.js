/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.ug', ['ui.select', 'ngSanitize'])
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('ug', {
                    parent: 'app',
                    url: '/ug',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'User Guide',
                    sidebarMeta: {
                        icon: 'ion-map',
                        order: 200,
                    },
                })
                .state('ug.md-html', {
                    url: '/md-html',
//                    templateUrl: 'app/pages/transaksi/stuffing/stuffing.html',
//                    controller: 'StuffingCtrl',
//          controllerAs: 'vm',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/ug/md-html.html',
                            controller: 'UgMdHtmlCtrl'
                        }
                    },
                    title: 'User Guide',
                    sidebarMeta: {
                        order: 0,
                    },
                })
    }
})();
