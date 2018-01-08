(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('SettingHargaService', SettingHargaService);

    /** @ngInject */
    SettingHargaService.inject = ['$http', '$resource'];

    function SettingHargaService($http, $resource) {
        var url = 'api/master/setting-harga';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/idKota/idToko/idKondisi/tglBerlaku/:idKota/:idToko/:idKondisi/:tglBerlaku', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (idKota, idToko, idKondisi, tglBerlaku, p, callback) {
                return this.entityComposite.queryPage({"idKota": idKota, "idToko": idToko, "idKondisi": idKondisi, "tglBerlaku": tglBerlaku, "page": p, "size": 10}, callback)
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

