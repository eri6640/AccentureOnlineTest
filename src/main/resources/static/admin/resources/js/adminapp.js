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
				controller : 'TestsController',
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

app.controller('LoginController', function($rootScope, $scope, $http,
		$location, $window) {

	$scope.showLogin = false;
	$scope.error = false;
	$scope.success = false;

	$http.get("/data/auth/isloggedin").success(function(data) {
		$scope.showLogin = !data;
		if (data) {
			//$window.location.href = "/";
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
			});

		} else {
			$scope.error = {
				"message" : "Nav aizpilditi visi lauki!"
			};
		}
	};
	$scope.LogOut = function() {
		$http.get("/data/auth/logout").success(function(data) {
			$window.location.href = "/#/login";
		});
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
		var questionID = questionChoice.testQuestion.tests.id;
		var testsID = questionChoice.testQuestion.id;
		var data = $.param({
			testsID : testsID,
			questionID : questionID

		})
		$http.get(
				"/data/tests/deleteQuestion?testsID=" + testsID
						+ "&questionID=" + questionID).success(function(data) {
			if (data) {
				alert("CANT DELETE!");
			} else {
				alert("DELETED!");
			}
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
				.success(function(data) {
					if (data) {
						alert("DELETED!");
					} else {
						alert("CANT DELETE!");
					}
				}).error(function() {
					alert("CAN'T DELETE THIS Choice");
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

		$http.get(
				"/data/tests/userAnswers?userID=" + userId + "&testID="
						+ testId).success(function(data) {
			$scope.currentUserAnswers = data;
			$scope.success = {
				"message" : "Success"
			};
		});
	};

});

app.controller('UserController', function($rootScope, $scope, $http, $location,
		$window) {

	$scope.showInfoUsers = false;
	$scope.userInfo = "";

	$scope.loadUsers = function() {
		var urlBase = "";
		$scope.toggle = true;
		$scope.selection = [];
		$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
		$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
		$http.defaults.headers.post["Content-Type"] = "application/json";
		$http.get(urlBase + '/data/tests/getUsers').success(function(data) {

			if (data != undefined) {
				$scope.users = data;
			} else {
				$scope.users = [];
			}
		});
	};

	$scope.loadUsers();

	$scope.addUser = function() {

		$http.get(
				"/data/user/create?email=" + $scope.email + "&name="
						+ $scope.name + "&surname=" + $scope.surname
						+ "&admin_status=false").success(function(data) {

			$scope.showInfoUsers = true;
			$scope.userInfo = "User created!";
			$scope.loadUsers();

		});
	};

	$scope.delUser = function(id) {

		$http.get("/data/user/delete?id=" + id).success(function(data) {

			$scope.loadUsers();

		});
	};

});

app.controller('TestsController', function($rootScope, $scope, $http,
		$location, $window) {

	var thisTestID;
	var thisUserID;
	var thisQuestionID;
	$scope.showInfoTests = false;
	$scope.testInfo = "";

	$scope.loadTests = function() {
		var urlBase = "";
		$scope.toggle = true;
		$scope.selection = [];
		$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
		$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
		$http.defaults.headers.post["Content-Type"] = "application/json";
		$http.get(urlBase + '/data/tests/selectallTests').success(
				function(data) {

					if (data != undefined) {
						$scope.tests = data;
					} else {
						$scope.tests = [];
					}
				});
	};

	$scope.loadTests();

	$scope.addTest = function() {
		$scope.getUser = function() {
			var urlBase = "";
			$scope.toggle = true;
			$scope.selection = [];
			$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
			$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
			$http.defaults.headers.post["Content-Type"] = "application/json";
			$http.get(urlBase + '/data/tests/getActiveUser').success(
					function(data) {

						$http.get(
								"/data/tests/create?title=" + $scope.testName
										+ "&userID=" + data + "&description="
										+ $scope.testDescription).success(
								function(data) {

									$scope.showInfoTests = true;
									$scope.testInfo = "Test created!";
									$scope.loadTests();

								});
					});

		};

		$scope.getUser();

	};

	$scope.delTest = function(id) {
		var urlBase = "";
		$scope.toggle = true;
		$scope.selection = [];
		$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
		$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
		$http.defaults.headers.post["Content-Type"] = "application/json";
		$http.get(urlBase + '/data/tests/remove?id=' + id).success(
				function(data) {
					$scope.loadTests();
				});
	};

	$scope.showTestQuestions = false;
	$scope.activeTest;

	$scope.AddTestQuestion = function(test) {
		activeTest = test;
		thisTestID = test.id;
		thisUserID = test.user.id;
		var testId = test.id;
		$http.get("/data/tests/getTestsQuestions?testID=" + testId).success(
				function(data) {
					$scope.questions = data;
					$scope.showTestQuestions = true;
				}).error(function() {
		});

	};
	$scope.showOption = false;

	var questionType;

	$scope.changedValue = function(item) {

		questionType = item;
		$http.get(
				"/data/tests/setQuestionType?questionType=" + questionType
						+ "&questionID=" + thisQuestionID).success(
				function(data) {
					if (data) {

					} else {
						$scope.showAlertChoices = true;
						$scope.choiceWarning = "No question selected!";
					}
				}).error(function() {
			// $scope.showAlertChoices = true;
			// $scope.choiceWarning = "No question created!";
		});

		if (item == 1 || item == 2) {
			$scope.showOption = true;
		} else {
			$scope.showOption = false;
		}
	};

	$scope.AddQuestion = function() {
		$scope.Question;

		if ($scope.Question == null || $scope.Question == "") {
			$scope.showAlertChoices = true;
			$scope.choiceWarning = "No question written!";
		} else {
			$http.get(
					"/data/tests/addQuestion?type=" + questionType + "&testID="
							+ thisTestID + "&userID=" + thisUserID
							+ "&question=" + $scope.Question).success(
					function(data) {
						if (data) {
							$scope.showAlertChoices = true;
							$scope.choiceWarning = "Question created!";
							$scope.AddTestQuestion(activeTest);
						} else {
							$scope.showAlertChoices = true;
							$scope.choiceWarning = "Can't create question!";
						}
						;
					}).error(function() {
				$scope.showAlertChoices = true;
				$scope.choiceWarning = "No question type chosen!";
			});
		}
		;
	};

	$scope.getQuestionID = function(question) {
		thisQuestionID = question.id;
	};

	$scope.choiceWarning = "";
	$scope.AddChoice = function() {
		$scope.showAlertChoices = false;
		$http.get(
				"/data/tests/addChoices?questionID=" + thisQuestionID
						+ "&choice1=" + $scope.option1 + "&choice2="
						+ $scope.option2 + "&choice3=" + $scope.option3
						+ "&choice4=" + $scope.option4).success(function(data) {

			$scope.showAlertChoices = true;
			$scope.choiceWarning = "Choices added!";

		}).error(function() {
			$scope.showAlertChoices = true;
			$scope.choiceWarning = "Can't create choices!";
		});
	};

	$scope.edit = function(id, title, desc) {
		$http.get(
				"/data/tests/edit?id=" + id + "&title=" + title
						+ "&description=" + desc).success(function(data) {
			$scope.loadTests();

		});
	};

});
