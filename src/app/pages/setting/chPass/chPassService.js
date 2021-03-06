(function () {
    'use strict';

    angular.module('BlurAdmin.pages.setting')
            .factory('ChPassService', ChPassService);

    /** @ngInject */
    ChPassService.inject = ['$http', '$resource'];

    function ChPassService($http, $resource) {
        var url = 'api/master/toko';
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
            cariMerk: cariMerk,
            hapus: hapus,
            lookupToko: lookupToko,
            lookupMerk: lookupMerk,
            listKapalBerangkat: listKapalBerangkat,
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
        function cariSatu(id) {
            return $http.get(url + '/' + id);
        }
        function cariSemua() {
            return $http.get(url + '/all');
        }
        function cariMerk(id) {
            return $http.get(url + '/merk/findOne/'+id);
        }
        function listKapalBerangkat(id) {
            return $http.get(url + '/toko/list/kapal-berangkat/'+id);
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
        function lookupToko(s) {
            console.log('get', url + "/toko/lookup/" +s);
            return $http.get(url + "/toko/lookup/" + s);
            
        }
        function lookupMerk(ik, s) {
            console.log('get', url + "/merk/lookup/" + ik+'/'+s);
            return $http.get(url + "/merk/lookup/" + ik+'/'+s);
            
        }
    }

})();

