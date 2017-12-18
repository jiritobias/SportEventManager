'use strict';

var pa165semApp = angular.module('pa165semApp', ['ngRoute', 'semControllers', 'ngCookies']);
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
        }).when('/competition', {
            templateUrl: 'partials/competition.html',
            controller: 'CompetitionCtrl'
        }).when('/admin/competitions', {
            templateUrl: 'partials/admin_competitions.html',
            controller: 'AdminCompetitionsCtrl'
        }).when('/admin/updatecompetition', {
            templateUrl: 'partials/admin_competitionupdate.html',
            controller: 'AdminUpdateCompetitionCtrl'
        }).when('/users', {
            templateUrl: 'partials/user_list.html',
            controller: 'UsersCtrl'
        }).when('/user/:userId', {
            templateUrl: 'partials/user_detail.html',
            controller: 'UserDetailsCtrl'
        }).when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'LoginCtrl'
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
    $rootScope.hideAll = function () {
        $rootScope.hideErrorAlert();
        $rootScope.hideWarningAlert();
        $rootScope.hideSuccessAlert();

    };
});


/*
 *
 */
semControllers.controller('CompetitionCtrl', function ($scope, $http) {
    loadCompetitions($http, $scope)
});

semControllers.controller('AdminCompetitionsCtrl', function ($scope, $http, $location, $rootScope) {
    loadCompetitions($http, $scope);

    $scope.update = function (competition) {
        console.log('updating  competition');
        $rootScope.competition = {
            'sport' : competition.dtoSport.name,
            'id' : competition.id,
        };
        $location.path("/admin/updatecompetition");
    }
});

semControllers.controller('AdminUpdateCompetitionCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('updating competition');

    $scope.update = function (competition) {

        $http({
            method: 'POST',
            url: apiV1('competitions/update'),
            data: competition
        }).then(function success(response) {
            console.log('update competition');
            var createdSport = response.data;
            //display confirmation alert
            $rootScope.successAlert = 'Competition "' + createdSport.name + '" was updated';
            //change view to list of sports
            $location.path("/admin/competitions");
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

semControllers.controller('SportCtrl', function ($scope, $http) {
    loadSports($http, $scope)
});

semControllers.controller('AdminNewSportCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('creating new sport');

    $scope.newsport = {
        'name': ''
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
            'name': sport.name,
            'id': sport.id
        };
        $location.path("/admin/updatesport");
    }
});

function isLoggedUser($cookies) {
    var username = $cookies.get('username');
    var psswd = $cookies.get('psswd');

    return username !== undefined && psswd !== undefined;
}

function setLoggedUser(response, $cookies) {
    $cookies.put('username', response.data.username);
    $cookies.put('role', response.data.role);
    $cookies.put('psswd', response.data.psswd);
}

function logoutUser($cookies) {
    $cookies.put('username', undefined);
    $cookies.put('role', undefined);
    $cookies.put('psswd', undefined);
}

function isAdmin($cookies) {
    var role = $cookies.get('role');
    return role !== undefined && role === "ADMINISTRATOR";
}

semControllers.controller('LoginCtrl', function ($scope, $http, $location, $rootScope, $cookies) {

    $scope.lg = {
        'email': '',
        'password': ''
    };
    $scope.isLogged = isLoggedUser($cookies);
    $scope.logout = function () {
        logoutUser($cookies);
        $rootScope.successAlert = 'Logout success';
        $location.path("/default")
    };

    $scope.login = function (user) {
        $http({
            method: 'POST',
            url: apiV1('auth/login'),
            data: user
        }).then(function success(response) {
            if (response !== undefined) {
                setLoggedUser(response, $cookies);
            }
            $rootScope.hideAll();
            $rootScope.successAlert = 'Login success';
            if (isAdmin($cookies)) {
                $location.path('/sport')
            } else {
                $location.path('/default')
            }

        }, function error(response) {
            $rootScope.errorAlert = 'Loggin failed!';
        });
    };

});


semControllers.controller('AdminUserCtrl', function ($scope, $http, $location, $rootScope) {
    loadUsers($http, $scope, $rootScope);

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
            url: apiV1('users/' + user.id + '/delete'),
            data: {
                'id': Number(user.id)
            }
        }).then(function success(response) {
            console.log('user deleted');
            $rootScope.successAlert = 'User with id ' + response.data.id + ' was deleted';
            // $location.path("/admin/users");
            loadUsers($http, $scope, $rootScope);
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

    $scope.user = {
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

semControllers.controller('UsersCtrl', function ($scope, $http, $rootScope) {
    loadUsers($http, $scope, $rootScope);
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

function loadUsers($http, $scope, $rootScope) {
    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchUser = '';

    var uri = apiV1('users');
    console.log('calling ' + uri);
    $scope.users = [];
    $http.get(uri).then(function (response) {
        try {
            var users = response.data['_embedded']['users'];
            console.log('AJAX loaded ' + users.length + ' users');
            console.log(users);
            $scope.users = users;
        } catch (e) {
            $rootScope.warningAlert = 'No users found!';
        }
    });
}

function loadSports($http, $scope) {
    console.log('calling ' + apiV1('sports'));

    $http.get(apiV1('sports')).then(function (response) {
        var sports = response.data['_embedded']['sports'];
        console.log('AJAX loaded ' + ' sports');
        $scope.sports = sports;
    });
}

function loadCompetitions($http, $scope){
    console.log('calling ' + apiV1('competitions'));

    $http.get(apiV1('competitions')).then(function (response) {
        var competitions = response.data['_embedded']['competitions'];
        console.log(competitions[1]);
        $scope.competitions = competitions;
    });
}

function apiV1(path) {
    return apiV1Path + path;
}