(function () {
    'use strict';

    angular.module('BlurAdmin')
            .factory('EnumService', EnumService);

    /** @ngInject */
    EnumService.inject = ['$http', '$resource'];

    function EnumService($http, $resource) {
        var url = 'api/master/enum';
        return {
            getUkuranPaket: getUkuranPaket,
            getUkuranKontainer: getUkuranKontainer,
            getJenisStuffing: getJenisStuffing
        }

//        return service;

        ;

        function getUkuranPaket() {
            return $http.get(url + '/ukuran-paket');
        }
        
        function getUkuranKontainer() {
            return $http.get(url + '/ukuran-kontainer');
        }
        function getJenisStuffing() {
            return $http.get(url + '/jenis-stuffing');
        }
    }

})();

