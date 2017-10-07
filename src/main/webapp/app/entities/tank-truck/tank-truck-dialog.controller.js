(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TankTruckDialogController', TankTruckDialogController);

    TankTruckDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TankTruck'];

    function TankTruckDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TankTruck) {
        var vm = this;

        vm.tankTruck = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tankTruck.id !== null) {
                TankTruck.update(vm.tankTruck, onSaveSuccess, onSaveError);
            } else {
                TankTruck.save(vm.tankTruck, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:tankTruckUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
