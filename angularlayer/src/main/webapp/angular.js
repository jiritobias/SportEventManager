'use strict';

/* Defines application and its dependencies */
//  sem - Sport Event Manager

var pa165semApp = angular.module('pa165semApp', ['ngRoute', 'semControllers']);
var semControllers = angular.module('semControllers', []);

/* Configures URL fragment routing, e.g. #/product/1  */
pa165semApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/sport', {templateUrl: 'partials/sport.html', controller: 'SportCtrl'}).
        otherwise({redirectTo: '/error'});
    }]);