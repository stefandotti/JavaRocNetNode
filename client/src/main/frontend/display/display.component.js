'use strict';

// Register `phoneDetail` component, along with its associated controller and template
angular.
  module('display').
  component('display', {
    templateUrl: 'display/display.template.html',
    controller: ['$routeParams', 'Display',
      function DisplayController($routeParams, Display) {
        var self = this;
        self.display = Display.get({stationId: $routeParams.stationId});
      }
    ]
  });
