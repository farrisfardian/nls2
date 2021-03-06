(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('MenuService', MenuService);

    /** @ngInject */
    MenuService.inject = ['$http', '$resource'];

    function MenuService($http, $resource) {
        var url = 'api/setting/menu';
        return {
            menu: $resource(url+'/:search', {}, {
                query: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.menu.query({"search": search, "page": p, "size": 10}, callback);
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            cariSemuaParent: cariSemuaParent,
            findByIdNotIn: findByIdNotIn,
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
        function cariSatu(id) {
            return $http.get(url + '/' + id);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function cariSemuaParent() {
            return $http.get(url + '/all-parent');
        }
        function findByIdNotIn(ids) {
            return $http.get(url + '/not-in/'+ids);
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
    }

})();

