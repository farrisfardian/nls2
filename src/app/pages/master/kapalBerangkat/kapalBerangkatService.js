(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('KapalBerangkatService', KapalBerangkatService);

    /** @ngInject */
    KapalBerangkatService.inject = ['$http', '$resource'];

    function KapalBerangkatService($http, $resource) {
        var url = 'api/master/kapal-berangkat';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/tglawal/tglakhir/idkota/idkapal/cari/tglnull/:tglawal/:tglakhir/:idkota/:idkapal/:a/:tglnull', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (tglawal, tglakhir, idkota, idkapal, search, tglnull, p, callback) {
                return this.entityComposite.queryPage({"a": search, "tglawal": tglawal, "tglakhir": tglakhir, "idkota": idkota, "idkapal": idkapal, "tglnull": tglnull, "page": p, "size": 10}, callback)
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            hapus: hapus,
            filterByIdKotaAndAktif: filterByIdKotaAndAktif,
            filterByTokoMerkTujuan: filterByTokoMerkTujuan
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
        function filterByIdKotaAndAktif(idKota) {
            return $http.get(url + '/all-aktif-by-kota/' + idKota);
        }
        function filterByTokoMerkTujuan(idToko, idMerk) {
            console.log(url + '/idToko/idMerk/' + idToko + '/' + idMerk)
            return $http.get(url + '/idToko/idMerk/' + idToko + '/' + idMerk);
        }
        function hapus(obj) {
            if (obj.id != null) {
                return $http.delete(url + "/" + obj.id);
            }
        }
    }

})();

