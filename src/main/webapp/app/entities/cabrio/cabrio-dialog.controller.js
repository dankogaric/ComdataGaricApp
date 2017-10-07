(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CabrioDialogController', CabrioDialogController);

    CabrioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cabrio'];

    function CabrioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cabrio) {
        var vm = this;

        vm.cabrio = entity;
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
            if (vm.cabrio.id !== null) {
                Cabrio.update(vm.cabrio, onSaveSuccess, onSaveError);
            } else {
                Cabrio.save(vm.cabrio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:cabrioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
