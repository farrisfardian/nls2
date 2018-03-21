(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('NotaService', NotaService);

    /** @ngInject */
    NotaService.inject = ['$http', '$resource'];

    function NotaService($http, $resource) {
        var url = 'api/transaksi/nota';
        return {
            entity: $resource(url + '/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            query: function (search, p, callback) {
                return this.entity.queryPage({"a": search, "page": p, "size": 10}, callback)
            },
            entityComposite: $resource(url + '/tglawal/tglakhir/cari/:tglawal/:tglakhir/:a', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryComposite: function (tglawal, tglakhir, search, p, callback) {
                return this.entityComposite.queryPage({"a": search, "tglawal": tglawal, "tglakhir": tglakhir, "page": p, "size": 10}, callback)
            },
            getTagihanTerbayar: $resource(url + '/tglAwal/tglAkhir/cari/idToko/idMerk/status/:tglawal/:tglakhir/:a/:idToko/:idMerk/:status', {}, {
                queryPage: {method: 'GET', isArray: false}
            }),
            queryGetTagihanTerbayar: function (tglawal, tglakhir, search, idToko, idMerk, status, p, callback) {
                return this.getTagihanTerbayar.queryPage({"a": search, "tglawal": tglawal, "tglakhir": tglakhir, "idToko": idToko, "idMerk": idMerk, "status": status, "page": p, "size": 10}, callback)
            },
            simpan: simpan,
            cariSatu: cariSatu,
            lookup: lookup,
            hapus: hapus,
            listDetailNotaTokoMerkTujuan: listDetailNotaTokoMerkTujuan,
            listSubtotalDetailNotaTokoMerkTujuan: listSubtotalDetailNotaTokoMerkTujuan,
            getTagihanTerbayarMulti: getTagihanTerbayarMulti
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
        function lookup(ik) {
            return $http.get(url + "/aktif/lookup/" + ik);
        }
        function listDetailNotaTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat) {
            console.log('toko: ' + idTokoTujuan + ', merk: ' + idMerkTujuan + ', kapalBerangkat: ' + idKapalBerangkat)
            return $http.get(url + "/gen-detail-nota/" + idTokoTujuan + "/" + idMerkTujuan + "/" + idKapalBerangkat);
        }
        function listSubtotalDetailNotaTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat) {
            console.log('toko: ' + idTokoTujuan + ', merk: ' + idMerkTujuan + ', kapalBerangkat: ' + idKapalBerangkat);
            return $http.get(url + "/gen-subtotal-detail-nota/" + idTokoTujuan + "/" + idMerkTujuan + "/" + idKapalBerangkat);
        }
        function getTagihanTerbayarMulti(idTokoTujuan, idMerkTujuan, status, idNotas) {
            console.log('toko: ' + idTokoTujuan + ', merk: ' + idMerkTujuan + ', status: ' + status + ', idNotas: ' + idNotas);
            return $http.get(url + "/get-tagihan-terbayar-multi/" + idTokoTujuan + "/" + idMerkTujuan + "/" + status + "/" + idNotas);
        }
        function hapus(id) {
            return $http.delete(url + '/' + id);
        }
    }

})();

