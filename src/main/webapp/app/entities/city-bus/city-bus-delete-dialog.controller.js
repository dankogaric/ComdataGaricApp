(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CityBusDeleteController',CityBusDeleteController);

    CityBusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CityBus'];

    function CityBusDeleteController($uibModalInstance, entity, CityBus) {
        var vm = this;

        vm.cityBus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CityBus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
