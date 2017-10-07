(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TructorTruckDeleteController',TructorTruckDeleteController);

    TructorTruckDeleteController.$inject = ['$uibModalInstance', 'entity', 'TructorTruck'];

    function TructorTruckDeleteController($uibModalInstance, entity, TructorTruck) {
        var vm = this;

        vm.tructorTruck = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TructorTruck.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
