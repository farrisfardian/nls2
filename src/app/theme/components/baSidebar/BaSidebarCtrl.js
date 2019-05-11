/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
            .controller('BaSidebarCtrl', BaSidebarCtrl);

    /** @ngInject */
    function BaSidebarCtrl($scope, baSidebarService) {

//        $scope.menuItems = baSidebarService.getMenuItems();
        $scope.menuItems = [
            {
                "name": "master",
                "title": "Master",
                "level": 0, "order": 100,
                "icon": "ion-compose",
                "stateRef": "master",
                "subMenu": [
                    {
                        "name": "master.item",
                        "title": "Item",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.item"
                    },
                    {
                        "name": "master.tambahan-biaya",
                        "title": "Tambahan Biaya",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.tambahan-biaya"
                    },
                    {
                        "name": "master.jenis-item",
                        "title": "Jenis Item",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.jenis-item"
                    },
                    {
                        "name": "master.kapal",
                        "title": "Kapal",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kapal"
                    },
                    {
                        "name": "master.kota",
                        "title": "Kota",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kota"
                    },
                    {
                        "name": "master.satuan-kirim",
                        "title": "Satuan Kirim",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.satuan-kirim"
                    },
                    {
                        "name": "master.kondisi",
                        "title": "Kondisi",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kondisi"
                    },
                    {
                        "name": "master.toko",
                        "title": "Toko",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.toko"
                    },
                    {
                        "name": "master.emkl",
                        "title": "EMKL",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.emkl"
                    },
                    {
                        "name": "master.kapal-berangkat",
                        "title": "Kapal Berangkat",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kapal-berangkat"
                    },
                    {
                        "name": "master.kategori-harga",
                        "title": "Kategori Harga",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kategori-harga"
                    },
                    {
                        "name": "master.setting-harga",
                        "title": "Setting Harga",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.setting-harga"
                    },
                    {
                        "name": "master.setting-aplikasi",
                        "title": "Setting Aplikasi",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.setting-aplikasi"
                    },
                    {
                        "name": "master.jenis-pembayaran",
                        "title": "Jenis Pembayaran",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.jenis-pembayaran"
                    },
                    {
                        "name": "master.rekening",
                        "title": "Rekening",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.rekening"
                    },
                    {
                        "name": "master.ket-jatuh-tempo",
                        "title": "Ket. Jatuh Tempo",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.ket-jatuh-tempo"
                    },
                    {
                        "name": "master.kategori-barang",
                        "title": "Kategori Barang",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.kategori-barang"
                    },
                    {
                        "name": "master.pelayaran",
                        "title": "Pelayaran",
                        "level": 1,
                        "order": 0,
                        "stateRef": "master.pelayaran"
                    },
                    
                ]
            },
            {
                "name": "pricelist",
                "title": "Pricelist",
                "level": 0,
                "order": 200,
                "icon": "ion-map",
                "stateRef": "pricelist",
                "subMenu": [
                    {
                        "name": "pricelist.pricelist-pelayaran",
                        "title": "Pricelist Pelayaran",
                        "level": 1,
                        "order": 0,
                        "stateRef": "pricelist.pricelist-pelayaran"
                    },
//                    {
//                        "name": "pricelist.dooring",
//                        "title": "Setting Dooring",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "pricelist.dooring"
//                    },
//                    {
//                        "name": "pricelist.provit",
//                        "title": "Setting Provit",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "pricelist.provit"
//                    }
                ]
            },
            {
                "name": "transaksi",
                "title": "Transaksi",
                "level": 0,
                "order": 200,
                "icon": "ion-map",
                "stateRef": "transaksi",
                "subMenu": [
                    {
                        "name": "transaksi.stuffing",
                        "title": "Stuffing",
                        "level": 1,
                        "order": 0,
                        "stateRef": "transaksi.stuffing"
                    },
                    {
                        "name": "transaksi.listsuratjalan",
                        "title": "List Surat Jalan",
                        "level": 1,
                        "order": 100,
                        "stateRef": "transaksi.listsuratjalan"
                    },
                    {
                        "name": "transaksi.suratjalan",
                        "title": "Surat Jalan",
                        "level": 1,
                        "order": 100,
                        "stateRef": "transaksi.suratjalan"
                    },
                    {
                        "name": "transaksi.nota",
                        "title": "Nota",
                        "level": 1,
                        "order": 100,
                        "stateRef": "transaksi.nota"
                    },
                    {
                        "name": "transaksi.pembayaran",
                        "title": "Pembayaran",
                        "level": 1,
                        "order": 100,
                        "stateRef": "transaksi.pembayaran"
                    }
                ]
            },
            {
                "name": "laporan",
                "title": "Laporan",
                "level": 0,
                "order": 300,
                "icon": "ion-compose",
                "stateRef": "laporan",
                "subMenu": [
                    {
                        "name": "laporan.dashboard",
                        "title": "Dashboard",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.dashboard"
                    },
                    {
                        "name": "laporan.pertoko",
                        "title": "Per Toko",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.pertoko"
                    },
                    {
                        "name": "laporan.perkapal",
                        "title": "Stuffing / Kapal",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.perkapal"
                    },
                    {
                        "name": "laporan.infoitem",
                        "title": "Info Item",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.infoitem"
                    },
                    {
                        "name": "laporan.container",
                        "title": "Jml Container Bulanan",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.container"
                    },
                    {
                        "name": "laporan.rincian-nota",
                        "title": "Rincian Nota",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.rincian-nota"
                    },
                    {
                        "name": "laporan.rekap-nota",
                        "title": "Rekap Nota",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.rekap-nota"
                    },
                    {
                        "name": "laporan.rekap-nota-belum-lunas",
                        "title": "Rekap Nota Belum Lunas",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.rekap-nota-belum-lunas"
                    },
                    {
                        "name": "laporan.rekap-pembayaran",
                        "title": "Rekap Pembayaran",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.rekap-pembayaran"
                    },
                    {
                        "name": "laporan.pricelist-pelayaran",
                        "title": "Pricelist Pelayaran",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.pricelist-pelayaran"
                    },
                    {
                        "name": "laporan.tracking-kapal",
                        "title": "Tracking Kapal",
                        "level": 1,
                        "order": 0,
                        "stateRef": "laporan.tracking-kapal"
                    }
                ]
            },
            {
                "name": "admin",
                "title": "Administrasi",
                "level": 0,
                "order": 100,
                "icon": "ion-settings",
                "stateRef": "admin",
                "subMenu": [
                    {
                        "name": "sistem",
                        "title": "Sistem",
                        "level": 0,
                        "order": 100,
                        "icon": "ion-settings",
                        "stateRef": "sistem",
                        "subMenu": [
                            {
                                "name": "user-management",
                                "title": "Pengguna",
                                "level": 1,
                                "order": 100,
                                "stateRef": "user-management"
                            },
                            {
                                "name": "jhi-metrics",
                                "title": "Metric",
                                "level": 1,
                                "order": 100,
                                "stateRef": "jhi-metrics"
                            },
                            {
                                "name": "jhi-health",
                                "title": "Health",
                                "level": 1,
                                "order": 100,
                                "stateRef": "jhi-health"
                            },
                            {
                                "name": "jhi-configuration",
                                "title": "Configuration",
                                "level": 1,
                                "order": 100,
                                "stateRef": "jhi-configuration"
                            },
                            {
                                "name": "audits",
                                "title": "Audits",
                                "level": 1,
                                "order": 100,
                                "stateRef": "audits"
                            },
                            {
                                "name": "docs",
                                "title": "API",
                                "level": 1,
                                "order": 100,
                                "stateRef": "docs"
                            },
                        ]
                    },
                    {
                        "name": "master.matauang",
                        "title": "Matauang",
                        "level": 1,
                        "order": 200,
                        "stateRef": "master.matauang"
                    },
                ]
            },
        ];
        console.log('$scope.menuItems', $scope.menuItems);
        $scope.defaultSidebarState = $scope.menuItems[0].stateRef;

        $scope.hoverItem = function ($event) {
            $scope.showHoverElem = true;
            $scope.hoverElemHeight = $event.currentTarget.clientHeight;
            var menuTopValue = 66;
            $scope.hoverElemTop = $event.currentTarget.getBoundingClientRect().top - menuTopValue;
        };

        $scope.$on('$stateChangeSuccess', function () {
            if (baSidebarService.canSidebarBeHidden()) {
                baSidebarService.setMenuCollapsed(true);
            }
        });
    }
})();
