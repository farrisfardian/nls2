(function () {
    'use strict';

    angular
            .module('BlurAdmin')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('app', {
                    abstract: true,
                    views: {
                        'content@': {
                            templateUrl: 'app/theme/components/login/login-page.html',
                            controller: 'LoginController',
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        authorize: ['Auth',
                            function (Auth) {
                                return Auth.authorize();
                            }
                        ]
                    }
                })
                .state('login', {
                    url: '/login',
                    title: 'Login',
                    views: {
                        'content@': {
                            templateUrl: 'app/theme/components/login/login-page.html',
                            controller: 'LoginController as vm',
                        }
                    },
                    data: {
                        loginRequired: false,
                        pageTitle: 'Daftar Akun'
                    },
                    sidebarMeta: {
                        order: 0,
                    },
                })
                ;
    }
})();
