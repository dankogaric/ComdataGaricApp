(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('ClassicCar', ClassicCar);

    ClassicCar.$inject = ['$resource'];

    function ClassicCar ($resource) {
        var resourceUrl =  'api/classic-cars/:id';

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
