(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('Parking', Parking);

    Parking.$inject = ['$resource'];

    function Parking ($resource) {
        var resourceUrl =  'api/parkings/:id';

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
