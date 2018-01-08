(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('KategoriHargaService', KategoriHargaService);

    /** @ngInject */
    KategoriHargaService.inject = ['$http', '$resource'];

    function KategoriHargaService($http, $resource) {
        var url = 'api/master/kategori-harga';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/tglawal/tglakhir/idkota/idkapal/cari/:tglawal/:tglakhir/:idkota/:idkapal/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (tglawal, tglakhir, idkota, idkapal, search, p, callback) {
                return this.entityComposite.queryPage({"a": search, "tglawal": tglawal, "tglakhir": tglakhir, "idkota": idkota, "idkapal": idkapal, "page": p, "size": 10}, callback)
            },
            simpan: simpan,
            cariSatu: cariSatu,
            cariSemua: cariSemua,
            filterPaketSatuanKirim: filterPaketSatuanKirim,
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
        function filterPaketSatuanKirim(paket, satuanKirim) {
            return $http.get(url + '/filter-paket-satuan-kirim/' + paket + '/' + satuanKirim);
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

