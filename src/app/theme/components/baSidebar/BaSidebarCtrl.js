/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
            .controller('BaSidebarCtrl', BaSidebarCtrl);

    /** @ngInject */
    function BaSidebarCtrl($scope, baSidebarService, $TreeDnDConvert, $rootScope) {

        $scope.getMenu = function () {
            var flat = [];
            for (var i = 0; i < $rootScope.currentLogin.role.menuSet.length; i++) {
                if ($rootScope.currentLogin.role.menuSet[i].menu.isSidebar) {
                    flat.push({
                        "id": $rootScope.currentLogin.role.menuSet[i].menu.id,
                        "name": $rootScope.currentLogin.role.menuSet[i].menu.name,
                        "title": $rootScope.currentLogin.role.menuSet[i].menu.title,
                        "level": $rootScope.currentLogin.role.menuSet[i].menu.level,
                        "order": $rootScope.currentLogin.role.menuSet[i].menu.order,
                        "icon": "ion-compose",
                        "stateRef": $rootScope.currentLogin.role.menuSet[i].menu.stateRef,
                        "parentId": $rootScope.currentLogin.role.menuSet[i].menu.parent == null ? null : $rootScope.currentLogin.role.menuSet[i].menu.parent.id,
                    });
                }
            }
//            console.log('flat', flat);
//            console.log('$rootScope.currentLogin.role.menuSet', $rootScope.currentLogin.role.menuSet);
            var tree = $TreeDnDConvert.line2tree(
                    flat, 'id', 'parentId'
                    );
//            console.log('tree', tree);
            for (var i = 0; i < tree.length; i++) {
                tree[i].subMenu = tree[i].__children__;
            }
            tree.sort(function (a, b) {
                return a.order - b.order
            });
            $scope.menuItems = tree;
//            [
//                        {
//                            'DemographicId': -1,
//                            'ParentId': undefined,
//                            'Name': 'United States of America',
//                            'Description': 'United States of America',
//                            'Area': 9826675,
//                            'Population': 318212000,
//                            'TimeZone': 'UTC -5 to -10'
//                        }
//                    ]

        };

        $scope.getMenu();

//        $scope.menuItems = baSidebarService.getMenuItems();
//        $scope.menuItems = [
//            {
//                "name": "master",
//                "title": "Master",
//                "level": 0, "order": 100,
//                "icon": "ion-compose",
//                "stateRef": "master",
//                "subMenu": [
//                    {
//                        "name": "master.item",
//                        "title": "Item",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.item"
//                    },
//                    {
//                        "name": "master.tambahan-biaya",
//                        "title": "Tambahan Biaya",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.tambahan-biaya"
//                    },
//                    {
//                        "name": "master.jenis-item",
//                        "title": "Jenis Item",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.jenis-item"
//                    },
//                    {
//                        "name": "master.kapal",
//                        "title": "Kapal",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.kapal"
//                    },
//                    {
//                        "name": "master.kota",
//                        "title": "Kota",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.kota"
//                    },
//                    {
//                        "name": "master.satuan-kirim",
//                        "title": "Satuan Kirim",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.satuan-kirim"
//                    },
//                    {
//                        "name": "master.kondisi",
//                        "title": "Kondisi",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.kondisi"
//                    },
//                    {
//                        "name": "master.toko",
//                        "title": "Toko",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.toko"
//                    },
//                    {
//                        "name": "master.emkl",
//                        "title": "EMKL",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.emkl"
//                    },
//                    {
//                        "name": "master.kapal-berangkat",
//                        "title": "Kapal Berangkat",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.kapal-berangkat"
//                    },
//                    {
//                        "name": "master.kategori-harga",
//                        "title": "Kategori Harga",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.kategori-harga"
//                    },
//                    {
//                        "name": "master.setting-harga",
//                        "title": "Setting Harga",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.setting-harga"
//                    },
//                    {
//                        "name": "master.setting-aplikasi",
//                        "title": "Setting Aplikasi",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.setting-aplikasi"
//                    },
//                    {
//                        "name": "master.jenis-pembayaran",
//                        "title": "Jenis Pembayaran",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.jenis-pembayaran"
//                    },
//                    {
//                        "name": "master.rekening",
//                        "title": "Rekening",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.rekening"
//                    },
//                    {
//                        "name": "master.ket-jatuh-tempo",
//                        "title": "Ket. Jatuh Tempo",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.ket-jatuh-tempo"
//                    },
//                    {
//                        "name": "master.pelayaran",
//                        "title": "Pelayaran",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "master.pelayaran"
//                    },
//                ]
//            },
//            {
//                "name": "pricelist",
//                "title": "Pricelist",
//                "level": 0,
//                "order": 200,
//                "icon": "ion-map",
//                "stateRef": "pricelist",
//                "subMenu": [
//                    {
//                        "name": "pricelist.pricelist-pelayaran",
//                        "title": "Pricelist Pelayaran",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "pricelist.pricelist-pelayaran"
//                    },
//                ]
//            },
//            {
//                "name": "transaksi",
//                "title": "Transaksi",
//                "level": 0,
//                "order": 200,
//                "icon": "ion-map",
//                "stateRef": "transaksi",
//                "subMenu": [
//                    {
//                        "name": "transaksi.stuffing",
//                        "title": "Stuffing",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "transaksi.stuffing"
//                    },
//                    {
//                        "name": "transaksi.listsuratjalan",
//                        "title": "List Surat Jalan",
//                        "level": 1,
//                        "order": 100,
//                        "stateRef": "transaksi.listsuratjalan"
//                    },
//                    {
//                        "name": "transaksi.suratjalan",
//                        "title": "Surat Jalan",
//                        "level": 1,
//                        "order": 100,
//                        "stateRef": "transaksi.suratjalan"
//                    },
//                    {
//                        "name": "transaksi.nota",
//                        "title": "Nota",
//                        "level": 1,
//                        "order": 100,
//                        "stateRef": "transaksi.nota"
//                    },
//                    {
//                        "name": "transaksi.pembayaran",
//                        "title": "Pembayaran",
//                        "level": 1,
//                        "order": 100,
//                        "stateRef": "transaksi.pembayaran"
//                    }
//                ]
//            },
//            {
//                "name": "laporan",
//                "title": "Laporan",
//                "level": 0,
//                "order": 300,
//                "icon": "ion-compose",
//                "stateRef": "laporan",
//                "subMenu": [
//                    {
//                        "name": "laporan.dashboard",
//                        "title": "Dashboard",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.dashboard"
//                    },
//                    {
//                        "name": "laporan.pertoko",
//                        "title": "Per Toko",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.pertoko"
//                    },
//                    {
//                        "name": "laporan.perkapal",
//                        "title": "Stuffing / Kapal",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.perkapal"
//                    },
//                    {
//                        "name": "laporan.infoitem",
//                        "title": "Info Item",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.infoitem"
//                    },
//                    {
//                        "name": "laporan.container",
//                        "title": "Jml Container Bulanan",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.container"
//                    },
//                    {
//                        "name": "laporan.rincian-nota",
//                        "title": "Rincian Nota",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.rincian-nota"
//                    },
//                    {
//                        "name": "laporan.rekap-nota",
//                        "title": "Rekap Nota",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.rekap-nota"
//                    },
//                    {
//                        "name": "laporan.rekap-nota-belum-lunas",
//                        "title": "Rekap Nota Belum Lunas",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.rekap-nota-belum-lunas"
//                    },
//                    {
//                        "name": "laporan.rekap-pembayaran",
//                        "title": "Rekap Pembayaran",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.rekap-pembayaran"
//                    },
//                    {
//                        "name": "laporan.pricelist-pelayaran",
//                        "title": "Pricelist Pelayaran",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.pricelist-pelayaran"
//                    },
//                    {
//                        "name": "laporan.tracking-kapal",
//                        "title": "Tracking Kapal",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "laporan.tracking-kapal"
//                    }
//                ]
//            },
//            {
//                "name": "setting",
//                "title": "Setting",
//                "level": 0,
//                "order": 200,
//                "icon": "ion-map",
//                "stateRef": "setting",
//                "subMenu": [
//                    {
//                        "name": "setting.menu",
//                        "title": "Menu",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "setting.menu"
//                    },
//                    {
//                        "name": "setting.role",
//                        "title": "Role",
//                        "level": 1,
//                        "order": 0,
//                        "stateRef": "setting.role"
//                    },
//                ]
//            },
//            {
//                "name": "admin",
//                "title": "Administrasi",
//                "level": 0,
//                "order": 100,
//                "icon": "ion-settings",
//                "stateRef": "admin",
//                "subMenu": [
//                    {
//                        "name": "user-management",
//                        "title": "Pengguna",
//                        "level": 1,
//                        "order": 100,
//                        "stateRef": "user-management"
//                    },
//                ]
//            },
//            {
//                "name": "ug.md-html",
//                "title": "User Guide",
//                "level": 1,
//                "order": 200,
//                "stateRef": "ug.md-html"
//            },
//        ];
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
