(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cabrio', {
            parent: 'entity',
            url: '/cabrio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cabrios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cabrio/cabrios.html',
                    controller: 'CabrioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('cabrio-detail', {
            parent: 'cabrio',
            url: '/cabrio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cabrio'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cabrio/cabrio-detail.html',
                    controller: 'CabrioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cabrio', function($stateParams, Cabrio) {
                    return Cabrio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cabrio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cabrio-detail.edit', {
            parent: 'cabrio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabrio/cabrio-dialog.html',
                    controller: 'CabrioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cabrio', function(Cabrio) {
                            return Cabrio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cabrio.new', {
            parent: 'cabrio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabrio/cabrio-dialog.html',
                    controller: 'CabrioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hasRemovableRoof: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cabrio', null, { reload: 'cabrio' });
                }, function() {
                    $state.go('cabrio');
                });
            }]
        })
        .state('cabrio.edit', {
            parent: 'cabrio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabrio/cabrio-dialog.html',
                    controller: 'CabrioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cabrio', function(Cabrio) {
                            return Cabrio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cabrio', null, { reload: 'cabrio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cabrio.delete', {
            parent: 'cabrio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabrio/cabrio-delete-dialog.html',
                    controller: 'CabrioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cabrio', function(Cabrio) {
                            return Cabrio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cabrio', null, { reload: 'cabrio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
