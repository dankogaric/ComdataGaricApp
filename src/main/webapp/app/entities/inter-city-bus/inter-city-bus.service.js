(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('InterCityBus', InterCityBus);

    InterCityBus.$inject = ['$resource'];

    function InterCityBus ($resource) {
        var resourceUrl =  'api/inter-city-buses/:id';

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
