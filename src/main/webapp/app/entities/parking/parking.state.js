(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parking', {
            parent: 'entity',
            url: '/parking',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Parkings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parking/parkings.html',
                    controller: 'ParkingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('parking-detail', {
            parent: 'parking',
            url: '/parking/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Parking'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parking/parking-detail.html',
                    controller: 'ParkingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Parking', function($stateParams, Parking) {
                    return Parking.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parking',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parking-detail.edit', {
            parent: 'parking-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking/parking-dialog.html',
                    controller: 'ParkingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parking', function(Parking) {
                            return Parking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parking.new', {
            parent: 'parking',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking/parking-dialog.html',
                    controller: 'ParkingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                area: null,
                                height: null,
                                restArea: null,
                                hasRoof: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parking', null, { reload: 'parking' });
                }, function() {
                    $state.go('parking');
                });
            }]
        })
        .state('parking.edit', {
            parent: 'parking',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking/parking-dialog.html',
                    controller: 'ParkingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parking', function(Parking) {
                            return Parking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parking', null, { reload: 'parking' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parking.delete', {
            parent: 'parking',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking/parking-delete-dialog.html',
                    controller: 'ParkingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Parking', function(Parking) {
                            return Parking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parking', null, { reload: 'parking' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
