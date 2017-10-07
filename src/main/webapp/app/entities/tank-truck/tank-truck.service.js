(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('TankTruck', TankTruck);

    TankTruck.$inject = ['$resource'];

    function TankTruck ($resource) {
        var resourceUrl =  'api/tank-trucks/:id';

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
