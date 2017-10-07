(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TructorTruckDialogController', TructorTruckDialogController);

    TructorTruckDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TructorTruck'];

    function TructorTruckDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TructorTruck) {
        var vm = this;

        vm.tructorTruck = entity;
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
            if (vm.tructorTruck.id !== null) {
                TructorTruck.update(vm.tructorTruck, onSaveSuccess, onSaveError);
            } else {
                TructorTruck.save(vm.tructorTruck, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:tructorTruckUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
