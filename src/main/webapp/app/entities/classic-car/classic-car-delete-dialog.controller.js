(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ClassicCarDeleteController',ClassicCarDeleteController);

    ClassicCarDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassicCar'];

    function ClassicCarDeleteController($uibModalInstance, entity, ClassicCar) {
        var vm = this;

        vm.classicCar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassicCar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
