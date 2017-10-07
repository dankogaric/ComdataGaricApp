(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('TructorTruckDetailController', TructorTruckDetailController);

    TructorTruckDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TructorTruck'];

    function TructorTruckDetailController($scope, $rootScope, $stateParams, previousState, entity, TructorTruck) {
        var vm = this;

        vm.tructorTruck = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:tructorTruckUpdate', function(event, result) {
            vm.tructorTruck = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
