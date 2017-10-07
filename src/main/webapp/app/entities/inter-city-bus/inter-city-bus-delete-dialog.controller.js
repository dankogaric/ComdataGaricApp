(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('InterCityBusDeleteController',InterCityBusDeleteController);

    InterCityBusDeleteController.$inject = ['$uibModalInstance', 'entity', 'InterCityBus'];

    function InterCityBusDeleteController($uibModalInstance, entity, InterCityBus) {
        var vm = this;

        vm.interCityBus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InterCityBus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
