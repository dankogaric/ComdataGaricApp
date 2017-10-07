(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ClassicCarController', ClassicCarController);

    ClassicCarController.$inject = ['ClassicCar'];

    function ClassicCarController(ClassicCar) {

        var vm = this;

        vm.classicCars = [];

        loadAll();

        function loadAll() {
            ClassicCar.query(function(result) {
                vm.classicCars = result;
                vm.searchQuery = null;
            });
        }
    }
})();
