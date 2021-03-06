(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TruckDetailController', TruckDetailController);

    TruckDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Truck'];

    function TruckDetailController($scope, $rootScope, $stateParams, previousState, entity, Truck) {
        var vm = this;

        vm.truck = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:truckUpdate', function(event, result) {
            vm.truck = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
