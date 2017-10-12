(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('VehicleDetailController', VehicleDetailController);

    VehicleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vehicle', 'Manufacturer', 'Parking'];

    function VehicleDetailController($scope, $rootScope, $stateParams, previousState, entity, Vehicle, Manufacturer, Parking) {
        var vm = this;

        vm.vehicle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:vehicleUpdate', function(event, result) {
            vm.vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
