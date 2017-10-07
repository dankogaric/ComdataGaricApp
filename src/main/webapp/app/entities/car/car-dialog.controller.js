(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Car', 'AdditionalEquipment'];

    function CarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Car, AdditionalEquipment) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.save = save;
        vm.addeqs = AdditionalEquipment.query({filter: 'car-is-null'});
        $q.all([vm.car.$promise, vm.addeqs.$promise]).then(function() {
            if (!vm.car.addEq || !vm.car.addEq.id) {
                return $q.reject();
            }
            return AdditionalEquipment.get({id : vm.car.addEq.id}).$promise;
        }).then(function(addEq) {
            vm.addeqs.push(addEq);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('comdataGaricApp:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
