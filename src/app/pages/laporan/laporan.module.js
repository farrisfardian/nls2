/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan', ['ui.select', 'ngSanitize'])
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('laporan', {
                    parent: 'app',
                    url: '/laporan',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'Laporan',
                    sidebarMeta: {
                        icon: 'ion-compose',
                        order: 300,
                    },
                })
                .state('laporan.pertoko', {
                    url: '/per-toko',
//          controllerAs: 'vm',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/pertoko/laporan-pertoko.html',
                            controller: 'LaporanPerMerkCtrl'
                        }
                    },
                    title: 'Per Toko',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.perkapal', {
                    url: '/per-kapal',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/perkapal/laporan-perkapal.html',
                            controller: 'LaporanPerKapalCtrl',
                        }
                    },
                    title: 'Per Kapal',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.packinglist', {
                    url: '/packing-list',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/packinglist/laporan-packinglist.html',
                            controller: 'LaporanPackingListCtrl'
                        }
                    },
                    title: 'Packing List',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.dashboard', {
                    url: '/dashboard',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/dashboard/dashboard.html',
                            controller: 'DashboardCtrl'
                        }
                    },
                    title: 'Dashboard',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.infoitem', {
                    url: '/infoitem',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/infoitem/infoItem.html',
                            controller: 'InfoItemCtrl'
                        }
                    },
                    title: 'Info Item',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.container', {
                    url: '/jmlcontainer',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/jmlContainerBulanan/jmlContainerBulanan.html',
                            controller: 'JmlContainerBulananCtrl'
                        }
                    },
                    title: 'Jml Container Bulanan',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.rincian-nota', {
                    url: '/rincian-nota',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/rincianNota/rincianNota.html',
                            controller: 'RincianNotaCtrl'
                        }
                    },
                    title: 'Rincian Nota',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('laporan.rekap-nota', {
                    url: '/rekap-nota',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/laporan/rekapNota/rekapNota.html',
                            controller: 'RekapNotaCtrl'
                        }
                    },
                    title: 'Rekap Nota',
                    sidebarMeta: {
                        order: 0,
                    },
                })
    }
})();
