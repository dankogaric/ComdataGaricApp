(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ParkingDeleteController',ParkingDeleteController);

    ParkingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Parking'];

    function ParkingDeleteController($uibModalInstance, entity, Parking) {
        var vm = this;

        vm.parking = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Parking.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
