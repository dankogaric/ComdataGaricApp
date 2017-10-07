(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tank-truck', {
            parent: 'entity',
            url: '/tank-truck?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TankTrucks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tank-truck/tank-trucks.html',
                    controller: 'TankTruckController',
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
        .state('tank-truck-detail', {
            parent: 'tank-truck',
            url: '/tank-truck/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TankTruck'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tank-truck/tank-truck-detail.html',
                    controller: 'TankTruckDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TankTruck', function($stateParams, TankTruck) {
                    return TankTruck.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tank-truck',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tank-truck-detail.edit', {
            parent: 'tank-truck-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tank-truck/tank-truck-dialog.html',
                    controller: 'TankTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TankTruck', function(TankTruck) {
                            return TankTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tank-truck.new', {
            parent: 'tank-truck',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tank-truck/tank-truck-dialog.html',
                    controller: 'TankTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tankCapacity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tank-truck', null, { reload: 'tank-truck' });
                }, function() {
                    $state.go('tank-truck');
                });
            }]
        })
        .state('tank-truck.edit', {
            parent: 'tank-truck',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tank-truck/tank-truck-dialog.html',
                    controller: 'TankTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TankTruck', function(TankTruck) {
                            return TankTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tank-truck', null, { reload: 'tank-truck' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tank-truck.delete', {
            parent: 'tank-truck',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tank-truck/tank-truck-delete-dialog.html',
                    controller: 'TankTruckDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TankTruck', function(TankTruck) {
                            return TankTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tank-truck', null, { reload: 'tank-truck' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
