'use strict';

// Register `phoneDetail` component, along with its associated controller and template
angular.
  module('display').
  component('display', {
    templateUrl: 'display/display.template.html',
    controller: ['$routeParams', 'Display', '$interval',
      function DisplayController($routeParams, Display, $interval) {
        var self = this;
        self.display = Display.get({stationId: $routeParams.stationId});
        $interval(function() {
            var newDisplay = Display.get({stationId: $routeParams.stationId});
            newDisplay.$promise.then(function(){
                if (!angular.equals(self.display, newDisplay)) {
                    console.log(newDisplay);
                    self.display = newDisplay;
                }
            });
        }, 10000);
        var time = Date.now();
        $interval(function() {
            var offset = Date.now() - time;
            self.clock = time + offset;
        }, 1000);
      }
    ]
  });
