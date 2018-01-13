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
        }).when('/admin/updateuser', {
            templateUrl: 'partials/admin_newuser.html',
            controller: 'AdminUpdateUserCtrl'
        }).when('/default', {
            templateUrl: 'partials/default.html',
            controller: 'HomeCtrl'
        }).when('/competition', {
            templateUrl: 'partials/competition.html',
            controller: 'CompetitionCtrl'
        }).when('/admin/competitions', {
            templateUrl: 'partials/admin_competitions.html',
            controller: 'AdminCompetitionsCtrl'
        }).when('/admin/newcompetition',{
            templateUrl: 'partials/admin_newcompetition.html',
            controller: 'CreateCompetitionCtrl'
        }).when('/admin/registertocompetition', {
            templateUrl: 'partials/admin_registertocompetition.html',
            controller: 'RegisterToCompetitionCtrl'
        }).when('/users', {
            templateUrl: 'partials/user_list.html',
            controller: 'UsersCtrl'
        }).when('/user/:userId', {
            templateUrl: 'partials/user_detail.html',
            controller: 'UserDetailsCtrl'
        }).when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'LoginCtrl'
        }).when('/sport/:sportId', {
            templateUrl: 'partials/sport_detail.html',
            controller: 'SportDetailCtrl'
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
    $rootScope.hideAllAlerts = function () {
        $rootScope.hideErrorAlert();
        $rootScope.hideWarningAlert();
        $rootScope.hideSuccessAlert();

    };
});

semControllers.controller('HomeCtrl', function ($scope, $http) {
    $scope.hideAllAlerts();
});

/*
 *
 */
semControllers.controller('CompetitionCtrl', function ($scope, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    loadCompetitions($http, $scope)

    $scope.unregisterFromCompUser = function(competition){

        var loggedUser = getLoggedUser($cookies);
        // $http({
        //     method: 'POST',
        //     url: apiV1('competitions/unregister'),
        //     data: msg
        // })
        $http({
            method: 'GET',
            url: apiV1('users/loadByEmail/'+loggedUser.username),
        }).then(function (response) {
            try {
                console.log(response.data);
                loggedUser.email = response.data['_embedded']['userId'];
                console.log('AJAX loaded userId ' + userId);
                console.log(userId);
            } catch (e) {
                $rootScope.warningAlert = 'No users found!';
            }
        });

        console.log(loggedUser.id);
    };
    $location.path("/competition");
});

