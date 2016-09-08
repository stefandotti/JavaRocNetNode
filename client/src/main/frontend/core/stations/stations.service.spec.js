'use strict';

describe('Stations', function() {
  var $httpBackend;
  var Station;
  var stationsData = {};

  // Add a custom equality tester before each test
  beforeEach(function() {
    jasmine.addCustomEqualityTester(angular.equals);
  });

  // Load the module that contains the `Phone` service before each test
  beforeEach(module('core.stations'));

  // Instantiate the service and "train" `$httpBackend` before each test
  beforeEach(inject(function(_$httpBackend_, _Station_) {
    $httpBackend = _$httpBackend_;
    $httpBackend.expectGET('http://localhost:8080/server/rest/stations').respond(stationsData);

    Station = _Station_;
  }));

  // Verify that there are no outstanding expectations or requests after each test
  afterEach(function () {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should fetch the stations data from `/stations/stations.json`', function() {
    var stations = Station.query();

    expect(stations).toEqual({});

    $httpBackend.flush();
    expect(stations).toEqual(stationsData);
  });

});
