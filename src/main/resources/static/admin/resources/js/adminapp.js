var app = angular.module('AdminAPP', [ 'ngRoute' ]);
app
		.config(function($routeProvider, $httpProvider, $locationProvider) {

			$routeProvider.when('/home', {
				controller : 'MainController',
				templateUrl : '/admin/page/admin_main.html'
			}).when('/', {
				redirectTo : '/home'
			}).when('/users', {
				controller : 'UserController',
				templateUrl : '/admin/page/users.html'
			}).when('/create-test', {
				controller : 'MainController',
				templateUrl : '/admin/page/create_test.html'
			}).when('/user-tests', {
				controller : 'MainController',
				templateUrl : '/admin/page/user_tests.html'
			}).when('/test-list', {
				controller : 'MainController',
				templateUrl : '/admin/page/view_tests.html'
			}).otherwise({
				redirectTo : '/home'
			});

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
			$httpProvider.defaults.headers.common['Accept'] = 'application/json';

		});
app.run([
		'$route',
		'$rootScope',
		'$location',
		function($route, $rootScope, $location) {
			var original = $location.path;
			$location.path = function(path, reload) {
				if (reload === false) {
					var lastRoute = $route.current;
					var un = $rootScope.$on('$locationChangeSuccess',
							function() {
								$route.current = lastRoute;
								un();
							});
				}
				return original.apply($location, [ path ]);
			};
		} ]);

app.controller('MainController', function($rootScope, $scope, $http, $location,
		$window) {

	if ($location.path == "/login") {
		$location.path('/');
	}

	$scope.logout = function() {

		$http.get("/data/auth/isloggedin").success(function(data) {
			$scope.loggedIn = data;
		});

		if ($scope.loggedIn) {
			$http.get("/data/auth/logout").success(function(data) {
				$scope.loggedIn = data;
			});
		}

		$location.path('/');

	};

});

app.controller('TestsController', function($rootScope, $scope, $http,
		$location, $window) {

});

app.controller('LoginController', function($rootScope, $scope, $http,
		$location, $window) {

	$scope.showLogin = false;
	$scope.error = false;
	$scope.success = false;

	$http.get("/data/auth/isloggedin").success(function(data) {
		$scope.showLogin = !data;
		if (data) {
			$window.location.href = "/";
		}
	});

	$location.path('/login', false);

	$scope.loginSubmit = function() {

		if ($scope.email && $scope.password) {

			var email = $scope.email;
			var password = $scope.password;
			$scope.success = false;

			$http.get(
					"/data/auth/submitlogin?email=" + email + "&password="
							+ password).success(function(data) {
				$scope.success = data;
				$window.location.href = "/";
				// $location.path( '/' );
			});

		} else {
			$scope.error = {
				"message" : "Nav aizpilditi visi lauki!"
			};
		}

	};

});

app.controller('AdminController', function($rootScope, $scope, $http,
		$location, $window) {

	var urlBase = "";
	$scope.toggle = true;
	$scope.selection = [];
	$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
	$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$http.get(urlBase + '/data/tests/getAllTests').success(function(data) {

		if (data != undefined) {
			$scope.tests = data;
		} else {
			$scope.tests = [];
		}
	});

	$scope.editData = {};
	$scope.Edit = function(test) {
		var testId = test.id;
		var data = $.param({
			testID : testId
		})
		$http.get("/data/tests/questionChoices?testID=" + testId).success(
				function(data) {
					$scope.questionChoices = data;
				}).error(function() {
			alert("CAN'T DELETE THIS CHOICE");
		});

	}

	$scope.DeleteQuestion = function(questionChoice) {
		var questionID = questionChoice.testQuestion.id;
		var data = $.param({
			questionID : questionID
		})
		$http.get("/data/tests/deleteQuestion?questionID=" + questionID)
				.success(function() {
					alert("DELETED");
				}).error(function() {
					alert("CAN'T DELETE THIS QUESTION");
				});
	}

	$scope.Delete = function(questionChoice) {
		var choiceID = questionChoice.id;
		var data = $.param({
			choiceID : choiceID
		})

		$http.get("/data/tests/deleteQuestionChoices?choiceID=" + choiceID)
				.success(function() {
					alert("DELETED");
				});

	}

});



app.controller('UserTestController', function($rootScope, $scope, $http,
		$location, $window) {
	var urlBase = "";
	$scope.toggle = true;
	$scope.selection = [];
	$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
	$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$http.get(urlBase + '/data/tests/getUserTests').success(function(data) {

		if (data != undefined) {
			$scope.userAnswers = data;
		} else {
			$scope.userAnswers = [];
		}
	});


	$scope.editData = {};

	$scope.Edit = function(userAnswer) {
		var userId = userAnswer.user.id;
		var testId = userAnswer.tests.id;
		var data = $.param({
			userID : userId,
			testID : testId
		})

		$http.get(
				"/data/tests/userAnswers?userID=" + userId + "&testID="
						+ testId).success(function(data) {
			$scope.currentUserAnswers = data;
		});


						$http.get( "/data/tests/userAnswers?userID="+userId+"&testID="+testId ).success( function ( data ){
							$scope.currentUserAnswers = data;
							$scope.success = { "message" : "Success" };
						});
						
						
					};

	


});


app.controller( 'UserController', function( $rootScope, $scope, $http, $location, $window ) {
	
		$scope.loadUsers = function() { 
		var urlBase = "";
		$scope.toggle = true;
		$scope.selection = [];
		$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
		$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
		$http.defaults.headers.post["Content-Type"] = "application/json";
		$http.get(urlBase + '/data/tests/getUsers').success( function(data) {

			if (data != undefined) {
				$scope.users = data;
			} else {
				$scope.users = [];
			}
		});
	   };
	
	$scope.addUser = function() {
		
		$http.get( "/data/user/create?email="+$scope.email+"&name="+$scope.name+"&surname="+$scope.surname+"&admin_status=false" ).success( function(data) {
		
			$scope.loadUsers();
			
		});
	};
	
	$scope.del = function(id) {
		
		$http.get( "/data/user/delete?id=" + id).success( function(data) {
		
			$scope.loadUsers();
			
		});
	};
	
});
	



