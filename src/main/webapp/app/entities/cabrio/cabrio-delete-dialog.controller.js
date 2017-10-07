(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CabrioDeleteController',CabrioDeleteController);

    CabrioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cabrio'];

    function CabrioDeleteController($uibModalInstance, entity, Cabrio) {
        var vm = this;

        vm.cabrio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cabrio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
