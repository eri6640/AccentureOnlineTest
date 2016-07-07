
var app = angular.module( 'CoreAPP', [ 'ngRoute', 'ngAnimate' ] );

app.config( function( $routeProvider, $httpProvider, $locationProvider ) {
	
	$routeProvider.when('/home', {
		controller : 'TestListController',
		templateUrl : 'page/home.html'
	}).when('/', {
		redirectTo: '/home'
	}).when('/login', {
		controller : 'LoginController',
		templateUrl : 'page/login.html'
	}).when('/question', {
		controller : 'QuestionController',
		templateUrl : 'page/question.html'
	}).otherwise({ redirectTo: '/home' });

	//$locationProvider.html5Mode({
	//	enabled: true,
	//	requireBase: false
	//});

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});

app.run( [ '$route', '$rootScope', '$location', function ( $route, $rootScope, $location ) {
    var original = $location.path;
    $location.path = function (path, reload) {
        if (reload === false) {
            var lastRoute = $route.current;
            var un = $rootScope.$on('$locationChangeSuccess', function () {
                $route.current = lastRoute;
                un();
            });
        }
        return original.apply($location, [path]);
    };
}]);



app.controller( 'MainController', function( $rootScope, $scope, $http, $location, $window ) {
	
});

app.controller( 'TestListController', function( $rootScope, $scope, $http, $location, $window ) {
	
	if( $location.path() == '/login' ) return;
	
	$scope.test_list = null;
	$scope.show_desc = false;
	
	$scope.getAvailableTests =  function( testId ) {
		$http.get( "/data/tests/selectAvailableTests" ).success( function ( data ) {
	
			if( data ){
				//$window.alert( data );
				$scope.test_list = data;
			}
			else{
				//$window.alert("false");
			}
		});
	};
	$scope.getAvailableTests();
	
	
	$scope.testDescription = function( testId ) {
		
		$http.get( "/data/tests/getTest?testId=" + testId ).success( function ( data ) {
			if( data ){
				$scope.test_title = data.test.title;
				$scope.test_description = data.test.description;
				$scope.show_desc = true;
			}
			else{
				$scope.show_desc = false;
			}
		});
		
	};
	
	$scope.startTest = function() {
		
		if( $scope.selectedTest ) {
			
			$http.get( "/data/tests/getTestInProgress" ).success( function ( data ) {
				
				if( ! data ){
					$http.get( "/data/tests/startTest?testId=" + $scope.selectedTest ).success( function ( data ) {
						
						if( data ){
							$window.location.href = "/#/question";
						}
						else{
							$window.alert("izveletais tekts jau izpildits");
						}
						
					});
				}
				else{
					//jau ir tests progressa
					$window.alert("tests jau progressaa");
					$window.location.href = "/#/question";
				}
				
			});
		}
		else{
			//nav izveletes neviens tests
			$window.alert("nav nekas izveletes");
		}
		
	};
	
	
});

app.controller( 'QuestionController', function( $rootScope, $scope, $http, $location, $window ) {
	
	$scope.nextQuestion = function( testId ) {
		
		$http.get( "/data/tests/getNextQuestion?testId=" + testId ).success( function ( data ) {
			
			if( data ){
				$scope.question = data.question;

				$http.get( "/data/tests/getQuestionChoices?questionId=" + $scope.question.id ).success( function ( data ) {
					
					if( data ){
						$scope.answers = data;
					}
					else{
						//metam atpakalj uz izvelni
						$window.location.href = "/";
					}
					
				});
				
			}
			else{
				//paradam, ka visi jautajumi ir izpilditi
			}
			
		});
		
	};
	
	
	$http.get( "/data/tests/getTestInProgress" ).success( function ( data ) {
		
		if( data ){
			$scope.nextQuestion( data.test.id );
		}
		else{
			//metam atpakalj uz izvelni
			$window.location.href = "/";
		}
		
	});
	
	$scope.selectRatio =  function( value ) {
		$scope.selectedData = value;
	}
	
	
	$scope.submitQuestion = function( question_type ) {
		
		if( question_type == 1 ){
			if( $scope.selectedData ){
				$window.alert( $scope.selectedData );
			}
			else{
				$window.alert("nav sniegta atbilde!");
			}
		}
		else if( question_type == 2 ){
			
		}
		else if( question_type == 3 ){
			
		}
		else if( question_type == 4 ){
			
		}
		else{
			
		}
		$window.alert("submitQuestion");
	}
	
	
});



app.controller( 'LoginController', function( $rootScope, $scope, $http, $location, $window ) {

	$scope.showLogin = false;
	$scope.error = false;
	$scope.success = false;
	
	$http.get( "/data/auth/isloggedin" ).success( function ( data ) {
		$scope.showLogin = ! data;
		if( data ){
			$window.location.href = "/";
		}
	});
	

	$location.path( '/login', false );
	
	
	$scope.loginSubmit = function() {
		
		if( $scope.email && $scope.password ){
			//$window.alert( "email: " + $scope.email + " password: " + $scope.password );
			
			var email = $scope.email;
			var password = $scope.password;
			$scope.success = false;
			
			$http.get( "/data/auth/submitlogin?email="+email+"&password="+password ).success( function ( data ){
				$scope.success = data;
				$scope.success = { "message" : "Success" };
				$window.location.href = "/";
				//$location.path( '/' );
			});
			
		}
		else{
			$scope.error = { "message" : "error" };
		}
		
	};
	
  
});








app.controller( 'TimerController', [ '$scope', '$interval', '$window', function( $scope, $interval, $window ) {
	$scope.timerTime = new Date().getTime();
	$scope.timeleft = 0;

    var timer;
    var startTimer = function() {

		if( angular.isDefined( timer ) ) return;
	
		timer = $interval( function() {
			$scope.timeleft = ( new Date().getTime() - $scope.timerTime ) / 1000;
			
			if( $scope.timeleft >= 60 * 60 ) {
				$scope.stopTimer();
				
				var confirmThis = $window.confirm("Press a button!");
				if( confirmThis == true ) {
					$window.alert("yes");
				}
				else {
					$window.alert("false");
				}
			}
		}, 1000);
    };

    $scope.stopTimer = function() {
    	if( angular.isDefined( timer ) ) {
    		$interval.cancel( timer );
    		timer = undefined;
    	}
    };

    $scope.resetTimer = function() {
    	$scope.timerTime = new Date().getTime();
    	$scope.stopTimer();
    };

    $scope.$on('$destroy', function() {
    	$scope.stopTimer();
	});
    
    startTimer();
}]);

app.filter('secondsToDateTime', function() {
    return function(seconds) {
        var d = new Date(0,0,0,0,0,0,0);
        d.setSeconds(seconds);
        return d;
    };
});

