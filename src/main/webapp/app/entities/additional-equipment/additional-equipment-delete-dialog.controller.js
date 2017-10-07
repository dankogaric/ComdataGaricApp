(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('AdditionalEquipmentDeleteController',AdditionalEquipmentDeleteController);

    AdditionalEquipmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'AdditionalEquipment'];

    function AdditionalEquipmentDeleteController($uibModalInstance, entity, AdditionalEquipment) {
        var vm = this;

        vm.additionalEquipment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AdditionalEquipment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
