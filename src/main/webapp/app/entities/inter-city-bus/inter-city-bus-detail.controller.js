(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('InterCityBusDetailController', InterCityBusDetailController);

    InterCityBusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InterCityBus'];

    function InterCityBusDetailController($scope, $rootScope, $stateParams, previousState, entity, InterCityBus) {
        var vm = this;

        vm.interCityBus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:interCityBusUpdate', function(event, result) {
            vm.interCityBus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
