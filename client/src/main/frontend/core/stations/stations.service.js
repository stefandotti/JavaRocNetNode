'use strict';

angular.
  module('core.stations').
  factory('Stations', ['$resource',
    function($resource) {
      return $resource('http://localhost:8080/server/app/rest/stations', {}, {
        query: {
          method: 'GET',
          isArray: true,
        }
      });
    }
  ]);
