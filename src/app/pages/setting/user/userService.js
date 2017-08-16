(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('UserService', UserService);

    /** @ngInject */
    UserService.inject = ['$http', '$resource'];

    function UserService($http, $resource) {
        var url = 'user';
        return {
            user: $resource('user/:id/filter/:search', {}, {
                query: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.user.query({"search": search, "page": p, "size": 10}, callback);
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            hapus: hapus,
            listMenuUser: listMenuUser,
            listSubMenuUser: listSubMenuUser,
            currentUser: currentUser,
            cekPassword: cekPassword,
            changePassword: changePassword
        }
        ;

        function simpan(obj) {
            if (obj.id == null || obj.id == undefined) {
                return $http.post(url, obj)
            } else {
                return $http.put(url + '/' + obj.id, obj)
            }
        }
        function cariSatu(id) {
            return $http.get(url + '/' + id);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
        function listMenuUser(userId) {
            return $http.get(url + '/menu-access/' + userId);
        }
        function listSubMenuUser(userId) {
            return $http.get(url + '/sub-menu-access/' + userId);
        }
        function currentUser() {
            return $http.get('homepage/userinfo');
        }
        function cekPassword(user, pass) {
            return $http.get(url + '/cek-pwd/' + user + '/' + pass);
        }
        function changePassword(user, pass) {
            return $http.put(url + '/ch-pass/' + user + '/' + pass);
        }

    }

})();

