(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TankTruckDeleteController',TankTruckDeleteController);

    TankTruckDeleteController.$inject = ['$uibModalInstance', 'entity', 'TankTruck'];

    function TankTruckDeleteController($uibModalInstance, entity, TankTruck) {
        var vm = this;

        vm.tankTruck = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TankTruck.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
