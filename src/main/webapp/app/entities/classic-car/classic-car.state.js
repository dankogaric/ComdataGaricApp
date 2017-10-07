(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('classic-car', {
            parent: 'entity',
            url: '/classic-car',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ClassicCars'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classic-car/classic-cars.html',
                    controller: 'ClassicCarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('classic-car-detail', {
            parent: 'classic-car',
            url: '/classic-car/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ClassicCar'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classic-car/classic-car-detail.html',
                    controller: 'ClassicCarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ClassicCar', function($stateParams, ClassicCar) {
                    return ClassicCar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'classic-car',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('classic-car-detail.edit', {
            parent: 'classic-car-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classic-car/classic-car-dialog.html',
                    controller: 'ClassicCarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassicCar', function(ClassicCar) {
                            return ClassicCar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classic-car.new', {
            parent: 'classic-car',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classic-car/classic-car-dialog.html',
                    controller: 'ClassicCarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                roofTopCapacity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('classic-car', null, { reload: 'classic-car' });
                }, function() {
                    $state.go('classic-car');
                });
            }]
        })
        .state('classic-car.edit', {
            parent: 'classic-car',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classic-car/classic-car-dialog.html',
                    controller: 'ClassicCarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassicCar', function(ClassicCar) {
                            return ClassicCar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classic-car', null, { reload: 'classic-car' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classic-car.delete', {
            parent: 'classic-car',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classic-car/classic-car-delete-dialog.html',
                    controller: 'ClassicCarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassicCar', function(ClassicCar) {
                            return ClassicCar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classic-car', null, { reload: 'classic-car' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
