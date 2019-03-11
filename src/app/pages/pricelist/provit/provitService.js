(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('ProvitService', ProvitService);

    /** @ngInject */
    ProvitService.inject = ['$http', '$resource'];

    function ProvitService($http, $resource) {
        var url = 'api/setting/provit';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/idKotaAsal/tglBerlaku/:idKotaAsal/:tglBerlaku', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (idKotaAsal, tglBerlaku, p, callback) {
                return this.entityComposite.queryPage({"idKotaAsal": idKotaAsal, "tglBerlaku": tglBerlaku, "page": p, "size": 10}, callback)
            },
            simpan: simpan,
            cariSatu: cariSatu,
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
        function cariSatu(column, id) {
            return $http.get(url + '/' + column + '/' + id);
        }
        function hapus(id) {
            return $http.delete(url + '/' + id);
        }
    }

})();

