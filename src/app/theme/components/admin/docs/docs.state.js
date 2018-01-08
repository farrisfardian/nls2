(function() {
    'use strict';

    angular
        .module('BlurAdmin.theme.components')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('docs', {
            parent: 'admin',
            url: '/docs',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'API'
            },
            views: {
                'content@': {
                    templateUrl: 'app/theme/components/admin/docs/docs.html'
                }
            }
        });
    }
})();
