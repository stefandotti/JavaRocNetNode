'use strict';

describe('stationList', function() {

  // Load the module that contains the `phoneList` component before each test
  beforeEach(module('stationList'));

  // Test the controller
  describe('StationListController', function() {
    var $httpBackend, ctrl;

    beforeEach(inject(function($componentController, _$httpBackend_) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('http://localhost:8080/server/rest/stations')
                  .respond([{name: 'Nexus S'}, {name: 'Motorola DROID'}]);

      ctrl = $componentController('stationList');
    }));

    it('should create a `stations` property with 2 phones fetched with `$http`', function() {
      jasmine.addCustomEqualityTester(angular.equals);

      expect(ctrl.stations).toEqual([]);

      $httpBackend.flush();
      expect(ctrl.stations).toEqual([{name: 'Nexus S'}, {name: 'Motorola DROID'}]);
    });

    it('should set a default value for the `orderProp` property', function() {
      expect(ctrl.orderProp).toBe('age');
    });

  });

});
