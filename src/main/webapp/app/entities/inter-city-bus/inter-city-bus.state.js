(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('inter-city-bus', {
            parent: 'entity',
            url: '/inter-city-bus',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InterCityBuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inter-city-bus/inter-city-buses.html',
                    controller: 'InterCityBusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('inter-city-bus-detail', {
            parent: 'inter-city-bus',
            url: '/inter-city-bus/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InterCityBus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inter-city-bus/inter-city-bus-detail.html',
                    controller: 'InterCityBusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InterCityBus', function($stateParams, InterCityBus) {
                    return InterCityBus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'inter-city-bus',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('inter-city-bus-detail.edit', {
            parent: 'inter-city-bus-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inter-city-bus/inter-city-bus-dialog.html',
                    controller: 'InterCityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InterCityBus', function(InterCityBus) {
                            return InterCityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inter-city-bus.new', {
            parent: 'inter-city-bus',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inter-city-bus/inter-city-bus-dialog.html',
                    controller: 'InterCityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                trunkCapacity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('inter-city-bus', null, { reload: 'inter-city-bus' });
                }, function() {
                    $state.go('inter-city-bus');
                });
            }]
        })
        .state('inter-city-bus.edit', {
            parent: 'inter-city-bus',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inter-city-bus/inter-city-bus-dialog.html',
                    controller: 'InterCityBusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InterCityBus', function(InterCityBus) {
                            return InterCityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inter-city-bus', null, { reload: 'inter-city-bus' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inter-city-bus.delete', {
            parent: 'inter-city-bus',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inter-city-bus/inter-city-bus-delete-dialog.html',
                    controller: 'InterCityBusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InterCityBus', function(InterCityBus) {
                            return InterCityBus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inter-city-bus', null, { reload: 'inter-city-bus' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
