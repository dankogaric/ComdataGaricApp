(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('BusDetailController', BusDetailController);

    BusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bus'];

    function BusDetailController($scope, $rootScope, $stateParams, previousState, entity, Bus) {
        var vm = this;

        vm.bus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:busUpdate', function(event, result) {
            vm.bus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
