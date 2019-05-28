/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.transaksi', ['ui.select', 'ngSanitize'])
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('transaksi', {
                    parent: 'app',
                    url: '/transaksi',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'Transaksi',
                    sidebarMeta: {
                        icon: 'ion-map',
                        order: 200,
                    },
                })
                .state('transaksi.stuffing', {
                    url: '/stuffing',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/stuffing/stuffing.html',
                            controller: 'StuffingCtrl'
                        }
                    },
                    title: 'Stuffing',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('transaksi.suratjalan', {
                    url: '/suratjalan',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/suratjalan/suratjalan.html',
                            controller: 'SuratJalanCtrl'
                        }
                    },
                    title: 'Surat Jalan',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.nota', {
                    url: '/nota',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/nota/nota.html',
                            controller: 'NotaCtrl'
                        }
                    },
                    title: 'Nota',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.pembayaran', {
                    url: '/pembayaran',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/pembayaran/pembayaran.html',
                            controller: 'PembayaranCtrl'
                        }
                    },
                    title: 'Pembayaran',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.suratjalanedit', {
                    url: '/suratjalan/:idSj',
                    views: {
                        'content@': {
                            url: '/suratjalan/:idSj',
                            templateUrl: 'app/pages/transaksi/suratjalan/suratjalan.html',
                        }
                    },
                    title: 'Surat Jalan [Edit]',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.notatambah', {
                    url: '/nota/:idToko/:idMerk/:idKapalBerangkat',
                    views: {
                        'content@': {
                            url: '/nota/:idToko/:idMerk/:idKapalBerangkat',
                            templateUrl: 'app/pages/transaksi/nota/nota.html',
                        }
                    },
                    title: 'Nota',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.pembayarantambah', {
                    url: '/pembayaran/:idToko/:idMerk/:idNotas',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/pembayaran/pembayaran.html',
                            controller: 'PembayaranCtrl'
                        }
                    },
                    title: 'Pembayaran',
                    sidebarMeta: {
                        order: 100,
                    },
                })
                .state('transaksi.listsuratjalan', {
                    url: '/listsuratjalan',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/transaksi/suratjalan/listsuratjalan.html',
                            controller: 'ListSuratJalanCtrl'
                        }
                    },
                    title: 'List Surat Jalan',
                    sidebarMeta: {
                        order: 100,
                    },
                })
    }
})();
