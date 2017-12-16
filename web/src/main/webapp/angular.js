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
        }).when('/admin/users', {
            templateUrl: 'partials/admin_users.html',
            controller: 'AdminUserCtrl'
        }).when('/admin/newuser', {
            templateUrl: 'partials/admin_newuser.html',
            controller: 'AdminNewUserCtrl'
        }).when('/default', {
            templateUrl: 'partials/default.html',
            controller: ''
        }).when('/users', {
            templateUrl: 'partials/user_list.html',
            controller: 'UsersCtrl'
        }).when('/user/:userId', {
            templateUrl: 'partials/user_detail.html',
            controller: 'UserDetailsCtrl'
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

    $scope.newsport={
        'name':''
    };

    $scope.create = function (newsport) {
        $http({
            method: 'POST',
            url: apiV1('sports/create'),
            data: newsport
        }).then(function success(response) {
            console.log('created sport');
            var createdSport = response.data;
            //display confirmation alert
            $rootScope.successAlert = 'A new sport "' + createdSport.name + '" was created';
            //change view to list of sports
            $location.path("/admin/sports");
        }, function error(response) {
            //display error
            console.log("error when creating sport");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot create sport ! Reason given by the server: ' + response.data.message;
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
            //change view to list of sports
            $location.path("/admin/sports");
        }, function error(response) {
            //display error
            console.log("error when updating sport");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot create sport ! Reason given by the server: ' + response.data.message;
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

semControllers.controller('AdminUserCtrl', function ($scope, $http, $location, $rootScope) {
    loadUsers($http, $scope);

    $scope.update = function (user) {
        console.log('updating  user');
        $rootScope.user = user;
        $location.path("/admin/updateuser");
    };

    $scope.delete = function (user) {
      console.log('deleting user');
      console.log(user);

      $http({
          method: 'POST',
          url: apiV1('users/'+user.id+'/delete'),
          data: {
              'id': Number(user.id)
          }
      }).then(function success(response) {
          console.log('user deleted');
          $rootScope.successAlert = 'User with id ' + response.data.id + ' was deleted';
          // $location.path("/admin/users");
          loadUsers($http, $scope);
      }, function error(response) {
          //display error
          console.log("error when deletin user");
          console.log(response);
          switch (response.data.code) {
              case 'InvalidRequestException':
                  $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                  break;
              default:
                  $rootScope.errorAlert = 'Cannot delete user ! Reason given by the server: ' + response.data.message;
                  break;
          }
      });
    }
});

semControllers.controller('AdminNewUserCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('creating new user');
    $scope.roles = ['SPORTSMEN', 'USER', 'ADMINISTRATOR'];
    $scope.genders = ['MAN', 'WOMAN'];

    $scope.user={
        'firstname': '',
        'lastname': '',
        'email': '',
        'gendre': $scope.genders[0],
        'birthdate': '',
        'phone': '',
        'address': '',
        'role': $scope.roles[0],
        'password': ''
    };

    $scope.create = function (user) {
        console.log(user);

        $http({
            method: 'POST',
            url: apiV1('users/create'),
            data: user
        }).then(function success(response) {
            console.log('created user');
            var createdUser = response.data;
            //display confirmation alert
            $rootScope.successAlert = 'A new user "' + createdUser.name + '" was created';
            //change view to list of sports
            $location.path("/admin/users");
        }, function error(response) {
            //display error
            console.log("error when creating user");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot create user ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };

});

semControllers.controller('UsersCtrl', function ($scope, $http) {
    loadUsers($http, $scope);
});

semControllers.controller('UserDetailsCtrl', function ($scope, $routeParams, $http) {
    var id = $routeParams.userId;
    var uri = apiV1('users/' + id);
    console.log('calling ' + uri);

   $http.get(uri).then(function (response) {
       $scope.user = response.data;
       console.log($scope.user);
   });
});

function loadUsers($http, $scope) {
    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchUser = '';

    var uri = apiV1('users');
    console.log('calling ' + uri);
    $http.get(uri).then(function (response) {
        var users = response.data['_embedded']['users'];
        console.log('AJAX loaded ' + users.length + ' users');
        console.log(users);
        $scope.users = users;
    });
}

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