'use strict';

describe('Controller Tests', function() {

    describe('Vehicle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVehicle, MockManufacturer, MockParking;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVehicle = jasmine.createSpy('MockVehicle');
            MockManufacturer = jasmine.createSpy('MockManufacturer');
            MockParking = jasmine.createSpy('MockParking');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Vehicle': MockVehicle,
                'Manufacturer': MockManufacturer,
                'Parking': MockParking
            };
            createController = function() {
                $injector.get('$controller')("VehicleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'comdataGaricApp:vehicleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
