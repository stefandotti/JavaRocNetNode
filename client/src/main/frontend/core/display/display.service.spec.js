'use strict';

describe('Display', function() {
  var $httpBackend;
  var Display;
  var displayData = {};

  // Add a custom equality tester before each test
  beforeEach(function() {
    jasmine.addCustomEqualityTester(angular.equals);
  });

  // Load the module that contains the `Phone` service before each test
  beforeEach(module('core.display'));

  // Instantiate the service and "train" `$httpBackend` before each test
  beforeEach(inject(function(_$httpBackend_, _Display_) {
    $httpBackend = _$httpBackend_;
    $httpBackend.expectGET('http://localhost:8080/server/rest/display/:stationId').respond(stationsData);

    Display = _Display_;
  }));

  // Verify that there are no outstanding expectations or requests after each test
  afterEach(function () {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should fetch the stations data from `/stations/stations.json`', function() {
    var display = Display.query();

    expect(display).toEqual({});

    $httpBackend.flush();
    expect(display).toEqual(displayData);
  });

});
