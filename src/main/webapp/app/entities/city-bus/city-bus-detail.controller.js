(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CityBusDetailController', CityBusDetailController);

    CityBusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CityBus'];

    function CityBusDetailController($scope, $rootScope, $stateParams, previousState, entity, CityBus) {
        var vm = this;

        vm.cityBus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:cityBusUpdate', function(event, result) {
            vm.cityBus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
