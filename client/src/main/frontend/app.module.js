'use strict';

// Define the module
var app = angular.module('rocnet-client-info-system', [
  'ngRoute',
  'ngResize',
  'core',
  'display',
  'stationList',
]);
app.controller('resize', ['$scope', function($scope) {
    $scope.resized=function($event){
        var w = $event.width;
        var h = $event.height;
        var e = document.querySelector('body');
        var cw = 1024;
        var ch = 768;
        var dx = w/cw;
        var dy = h/ch;

        console.log("w = "+w+ ", h = "+h+", "+cw+", "+ch);
        console.log(dx+", "+dy);

        if (dx > dy) {
            dx = dy;
        }

        console.log(dx);

        e.style.transformOrigin = '50% 50%';
        e.style.transform = 'scale('+dx+')';
    }
}]);