(function() {
    'use strict';

    angular
        .module('comdataGaricApp')
        .controller('ClassicCarDetailController', ClassicCarDetailController);

    ClassicCarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassicCar'];

    function ClassicCarDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassicCar) {
        var vm = this;

        vm.classicCar = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('comdataGaricApp:classicCarUpdate', function(event, result) {
            vm.classicCar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
