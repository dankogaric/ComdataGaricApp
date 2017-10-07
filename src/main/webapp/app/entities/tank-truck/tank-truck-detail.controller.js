(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TankTruckDetailController', TankTruckDetailController);

    TankTruckDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TankTruck'];

    function TankTruckDetailController($scope, $rootScope, $stateParams, previousState, entity, TankTruck) {
        var vm = this;

        vm.tankTruck = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:tankTruckUpdate', function(event, result) {
            vm.tankTruck = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
