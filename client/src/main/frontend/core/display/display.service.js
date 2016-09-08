'use strict';

angular.
  module('core.display').
  factory('Display', ['$resource',
    function($resource) {
      return $resource('http://localhost:8080/server/rest/display/:stationId', {}, {
        query: {
          method: 'GET',
          isObject: true,
        }
      });
    }
  ]);
