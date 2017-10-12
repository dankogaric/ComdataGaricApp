(function() {
    'use strict';

    

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        
        $stateProvider
        .state('vehicle-by-parking', {
            parent: 'entity',
            url: '/vehicles-by-parking/:parkingId?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vehicles by Parking'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vehicle-by-parking/vehicles-by-parking.html',
                    controller: 'VehicleParkingController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        });
    }

})();
