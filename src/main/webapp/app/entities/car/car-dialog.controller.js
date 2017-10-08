(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Car', 'Manufacturer'];

    function CarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Car, Manufacturer) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.save = save;
        vm.manufacturers = Manufacturer.query({filter: 'car-is-null'});

        vm.carTypes = ["ClassicCar", "Cabrio"];
        //vm.selectedCarType = vm.carTypes[0];
        
        $q.all([vm.car.$promise, vm.manufacturers.$promise]).then(function() {
            console.log("THE CAR IS,", vm.car);
       

            if (!vm.car.manufacturer || !vm.car.manufacturer.id) {
                return $q.reject();
            }
          
            

            return Manufacturer.get({id : vm.car.manufacturer.id}).$promise;
        }).then(function(addEq) {
          //  vm.manufacturers.push(addEq);
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
