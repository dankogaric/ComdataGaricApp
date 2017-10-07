(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tructor-truck', {
            parent: 'entity',
            url: '/tructor-truck',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TructorTrucks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tructor-truck/tructor-trucks.html',
                    controller: 'TructorTruckController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tructor-truck-detail', {
            parent: 'tructor-truck',
            url: '/tructor-truck/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TructorTruck'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tructor-truck/tructor-truck-detail.html',
                    controller: 'TructorTruckDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TructorTruck', function($stateParams, TructorTruck) {
                    return TructorTruck.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tructor-truck',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tructor-truck-detail.edit', {
            parent: 'tructor-truck-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tructor-truck/tructor-truck-dialog.html',
                    controller: 'TructorTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TructorTruck', function(TructorTruck) {
                            return TructorTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tructor-truck.new', {
            parent: 'tructor-truck',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tructor-truck/tructor-truck-dialog.html',
                    controller: 'TructorTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horsePower: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tructor-truck', null, { reload: 'tructor-truck' });
                }, function() {
                    $state.go('tructor-truck');
                });
            }]
        })
        .state('tructor-truck.edit', {
            parent: 'tructor-truck',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tructor-truck/tructor-truck-dialog.html',
                    controller: 'TructorTruckDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TructorTruck', function(TructorTruck) {
                            return TructorTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tructor-truck', null, { reload: 'tructor-truck' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tructor-truck.delete', {
            parent: 'tructor-truck',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tructor-truck/tructor-truck-delete-dialog.html',
                    controller: 'TructorTruckDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TructorTruck', function(TructorTruck) {
                            return TructorTruck.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tructor-truck', null, { reload: 'tructor-truck' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
