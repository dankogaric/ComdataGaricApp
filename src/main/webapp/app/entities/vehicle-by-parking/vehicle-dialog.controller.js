(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('VehicleDialogController', VehicleDialogController);

    VehicleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vehicle', 'Manufacturer', 'Parking'];

    function VehicleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Vehicle, Manufacturer, Parking) {
        var vm = this;

        vm.vehicle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.manufacturers = Manufacturer.query();
        vm.parkings = Parking.query();

        vm.vehicleTypes = ["CLASSIC_CAR", "CABRIO", "CITY_BUS", "INTERCITY_BUS", "TRUCTOR_TRUCK", "TANK_TRUCK"];


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vehicle.id !== null) {
                Vehicle.update(vm.vehicle, onSaveSuccess, onSaveError);
            } else {
                Vehicle.save(vm.vehicle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:vehicleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
