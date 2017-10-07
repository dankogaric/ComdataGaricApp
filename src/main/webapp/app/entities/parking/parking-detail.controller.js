(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ParkingDetailController', ParkingDetailController);

    ParkingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Parking'];

    function ParkingDetailController($scope, $rootScope, $stateParams, previousState, entity, Parking) {
        var vm = this;

        vm.parking = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:parkingUpdate', function(event, result) {
            vm.parking = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
