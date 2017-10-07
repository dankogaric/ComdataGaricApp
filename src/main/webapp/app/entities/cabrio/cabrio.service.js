(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('Cabrio', Cabrio);

    Cabrio.$inject = ['$resource'];

    function Cabrio ($resource) {
        var resourceUrl =  'api/cabrios/:id';

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
