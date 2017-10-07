(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CabrioDetailController', CabrioDetailController);

    CabrioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cabrio'];

    function CabrioDetailController($scope, $rootScope, $stateParams, previousState, entity, Cabrio) {
        var vm = this;

        vm.cabrio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:cabrioUpdate', function(event, result) {
            vm.cabrio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
