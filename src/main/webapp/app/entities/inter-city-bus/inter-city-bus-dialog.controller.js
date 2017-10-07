(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('InterCityBusDialogController', InterCityBusDialogController);

    InterCityBusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InterCityBus'];

    function InterCityBusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InterCityBus) {
        var vm = this;

        vm.interCityBus = entity;
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
            if (vm.interCityBus.id !== null) {
                InterCityBus.update(vm.interCityBus, onSaveSuccess, onSaveError);
            } else {
                InterCityBus.save(vm.interCityBus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:interCityBusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
