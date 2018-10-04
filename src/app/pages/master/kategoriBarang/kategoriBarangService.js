(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('KategoriBarangService', KategoriBarangService);

    /** @ngInject */
    KategoriBarangService.inject = ['$http', '$resource'];

    function KategoriBarangService($http, $resource) {
        var url = 'api/master/kategori-barang';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            lookup: lookup,
            hapus: hapus,
        }

//        return service;

        ;

        function simpan(data, ori) {
            if (ori.id == null || ori.id == undefined) {
                return $http.post(url, data)
            } else {
                return $http.put(url + '/' + ori.id, data)
            }
        }
        function cariSatu(column, value) {
            return $http.get(url + '/' + column + '/' + value);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function lookup(s) {
            return $http.get(url + '/kategori/lookup/' + s);
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
    }

})();

