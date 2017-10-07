(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CityBusDialogController', CityBusDialogController);

    CityBusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CityBus'];

    function CityBusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CityBus) {
        var vm = this;

        vm.cityBus = entity;
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
            if (vm.cityBus.id !== null) {
                CityBus.update(vm.cityBus, onSaveSuccess, onSaveError);
            } else {
                CityBus.save(vm.cityBus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:cityBusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
