/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.setting', ['ui.select', 'ngSanitize'])
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
                .state('setting', {
                    parent: 'app',
                    url: '/setting',
                    template: '<ui-view autoscroll="true" autoscroll-body-top></ui-view>',
                    abstract: true,
                    title: 'Setting',
                    sidebarMeta: {
                        icon: 'ion-compose',
                        order: 100,
                    },
                })
                //change password
                .state('setting.ch-pass', {
                    url: '/ch-pass',
                    templateUrl: 'app/pages/setting/chPass/ch-pass.html',
                    controller: 'ChPassController',
                    title: 'Ganti Password',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                //user
                .state('setting.user', {
                    url: '/user',
                    templateUrl: 'app/pages/setting/user/user.html',
                    controller: 'UserController',
                    title: 'User',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                //menu
                .state('setting.menu', {
                    url: '/menu',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/setting/menu/menu.html',
                            controller: 'MenuController',
                        }
                    },
                    title: 'Menu',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                //permission
                .state('setting.permission', {
                    url: '/permission',
                    templateUrl: 'app/pages/setting/permission/permission.html',
                    controller: 'PermissionController',
                    title: 'Permission',
                    sidebarMeta: {
                        order: 0,
                    },
                })
                //role
                .state('setting.role', {
                    url: '/role',
                    views: {
                        'content@': {
                            templateUrl: 'app/pages/setting/role/role.html',
                            controller: 'RoleController',
                        }
                    },
                    title: 'Role',
                    sidebarMeta: {
                        order: 0,
                    },
                })
    }
})();
