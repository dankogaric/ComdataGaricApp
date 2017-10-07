(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ClassicCarDialogController', ClassicCarDialogController);

    ClassicCarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassicCar'];

    function ClassicCarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassicCar) {
        var vm = this;

        vm.classicCar = entity;
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
            if (vm.classicCar.id !== null) {
                ClassicCar.update(vm.classicCar, onSaveSuccess, onSaveError);
            } else {
                ClassicCar.save(vm.classicCar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:classicCarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
