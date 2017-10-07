(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('AdditionalEquipmentDetailController', AdditionalEquipmentDetailController);

    AdditionalEquipmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AdditionalEquipment', 'Car'];

    function AdditionalEquipmentDetailController($scope, $rootScope, $stateParams, previousState, entity, AdditionalEquipment, Car) {
        var vm = this;

        vm.additionalEquipment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:additionalEquipmentUpdate', function(event, result) {
            vm.additionalEquipment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
