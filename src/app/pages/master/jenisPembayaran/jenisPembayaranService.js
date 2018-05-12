(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('JenisPembayaranService', JenisPembayaranService);

    /** @ngInject */
    JenisPembayaranService.inject = ['$http', '$resource'];

    function JenisPembayaranService($http, $resource) {
        var url = 'api/master/jenis-pembayaran';
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
        function cariSatu(column,id) {
            return $http.get(url + '/'+column+'/' + id);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
    }

})();

