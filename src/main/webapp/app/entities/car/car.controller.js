(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CarController', CarController);

    CarController.$inject = ['Car'];

    function CarController(Car) {

        var vm = this;

        vm.cars = [];

        loadAll();

        function loadAll() {
            Car.query(function(result) {
                vm.cars = result;
                vm.searchQuery = null;
            });
        }
    }
})();
