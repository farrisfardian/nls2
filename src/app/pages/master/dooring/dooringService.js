(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('DooringService', DooringService);

    /** @ngInject */
    DooringService.inject = ['$http', '$resource'];

    function DooringService($http, $resource) {
        var url = 'api/setting/dooring';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/idKotaAsal/idKotaTujuan/tglBerlaku/:idKotaAsal/:idKotaTujuan/:tglBerlaku', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (idKotaAsal, idKotaTujuan, tglBerlaku, p, callback) {
                return this.entityComposite.queryPage({"idKotaAsal": idKotaAsal, "idKotaTujuan": idKotaTujuan, "tglBerlaku": tglBerlaku, "page": p, "size": 10}, callback)
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

