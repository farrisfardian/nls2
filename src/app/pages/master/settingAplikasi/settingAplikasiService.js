'use strict';

angular.module('BlurAdmin')
  .factory('SettingAplikasiService', function($http, $resource) {
            var url = 'api/master/setting-aplikasi';
            return {
                jm: $resource(url + '/:search', {}, {
                    queryPage: {method: 'GET', isArray: false}
                }),
                cariSatu: function (column, value) {
                    return $http.get(url + '/' + column + '/' + value)
                },
                query: function (search, p, callback) {
                    console.log(p);
                    return this.jm.queryPage({"search": search, "page.page": p, "page.size": 10}, callback);
                },
                simpan: function (obj) {
                    if (obj.id === null || obj.id === undefined) {
                        return $http.post(url, obj);
                    } else {
                        return $http.put(url+'/'+obj.id, obj);
                    }
                },
                hapus: function (obj) {
                    if (obj.kode != null) {
                        return $http.delete(url + "/" + obj.kode);
                    }
                },
                findByNama: function (value) {
                    return $http.get(url + '/cek-nama/' + value);
                },
                findAll: function () {
                    return $http.get(url + '/all');
                },
                count: function () {
                    return $http.get(url + '/count');
                }
            }
        });

