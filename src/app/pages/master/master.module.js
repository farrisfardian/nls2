/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.master', ['ui.select', 'ngSanitize'])
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('master', {
                    parent: 'app',
                    url: '/master',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'Master',
                    sidebarMeta: {
                        icon: 'ion-compose',
                        order: 100,
                    },
                })
                .state('master.item', {
                    url: '/item',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/item/item.html',
                            controller: 'ItemCtrl'
                        }
                    },
                    title: 'Item',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kontainer', {
                    url: '/kontainer',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kontainer/kontainer.html',
                            controller: 'KontainerCtrl',
                        }
                    },
                    title: 'Kontainer',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.jenis-item', {
                    url: '/jenis-item',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/jenisItem/jenis-item.html',
                            controller: 'JenisItemCtrl',
                        }
                    },
                    title: 'Jenis Item',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.tambahan-biaya', {
                    url: '/tambahan-biaya',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/tambahanBiaya/tambahan-biaya.html',
                            controller: 'TambahanBiayaCtrl',
                        }
                    },
                    title: 'Tambahan Biaya',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kapal', {
                    url: '/kapal',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kapal/kapal.html',
                            controller: 'KapalCtrl',
                        }
                    },
                    title: 'Kapal',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kota', {
                    url: '/kota',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kota/kota.html',
                            controller: 'KotaCtrl',
                        }
                    },
                    title: 'Kota',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.satuan-kirim', {
                    url: '/satuan-kirim',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/satuanKirim/satuan-kirim.html',
                            controller: 'SatuanKirimCtrl',
                        }
                    },
                    title: 'Satuan Kirim',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kondisi', {
                    url: '/kondisi',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kondisi/kondisi.html',
                            controller: 'KondisiCtrl',
                        }
                    },
                    title: 'Kondisi',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.toko', {
                    url: '/toko',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/toko/toko.html',
                            controller: 'TokoCtrl',
                        }
                    },
                    title: 'Toko',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.emkl', {
                    url: '/emkl',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/emkl/emkl.html',
                            controller: 'EmklCtrl',
                        }
                    },
                    title: 'Emkl',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kapal-berangkat', {
                    url: '/kapal-berangkat',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kapalBerangkat/kapal-berangkat.html',
                            controller: 'KapalBerangkatCtrl',
                        }
                    },
                    title: 'Kapal Berangkat',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.kategori-harga', {
                    url: '/kategori-harga',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/kategoriHarga/kategori-harga.html',
                            controller: 'KategoriHargaCtrl',
                        }
                    },
                    title: 'Kategori Harga',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.setting-harga', {
                    url: '/setting-harga',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/settingHarga/setting-harga.html',
                            controller: 'SettingHargaCtrl',
                        }
                    },
                    title: 'Setting Harga',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.setting-aplikasi', {
                    url: '/setting-aplikasi',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/settingAplikasi/setting-aplikasi.html',
                            controller: 'SettingAplikasiCtrl',
                        }
                    },
                    title: 'Setting Aplikasi',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.jenis-pembayaran', {
                    url: '/jenis-pembayaran',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/jenisPembayaran/jenis-pembayaran.html',
                            controller: 'JenisPembayaranCtrl',
                        }
                    },
                    title: 'Jenis Pembayaran',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                .state('master.rekening', {
                    url: '/rekening',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/master/rekening/rekening.html',
                            controller: 'RekeningCtrl',
                        }
                    },
                    title: 'Rekening',
                    sidebarMeta: {
                        order: 0,
                    },
                })
    }
})();
