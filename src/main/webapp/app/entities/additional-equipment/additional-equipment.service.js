(function() {
    'use strict';
    angular
        .module('comdataGaricApp')
        .factory('AdditionalEquipment', AdditionalEquipment);

    AdditionalEquipment.$inject = ['$resource'];

    function AdditionalEquipment ($resource) {
        var resourceUrl =  'api/additional-equipments/:id';

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
