(function () {
    'use strict';

    angular
            .module('BlurAdmin')
            .controller('PageTopCtrl', PageTopCtrl);

    PageTopCtrl.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', '$rootScope', '$timeout', '$scope'];

    function PageTopCtrl($state, Auth, Principal, ProfileService, $rootScope, $timeout, $scope) {
        var vm = this;
        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;
        vm.cekAuth = cekAuth;

        $timeout(function () {
            angular.element('#username').focus();
        }, 200);

        function cancel() {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
        }
        $scope.$on('authenticationSuccess', function () {
            console.log('menjalankan getAccount()')
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function login(event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                        $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('dashboard');
                    vm.isNavbarCollapsed = false;
                }

//                $rootScope.$broadcast('BlurAdmin:refreshMenu', {});

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                console.log('Auth.getPreviousState()', Auth.getPreviousState());
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    console.log('Auth.getPreviousState()', previousState);
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function requestResetPassword() {
            $state.go('requestReset');
        }

        function logout() {
            console.log('logout');
            collapseNavbar();
            Auth.logout();
            $state.go('login');
//            $rootScope.$broadcast('BlurAdmin:refreshMenu', {});
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function cekAuth() {
            console.log('cekAuth');
            alert(Principal.isAuthenticated());
        }
    }
})();
