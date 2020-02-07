(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('RoleService', RoleService);

    /** @ngInject */
    RoleService.inject = ['$http', '$resource'];

    function RoleService($http, $resource) {
        var url = 'api/setting/role';
        return {
            menu: $resource(url + '/:search', {}, {
                query: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.menu.query({"search": search, "page": p, "size": 10}, callback);
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            cariSemuaParent: cariSemuaParent,
            hapus: hapus
        }
        ;

        function simpan(obj) {
            if (obj.id == null || obj.id == undefined) {
                return $http.post(url, obj)
            } else {
                return $http.put(url + '/' + obj.id, obj)
            }
        }
        function cariSatu(col, id) {
            return $http.get(url + '/' + col + '/' + id);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function cariSemuaParent() {
            return $http.get(url + '/all-parent');
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
    }

})();

