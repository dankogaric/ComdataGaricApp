(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('AdditionalEquipmentController', AdditionalEquipmentController);

    AdditionalEquipmentController.$inject = ['AdditionalEquipment'];

    function AdditionalEquipmentController(AdditionalEquipment) {

        var vm = this;

        vm.additionalEquipments = [];

        loadAll();

        function loadAll() {
            AdditionalEquipment.query(function(result) {
                vm.additionalEquipments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
