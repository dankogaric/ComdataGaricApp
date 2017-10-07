(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('Bus', Bus);

    Bus.$inject = ['$resource'];

    function Bus ($resource) {
        var resourceUrl =  'api/buses/:id';

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
