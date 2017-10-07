(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ParkingController', ParkingController);

    ParkingController.$inject = ['Parking'];

    function ParkingController(Parking) {

        var vm = this;

        vm.parkings = [];

        loadAll();

        function loadAll() {
            Parking.query(function(result) {
                vm.parkings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
