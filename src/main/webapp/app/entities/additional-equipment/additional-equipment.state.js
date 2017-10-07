(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('additional-equipment', {
            parent: 'entity',
            url: '/additional-equipment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AdditionalEquipments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-equipment/additional-equipments.html',
                    controller: 'AdditionalEquipmentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('additional-equipment-detail', {
            parent: 'additional-equipment',
            url: '/additional-equipment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AdditionalEquipment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-equipment/additional-equipment-detail.html',
                    controller: 'AdditionalEquipmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AdditionalEquipment', function($stateParams, AdditionalEquipment) {
                    return AdditionalEquipment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'additional-equipment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('additional-equipment-detail.edit', {
            parent: 'additional-equipment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-equipment/additional-equipment-dialog.html',
                    controller: 'AdditionalEquipmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalEquipment', function(AdditionalEquipment) {
                            return AdditionalEquipment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-equipment.new', {
            parent: 'additional-equipment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-equipment/additional-equipment-dialog.html',
                    controller: 'AdditionalEquipmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hasAbs: null,
                                hasEsp: null,
                                hasGlassRoof: null,
                                hasAluWheels: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('additional-equipment', null, { reload: 'additional-equipment' });
                }, function() {
                    $state.go('additional-equipment');
                });
            }]
        })
        .state('additional-equipment.edit', {
            parent: 'additional-equipment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-equipment/additional-equipment-dialog.html',
                    controller: 'AdditionalEquipmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalEquipment', function(AdditionalEquipment) {
                            return AdditionalEquipment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-equipment', null, { reload: 'additional-equipment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-equipment.delete', {
            parent: 'additional-equipment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-equipment/additional-equipment-delete-dialog.html',
                    controller: 'AdditionalEquipmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AdditionalEquipment', function(AdditionalEquipment) {
                            return AdditionalEquipment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-equipment', null, { reload: 'additional-equipment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
