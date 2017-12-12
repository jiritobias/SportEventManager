'use strict';


var pa165semApp = angular.module('pa165semApp', ['ngRoute', 'semControllers']);
var semControllers = angular.module('semControllers', []);
var apiV1Path = '/pa165/rest/';

pa165semApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/sport', {
            templateUrl: 'partials/sport.html',
            controller: 'SportCtrl'
        }).when('/admin/sports', {
            templateUrl: 'partials/admin_sports.html',
            controller: 'AdminSportCtrl'
        }).when('/admin/updatesport', {
            templateUrl: 'partials/admin_sportupdate.html',
            controller: 'AdminUpdateSportCtrl'
        }).when('/admin/newsport', {
            templateUrl: 'partials/admin_newsport.html',
            controller: 'AdminNewSportCtrl'
        }).when('/default', {
            templateUrl: 'partials/default.html',
            controller: ''
        }).otherwise({redirectTo: '/default'});
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


/*
 *
 */
semControllers.controller('SportCtrl', function ($scope, $http) {
    loadSports($http, $scope)
});

semControllers.controller('AdminNewSportCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('creating new sport');

    $scope.create = function (sport) {
        $http({
            method: 'POST',
            url: apiV1('sports/create'),
            data: sport
        }).then(function success(response) {
            console.log('created sport');
            var createdSport = response.data;
            //display confirmation alert
            $rootScope.successAlert = 'A new sport "' + createdSport.name + '" was created';
            //change view to list of products
            $location.path("/admin/sports");
        }, function error(response) {
            //display error
            console.log("error when creating product");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };

});

semControllers.controller('AdminUpdateSportCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('updating sport');

    $scope.update = function (sport) {

        $http({
            method: 'POST',
            url: apiV1('sports/update'),
            data: sport
        }).then(function success(response) {
            console.log('update sport');
            var createdSport = response.data;
            //display confirmation alert
            $rootScope.successAlert = 'Sport "' + createdSport.name + '" was updated';
            //change view to list of products
            $location.path("/admin/sports");
        }, function error(response) {
            //display error
            console.log("error when updating product");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };

});

semControllers.controller('AdminSportCtrl', function ($scope, $http, $location, $rootScope) {
    loadSports($http, $scope);

    $scope.update = function (sport) {
        console.log('updating  sport');
        $rootScope.sport = {
            'name' : sport.name,
            'id' : sport.id
        };
        $location.path("/admin/updatesport");
    }
});

function loadSports($http, $scope) {
    console.log('calling' + apiV1('sports'));

    $http.get(apiV1('sports')).then(function (response) {
        var sports = response.data['_embedded']['sports'];
        console.log('AJAX loaded ' + ' sports');
        $scope.sports = sports;
    });
}

function apiV1(path) {
    return apiV1Path + path;
}