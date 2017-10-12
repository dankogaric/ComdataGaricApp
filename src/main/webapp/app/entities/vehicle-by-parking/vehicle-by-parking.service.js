(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('VehicleByParking', VehicleByParking);

    VehicleByParking.$inject = ['$resource'];

    function VehicleByParking ($resource) {
        var resourceUrl =  'api/vehicles';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
