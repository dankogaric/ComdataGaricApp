(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('AdditionalEquipmentDialogController', AdditionalEquipmentDialogController);

    AdditionalEquipmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AdditionalEquipment', 'Car'];

    function AdditionalEquipmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AdditionalEquipment, Car) {
        var vm = this;

        vm.additionalEquipment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.additionalEquipment.id !== null) {
                AdditionalEquipment.update(vm.additionalEquipment, onSaveSuccess, onSaveError);
            } else {
                AdditionalEquipment.save(vm.additionalEquipment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:additionalEquipmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
