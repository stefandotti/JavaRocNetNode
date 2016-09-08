'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.
  module('stationList').
  component('stationList', {
    templateUrl: 'station-list/station-list.template.html',
    controller: ['Stations',
      function StationListController(Station) {
        this.stations = Station.query();
      }
    ]
  });
