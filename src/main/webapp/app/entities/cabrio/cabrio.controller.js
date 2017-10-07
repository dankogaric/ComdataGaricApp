(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('CabrioController', CabrioController);

    CabrioController.$inject = ['Cabrio'];

    function CabrioController(Cabrio) {

        var vm = this;

        vm.cabrios = [];

        loadAll();

        function loadAll() {
            Cabrio.query(function(result) {
                vm.cabrios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
