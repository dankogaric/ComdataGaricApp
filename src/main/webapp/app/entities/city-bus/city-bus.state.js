(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('city-bus', {
            parent: 'entity',
            url: '/city-bus?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CityBuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/city-bus/city-buses.html',
                    controller: 'CityBusController',
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
        })
        .state('city-bus-detail', {
            parent: 'city-bus',
            url: '/city-bus/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CityBus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/city-bus/city-bus-detail.html',
                    controller: 'CityBusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CityBus', function($stateParams, CityBus) {
                    return CityBus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'city-bus',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('city-bus-detail.edit', {
            parent: 'city-bus-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-bus/city-bus-dialog.html',
                    controller: 'CityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CityBus', function(CityBus) {
                            return CityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('city-bus.new', {
            parent: 'city-bus',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-bus/city-bus-dialog.html',
                    controller: 'CityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hasWhrist: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('city-bus', null, { reload: 'city-bus' });
                }, function() {
                    $state.go('city-bus');
                });
            }]
        })
        .state('city-bus.edit', {
            parent: 'city-bus',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-bus/city-bus-dialog.html',
                    controller: 'CityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CityBus', function(CityBus) {
                            return CityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('city-bus', null, { reload: 'city-bus' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('city-bus.delete', {
            parent: 'city-bus',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-bus/city-bus-delete-dialog.html',
                    controller: 'CityBusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CityBus', function(CityBus) {
                            return CityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('city-bus', null, { reload: 'city-bus' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
