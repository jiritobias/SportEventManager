'use strict';


var pa165semApp = angular.module('pa165semApp', ['ngRoute', 'semControllers']);
var semControllers = angular.module('semControllers', []);


pa165semApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/sport', {templateUrl: 'partials/sport.html', controller: 'SportCtrl'}).
        otherwise({redirectTo: '/error'});
    }]);

pa165semApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});