(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('VehicleParkingController', VehicleParkingController);

        VehicleParkingController.$inject = ['Parking', '$resource', '$scope', '$state', 'VehicleByParking', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', '$rootScope'];

    function VehicleParkingController(Parking, $resource, $scope, $state, VehicleByParking, ParseLinks, AlertService, paginationConstants, pagingParams, $rootScope) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.parkings = Parking.query(function(result) {
            vm.parkings = result;
            vm.searchQuery = null;
            loadAll();
        });
        //[1, 2, 3];
        //$rootScope.parkingId = vm.parkingId = 1;
        //vm.parkingId = 1;

       // $scope.parkingId = 1;

        
        function load() {
            if (typeof vm.parkingId != 'undefined' && vm.parkingId != '') {
                var resourceUrl = 'api/vehicles-by-parking/' + vm.parkingId;
                var resource = $resource(resourceUrl, {}, {
                    'query': { method: 'GET', isArray: true}
                });
                resource.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                //loadAll();
            }
        }

        $scope.$watch('vm.parkingId', function () {
            load();
        });

        function loadAll () {
            VehicleByParking.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

        }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }
        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.vehicles = data;
            vm.page = pagingParams.page;
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            console.log($state);
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch,
                parkingId: vm.parkingId
            });
        }
    }
})();
