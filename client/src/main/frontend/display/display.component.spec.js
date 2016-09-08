'use strict';

describe('stationDetail', function() {

  // Load the module that contains the `phoneDetail` component before each test
  beforeEach(module('stationDetail'));

  // Test the controller
  describe('StationDetailController', function() {
    var $httpBackend, ctrl;
    var xyzPhoneData = {
      name: 'station xyz',
      images: ['image/url1.png', 'image/url2.png']
    };

    beforeEach(inject(function($componentController, _$httpBackend_, $routeParams) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('http://localhost:8080/server/rest/display/:stationId').respond(xyzStationData);

      $routeParams.stationId = 'xyz';

      ctrl = $componentController('stationDetail');
    }));

    it('should fetch the station details', function() {
      jasmine.addCustomEqualityTester(angular.equals);

      expect(ctrl.station).toEqual({});

      $httpBackend.flush();
      expect(ctrl.station).toEqual(xyzStationData);
    });

  });

});
