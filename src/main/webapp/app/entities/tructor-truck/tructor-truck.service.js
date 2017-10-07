(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('TructorTruck', TructorTruck);

    TructorTruck.$inject = ['$resource'];

    function TructorTruck ($resource) {
        var resourceUrl =  'api/tructor-trucks/:id';

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
