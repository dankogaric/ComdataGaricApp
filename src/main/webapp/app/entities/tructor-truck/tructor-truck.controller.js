(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TructorTruckController', TructorTruckController);

    TructorTruckController.$inject = ['TructorTruck'];

    function TructorTruckController(TructorTruck) {

        var vm = this;

        vm.tructorTrucks = [];

        loadAll();

        function loadAll() {
            TructorTruck.query(function(result) {
                vm.tructorTrucks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
