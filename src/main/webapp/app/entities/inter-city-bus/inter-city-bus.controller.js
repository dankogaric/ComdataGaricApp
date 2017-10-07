(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('InterCityBusController', InterCityBusController);

    InterCityBusController.$inject = ['InterCityBus'];

    function InterCityBusController(InterCityBus) {

        var vm = this;

        vm.interCityBuses = [];

        loadAll();

        function loadAll() {
            InterCityBus.query(function(result) {
                vm.interCityBuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
