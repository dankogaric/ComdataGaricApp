(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ManufacturerController', ManufacturerController);

    ManufacturerController.$inject = ['Manufacturer'];

    function ManufacturerController(Manufacturer) {

        var vm = this;

        vm.manufacturers = [];

        loadAll();

        function loadAll() {
            Manufacturer.query(function(result) {
                vm.manufacturers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
