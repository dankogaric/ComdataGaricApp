(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('CityBus', CityBus);

    CityBus.$inject = ['$resource'];

    function CityBus ($resource) {
        var resourceUrl =  'api/city-buses/:id';

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