semControllers.controller('AdminCompetitionsCtrl', function ($scope, $http, $location, $rootScope, competitionServ, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

    loadCompetitions($http, $scope);

    $scope.update = function (competition) {
        console.log('updating  competition');
        $rootScope.competition = {
            'sport' : competition.dtoSport.name,
            'id' : competition.id,
        };
        $location.path("/admin/updatecompetition");
    };

    $scope.loadCompetition = function (competition) {
        console.log("setting competition");
        console.log(competitionServ);
        competitionServ.competition = competition;
    };

    $scope.delete = function (competition) {
        console.log('deleting competition');
        console.log(competition);

        $http({
            method: 'POST',
            url: apiV1('competitions/' + competition.id + '/delete'),
            data: {
                'id': Number(competition.id)
            }
        }).then(function success(response) {
            console.log('competition deleted');
            // $location.path("/admin/competitions");
            $rootScope.successAlert = 'Competition with id ' + response.data.id + ' was deleted';
            loadCompetitions($http, $scope, $rootScope);
        }, function error(response) {
            //display error
            console.log("error when deleting competition");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot delete competition ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    }

    $scope.unregisterFromComp = function (sportsman, competition) {
        var msg = '{"sportsMen":' + sportsman.id + ',"competition":' + competition.id + '}';
        console.log(msg);

        $http({
            method: 'POST',
            url: apiV1('competitions/unregister'),
            data: msg
        }).then(function success(response) {
            console.log('users unregistred from competition');
            // display confirmation alert
            $rootScope.successAlert = 'User was unregistered successfully.';
            // change view to list of sports
            $location.path("/admin/competitions");
        }, function error(response) {
            // display error
            console.log("error when updating competition");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot unregister from competition ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    }
});

semControllers.service('competitionServ', function () {
    this.currentCompetiton = {};

    return this.currentCompetiton;
});

semControllers.controller('RegisterToCompetitionCtrl', function ($scope, $http, $rootScope, $location, $cookies, competitionServ) {

    console.log('register to competition');

    $scope.hideAllAlerts();
    $scope.result = {
        checked:''
    };

    loadUsers($http, $scope, $rootScope, $cookies);

    $scope.registertocomp = function (result) {

        var competition = competitionServ.competition;


        competition.dtoSportsmen.forEach(function(sportsman){
            if(sportsman.id === result.id){
                alert('User already at competition');
            }});

        var msg = '{"sportsMan":' + result.id + ',"competition":' + competition.id + '}';
        console.log(msg);

        $http({
            method: 'POST',
            url: apiV1('competitions/register'),
            data: msg
        }).then(function success(response) {
            console.log('users added to competition');
            // display confirmation alert
            $rootScope.successAlert = 'Selected users was added to competition.';
            // change view to list of sports
            $location.path("/admin/competitions");
        }, function error(response) {
            // display error
            console.log("error when updating competition");
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

semControllers.controller('CreateCompetitionCtrl', function ($scope, $routeParams, $http, $location, $rootScope) {
    console.log('creating new competition');

    $scope.hideAllAlerts();
    loadSports($http, $scope);

    $scope.competition = {
        'sport': ''
    };

    $scope.create = function (competition) {
        // delete competition.sport['_links'];

        var result = {};
        var sport = JSON.parse(competition.sport);
        result.sport = {};
        result.sport.id = sport.id;
        result.sport.name = sport.name;

        $http({
            method: 'POST',
            url: apiV1('competitions/create'),
            data: result
        }).then(function success(response) {
            console.log('created competition');
            var createdCompetition = response.data;

            // display confirmation alert
            $rootScope.successAlert = 'A new competition in "' + createdCompetition.dtoSport.name + '" was created';
// change view to list of sports
            $location.path("/admin/competitions");
        }, function error(response) {
            // display error
            console.log("error when creating competition");
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
    $scope.hideAllAlerts();
    loadSports($http, $scope)
});

semControllers.controller('AdminNewSportCtrl', function ($scope, $routeParams, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

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

semControllers.controller('AdminUpdateSportCtrl', function ($scope, $routeParams, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

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

semControllers.controller('AdminSportCtrl', function ($scope, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

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

    var isLogged = username !== undefined && psswd !== undefined;
    if (isLogged) {
        loggedUser = {
            'username': $cookies.get('username'),
            'role': $cookies.get('role'),
            'psswd': $cookies.get('psswd')
        }
    }

    return isLogged;
}

function setLoggedUser(response, $cookies) {
    $cookies.put('username', response.data.username);
    $cookies.put('role', response.data.role);
    $cookies.put('psswd', response.data.psswd);
    loggedUser = response.data;
}

function logoutUser($cookies) {
    $cookies.remove('username');
    $cookies.remove('role');
    $cookies.remove('psswd');
    loggedUser = undefined;
}

// function isAdmin($cookies) {
//     var role = $cookies.get('role');
//     return role !== undefined && role === "ADMINISTRATOR";
// }

semControllers.controller('LoginCtrl', function ($scope, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    $scope.lg = {
        'email': '',
        'password': ''
    };
    $rootScope.isLogged = isLoggedUser($cookies);
    if (isLoggedUser($cookies)) {
        loggedUser = {
            'username': $cookies.get('username'),
            'role': $cookies.get('role'),
            'psswd': $cookies.get('psswd')
        }
    }

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
                loggedUser = response.data;
            }
            $rootScope.hideAllAlerts();
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


semControllers.controller('AdminUserCtrl', function ($scope, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

    loadUsers($http, $scope, $rootScope, $cookies);

    $scope.update = function (user) {
        console.log('updating  user');
        $rootScope.user = user;
        $rootScope.user.birthdate = new Date($rootScope.user.birthdate);
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
            loadUsers($http, $scope, $rootScope, $cookies);
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

function checkAdminRights($location, $rootScope, $cookies) {
    if (!isLogged($cookies) || !isAdmin($cookies)) {
        $location.path("/login");
        $rootScope.errorAlert = 'You are not Admin!';
        return false;
    }
    return true;
}

semControllers.controller('AdminNewUserCtrl', function ($scope, $routeParams, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

    console.log('creating new user');
    $scope.roles = ['SPORTSMEN', 'USER', 'ADMINISTRATOR'];
    $scope.genders = ['MAN', 'WOMAN'];
    $scope.updating = false;

    $scope.user = {
        'firstname': '',
        'lastname': '',
        'email': '',
        'gender': $scope.genders[0],
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

semControllers.controller('AdminUpdateUserCtrl', function ($scope, $routeParams, $http, $location, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    if(!checkAdminRights($location, $rootScope, $cookies)){
        return
    }

    $scope.roles = ['SPORTSMEN', 'USER', 'ADMINISTRATOR'];
    $scope.genders = ['MAN', 'WOMAN'];
    $scope.updating = true;

    $scope.update = function (user) {
        $http({
            method: 'PUT',
            url: apiV1('users/' + user.id),
            data: {
                "id": user.id,
                "email": user.email,
                "firstname": user.firstname,
                "lastname": user.lastname,
                "gendre": user.gender,
                "birthdate": user.birthdate,
                "phone": user.phone,
                "address": user.address,
                "role": user.role,
                "passwordHash": ""
            }
        }).then(function success(response) {
            var updatedUser = response.data;
            $rootScope.successAlert = 'User ' + updatedUser.id + ' was updated';
            $location.path('/admin/users');
        }, function error(response) {
            //display error
            console.log("error when updating user");
            console.log(response);
            switch (response.data.code) {
                case 'InvalidRequestException':
                    $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                    break;
                default:
                    $rootScope.errorAlert = 'Cannot update user ! Reason given by the server: ' + response.data.message;
                    break;
            }
        });
    };

    console.log('updating user');
});

semControllers.controller('UsersCtrl', function ($scope, $http, $rootScope, $cookies) {
    $scope.hideAllAlerts();
    loadUsers($http, $scope, $rootScope, $cookies);
});

semControllers.controller('UserDetailsCtrl', function ($scope, $routeParams, $http) {
    $scope.hideAllAlerts();
    var id = $routeParams.userId;
    var uri = apiV1('users/' + id);
    console.log('calling ' + uri);

    $http.get(uri).then(function (response) {
        $scope.user = response.data;
        $scope.user.comps = [];
        $scope.user.competitions.forEach((t, number) => {
            $scope.user.comps.push("Competition " + t.id + " (" + t.sport.name + ", " + t.date + ")");
        });
        $scope.user.comps = $scope.user.comps.join(", ");
        console.log($scope.user);
    });
});

semControllers.controller('SportDetailCtrl', function ($scope, $routeParams, $http) {
    $scope.hideAllAlerts();
    var id = $routeParams.sportId;
    var uri = apiV1('sports/' + id);
    console.log('calling ' + uri);

    $http.post(uri).then(function (response) {
        $scope.sport = response.data;
        console.log($scope.sport);
    });
});


function getUserIdByEmail($http, $scope, $rootScope, email){
    var userId = false;
    $http.get(apiV1('users/loadByEmail/'+email)).then(function (response) {
        try {
            console.log(response.data['_embedded']);
            userId = response.data['_embedded']['userId'];
            console.log('AJAX loaded userId ' + userId);
            console.log(userId);
        } catch (e) {
            $rootScope.warningAlert = 'No users found!';
        }
    });
    return userId
}

function loadUsers($http, $scope, $rootScope, $cookies) {
    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchUser = '';

    if (!isAdmin($cookies)) {
        var uri = apiV1('users?role=SPORTSMEN')
    } else {
        var uri = apiV1('users');
    }
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

    $scope.sports = [];
    $http.get(apiV1('sports')).then(function (response) {
        var sports = response.data['_embedded']['sports'];
        console.log('AJAX loaded ' + ' sports');
        $scope.sports = sports;
        console.log(sports);
    });
}

function loadCompetitions($http, $scope){
    console.log('calling ' + apiV1('competitions'));

    $scope.competitions = [];
    $http.get(apiV1('competitions')).then(function (response) {
        var competitions = response.data['_embedded']['competitions'];
        console.log(competitions);
        $scope.competitions = competitions;
    });
}

function apiV1(path) {
    return apiV1Path + path;
}

var loggedUser = undefined;

function getLoggedUser($cookies) {
    var result = {};
    result.role = $cookies.get('role');
    result.username = $cookies.get('username');
    result.psswd = $cookies.get('psswd');
    return result;
}

function isLogged($cookies) {
    return getLoggedUser($cookies) !== undefined;
}

function isAdmin($cookies) {
    // console.log(getLoggedUser($cookies));
    return isLogged($cookies) && getLoggedUser($cookies).role === 'ADMINISTRATOR';
}