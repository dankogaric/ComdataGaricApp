(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ParkingDialogController', ParkingDialogController);

    ParkingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Parking'];

    function ParkingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Parking) {
        var vm = this;

        vm.parking = entity;
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
            if (vm.parking.id !== null) {
                Parking.update(vm.parking, onSaveSuccess, onSaveError);
            } else {
                Parking.save(vm.parking, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:parkingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
