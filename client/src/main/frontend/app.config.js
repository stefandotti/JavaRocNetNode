'use strict';

angular.
  module('rocnet-client-info-system').
  config(['$locationProvider' ,'$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/stations', {
          template: '<station-list></station-list>'
        }).
        when('/display/:stationId', {
          template: '<display></display>'
        }).
        otherwise('/stations');
    }
  ]);
