(function () {
    'use strict';

    angular.module('BlurAdmin.pages.laporan')
            .controller('DashboardCtrl', DashboardCtrl)
    function DashboardCtrl($scope, $filter, DashboardService) {
        $scope.d = new Date();
        $scope.d.setMonth($scope.d.getMonth() - 1);
        $scope.options = {
            tgl1: $scope.d,
            tgl2: new Date(),
            grup: {name: 'Kota Tujuan', value: 'kota_tujuan'},
            order: {name: 'Kontainer Terbesar', value: 'kontainer_desc'},
            limit: 6
        };
        $scope.optionsNota = {
            tgl1: $scope.d,
            tgl2: new Date(),
            grup: {name: 'Kota Tujuan', value: 'kota_tujuan'},
            order: {name: 'Tagihan Terbesar', value: 'tagihan_desc'},
            limit: 6
        };
        $scope.optionsTgl = {format: 'DD/MM/YYYY', showClear: false};
        $scope.listGrup = [
            {name: 'Kota Tujuan', value: 'kota_tujuan'},
            {name: 'Kondisi', value: 'kondisi'},
            {name: 'Customer', value: 'customer'},
            {name: 'Kapal', value: 'kapal'},
//            {name: 'Kontainer', value: 'nomor_kontainer'},
            {name: 'Emkl', value: 'emkl'},
            {name: 'Pengirim', value: 'pengirim'}
        ];
        $scope.listOrder = [
            {name: 'Nama Asc', value: 'grup_asc'},
            {name: 'Nama Desc', value: 'grup_desc'},
            {name: 'Coli Terbesar', value: 'coli_desc'},
            {name: 'Coli Terkecil', value: 'coli_asc'},
            {name: 'Kubikasi Terbesar', value: 'kubikasi_desc'},
            {name: 'Kubikasi Terkecil', value: 'kubikasi_asc'},
            {name: 'Kontainer Terbesar', value: 'kontainer_desc'},
            {name: 'Kontainer Terkecil', value: 'kontainer_asc'},
        ];
        $scope.listOrder2 = [
            {name: 'Tagihan Terbesar', value: 'tagihan_desc'},
            {name: 'Tagihan Terkecil', value: 'tagihan_asc'},
            {name: 'Pembayaran Terbesar', value: 'terbayar_desc'},
            {name: 'Pembayaran Terkecil', value: 'terbayar_asc'},
            {name: 'Sisa Terbesar', value: 'sisa_desc'},
            {name: 'Sisa Terkecil', value: 'sisa_asc'},
            {name: 'Pros.Bayar Terbesar', value: 'pros_bayar_desc'},
            {name: 'Pros.Bayar Terkecil', value: 'pros_bayar_asc'},
        ];

        Highcharts.setOptions({
            lang: {
                decimalPoint: ',',
                thousandsSep: '.',
                numericSymbols: [' ribu', ' juta', ' milyar']
            }
        });
        $scope.initGrupColiKubikasiChart = function (series, categories, grup, idContainer) {
            $scope.grupColiKubikasiChart = Highcharts.chart(idContainer, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Rekap Coli & Kubikasi berdasarkan ' + grup
                },
                subtitle: {
                    text: 'Sumber : Data Transaksi NLS'
                },
                xAxis: {
                    categories: categories,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Jumlah'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:,.1f}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: series
            });
        };
        $scope.initGrupKontainerChart = function (series, categories, grup, idContainer) {
            $scope.grupKontainerChart = Highcharts.chart(idContainer, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Rekap Kontainer berdasarkan ' + grup
                },
                subtitle: {
                    text: 'Sumber : Data Transaksi NLS'
                },
                xAxis: {
                    categories: categories,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Jumlah'
                    }
                },
                colors: ['#990000'],
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            color: '#ffffff',
                            formatter: function () {
                                if (this.y > 1000000)
                                    return Highcharts.numberFormat(this.y / 1000000, 1) + "m";  // maybe only switch if > 1000
                                //if ( this.y > 10000000 ) return Highcharts.numberFormat( this.y/10000000, 1) + "b"; 
                                return Highcharts.numberFormat(this.y, 0);
                            }

                        }
                    }
                },
                series: series
            });
        };

        $scope.initGrupTagihanChart = function (series, categories, grup, idContainer) {
            $scope.grupTagihanChart = Highcharts.chart(idContainer, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Rekap Tagihan, Terbayar & Sisa berdasarkan ' + grup
                },
                subtitle: {
                    text: 'Sumber : Data Transaksi NLS'
                },
                xAxis: {
                    categories: categories,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Jumlah'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:,.1f}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    },
                },
                series: series
            });
        };
        $scope.initGrupProsChart = function (series, categories, grup, idContainer) {
            $scope.grupprosChart = Highcharts.chart(idContainer, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Rekap Prosentase Terbayar berdasarkan ' + grup
                },
                subtitle: {
                    text: 'Sumber : Data Transaksi NLS'
                },
                xAxis: {
                    categories: categories,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    max: 100,
                    title: {
                        text: 'Jumlah'
                    }
                },
                colors: ['#990000'],
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            color: '#ffffff',
                            formatter: function () {
                                if (this.y > 1000000)
                                    return Highcharts.numberFormat(this.y / 1000000, 1) + "m";  // maybe only switch if > 1000
                                //if ( this.y > 10000000 ) return Highcharts.numberFormat( this.y/10000000, 1) + "b"; 
                                return Highcharts.numberFormat(this.y, 1);
                            }

                        }
                    }
                },
                series: series
            });
        };

        $scope.reloadData = function () {
            $scope.param = {
                tgl1: $filter('date')(new Date($scope.options.tgl1), 'yyyy-MM-dd'),
                tgl2: $filter('date')(new Date($scope.options.tgl2), 'yyyy-MM-dd'),
                grup: $scope.options.grup.value,
                order: $scope.options.order.value,
                limit: $scope.options.limit
            };
            console.log('$scope.param', $scope.param);
            DashboardService.rekapColiKubikasi($scope.param).success(function (data) {
                $scope.categories = [];
                $scope.categories2 = [];
                $scope.seriesColi = {
                    name: 'Coli',
                    data: []
                };
                $scope.seriesKubikasi = {
                    name: 'Kubikasi (metrik kubik)',
                    data: []
                };
                $scope.seriesKontainer = {
                    name: 'Kontainer',
                    data: []
                };
                for (var i = 0; i < data.length; i++) {
                    $scope.categories.push(data[i].grup);
                    $scope.categories2.push(data[i].grup);
                    $scope.seriesColi.data.push(data[i].coli);
                    $scope.seriesKubikasi.data.push(data[i].kubikasi);
                    $scope.seriesKontainer.data.push(data[i].kontainer);
                }
                $scope.listSeries = [];
                $scope.listSeries2 = [];
                $scope.listSeries.push($scope.seriesColi);
                $scope.listSeries.push($scope.seriesKubikasi);
                $scope.listSeries2.push($scope.seriesKontainer);
                console.log('$scope.categories', $scope.categories);
                console.log('$scope.listSeries', $scope.listSeries);
                $scope.initGrupColiKubikasiChart($scope.listSeries, $scope.categories, $scope.options.grup.name, 'grupColiKubikasiChart');
                $scope.initGrupKontainerChart($scope.listSeries2, $scope.categories2, $scope.options.grup.name, 'grupKontainerChart');
            });
        };

        $scope.reloadDataNota = function () {
            $scope.paramNota = {
                tgl1: $filter('date')(new Date($scope.optionsNota.tgl1), 'yyyy-MM-dd'),
                tgl2: $filter('date')(new Date($scope.optionsNota.tgl2), 'yyyy-MM-dd'),
                order: $scope.optionsNota.order.value,
                limit: $scope.optionsNota.limit
            };
            console.log('$scope.param', $scope.paramNota);
            DashboardService.rekapTerbayarTagihan($scope.paramNota).success(function (data) {
                $scope.categoriesNota = [];
                $scope.categoriesNota2 = [];
                $scope.seriesTagihan = {
                    name: 'Tagihan',
                    data: []
                };
                $scope.seriesTerbayar = {
                    name: 'Terbayar',
                    data: []
                };
                $scope.seriesSisa = {
                    name: 'Sisa',
                    data: []
                };
                $scope.seriesPros = {
                    name: 'Prosentase',
                    data: []
                };
                for (var i = 0; i < data.length; i++) {
                    $scope.categoriesNota.push(data[i].grup);
                    $scope.categoriesNota2.push(data[i].grup);
                    $scope.seriesTagihan.data.push(data[i].tagihan);
                    $scope.seriesTerbayar.data.push(data[i].terbayar);
                    $scope.seriesSisa.data.push(data[i].sisa);
                    $scope.seriesPros.data.push(data[i].pros_bayar);
                }
                $scope.listSeriesNota = [];
                $scope.listSeriesNota2 = [];
                $scope.listSeriesNota.push($scope.seriesTagihan);
                $scope.listSeriesNota.push($scope.seriesTerbayar);
                $scope.listSeriesNota.push($scope.seriesSisa);
                $scope.listSeriesNota2.push($scope.seriesPros);
                console.log('$scope.categoriesNota', $scope.categoriesNota);
                console.log('$scope.listSeriesNota', $scope.listSeriesNota);
                $scope.initGrupTagihanChart($scope.listSeriesNota, $scope.categoriesNota, 'Toko', 'grupTagihanChart');
                $scope.initGrupProsChart($scope.listSeriesNota2, $scope.categoriesNota2, 'Toko', 'grupProsChart');
            });
        };

        $scope.$watchGroup(['options.grup', 'options.order', 'options.tgl1', 'options.tgl2', 'options.limit'], function (newValues, oldValues, scope) {
            $scope.reloadData();
        });

        $scope.$watchGroup(['optionsNota.order', 'optionsNota.tgl1', 'optionsNota.tgl2', 'optionsNota.limit'], function (newValues, oldValues, scope) {
            $scope.reloadDataNota();
        });

    }
})();