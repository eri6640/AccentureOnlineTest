
var app = angular.module( 'CoreAPP', [ 'ngRoute', 'ngCookies', 'ngAnimate' ] );

app.config( function( $routeProvider, $httpProvider, $locationProvider ) {
	
	$routeProvider.when('/home', {
		controller : 'TestListController',
		templateUrl : 'page/home.html'
	});
	
	$routeProvider.when('/', {
		redirectTo: '/home'
	});
	
	$routeProvider.when('/login', {
		templateUrl : 'page/login.html'
	});
	
	$routeProvider.when('/question', {
		controller : 'QuestionController',
		templateUrl : 'page/question.html'
	});
	
	$routeProvider.when('/question-end', {
		controller : 'QuestionEndController',
		templateUrl : 'page/question_end.html'
	});
	
	$routeProvider.otherwise({ redirectTo: '/home' });

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






app.controller( 'LoginController', function( $rootScope, $scope, $http, $cookies, $location, $window ) {

	$scope.showLogin = false;
	$scope.error = false;
	$scope.success = false;
	
	var loggedInRes = $http.post( '/data/auth/isloggedin' );
	loggedInRes.success( function( data, status, headers, config ) {
		$scope.showLogin = ! data;
	});
	
	/*var req = {
		method: 'POST',
		url: '/data/auth/isloggedin',
		headers: {
			'X-Requested-With': 'XMLHttpRequest',
	        'Accept': 'application/json'
		}
	}**/

	$location.path( '/login', false );
	
	
	$scope.loginSubmit = function() {
		
		if( $scope.email && $scope.password ){
			//$window.alert( "email: " + $scope.email + " password: " + $scope.password );
			
			$scope.success = false;
			
			var dataObj = {
				email : $scope.email,
				password : $scope.password
			};
			
			var doLoginRes = $http.post( '/data/auth/doLogin', dataObj );
			
			doLoginRes.success( function( data, status, headers, config ) {
				$scope.success = data;
				$scope.success = { "message" : "Success" };
				$window.location.href = "/";
			});
			
		}
		else{
			$scope.error = { "message" : "error" };
		}
		
	};
	
	$scope.logout = function() {

		var logoutRes = $http.post( '/data/auth/doLogout' );
		logoutRes.success( function( data, status, headers, config ) {
			$window.location.href = "/#/login";
		});

	};
	
  
});//login controller







app.controller( 'MainController', function( $rootScope, $scope, $http, $location, $window ) {

});

app.controller( 'TestListController', function( $rootScope, $scope, $http, $location, $window ) {
	
	if( $location.path() == '/login' ) return;
	
	$scope.test_list = null;
	$scope.show_desc = false;
	
	$scope.getAvailableTests =  function( testId ) {
		
		var selectAvailableTestsRes = $http.post( '/data/tests/selectAvailableTests' );
		selectAvailableTestsRes.success( function( data, status, headers, config ) {
			if( data ){
				$scope.test_list = data;
			}
		});
		
	};
	$scope.getAvailableTests();
	
	
	$scope.showTestDescription = function( test_title, test_description ) {
		
		$scope.test_title = test_title;
		$scope.test_description = test_description;
		$scope.show_desc = true;
		
	};
	
	$scope.startTest = function() {
		
		if( $scope.selectedTest ) {
			
			var getTestInProgressRes = $http.post( '/data/tests/getTestInProgress' );
			getTestInProgressRes.success( function( data, status, headers, config ) {
				if( ! data ){
					
					var dataObj = {
							id : $scope.selectedTest - 0
						};
					
					var startTestRes = $http.post( '/data/tests/startTest', dataObj );
					startTestRes.success( function( data, status, headers, config ) {
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
	
	$scope.answer_testfield = null;
	$scope.timerTime = new Date().getTime();
	
	$scope.nextQuestion = function( testId ) {
		
		var getNextQuestionRes = $http.post( '/data/tests/getNextQuestion', { id : testId - 0 } );
		getNextQuestionRes.success( function( data, status, headers, config ) {

			if( data ){
				$scope.question = data;
				
				$scope.loadQuestionHTML = null;
				$scope.answer_testfield = null;
				
				switch( $scope.question.type ) {
					case 1:
						$scope.loadQuestionHTML = 'page/answer_types/single.html';
						break;
					case 2:
						$scope.loadQuestionHTML = 'page/answer_types/multi.html';
						break;
					case 3:
						$scope.loadQuestionHTML = 'page/answer_types/text.html';
						break;
					case 4:
						$scope.loadQuestionHTML = 'page/answer_types/drawing.html';
						break;
					default:
						$scope.loadQuestionHTML = 'page/answer_types/single.html';
				}
				
				
				var getQuestionChoicesRes = $http.post( '/data/tests/getQuestionChoices', { id : $scope.question.id - 0 } );
				getQuestionChoicesRes.success( function( data, status, headers, config ) {
					if( data ){
						$scope.answers = data;
					}
					else{
						//metam atpakalj uz izvelni
						$scope.forceStopTest( testId );
					}
				});
				
				var getTimerTimeRes = $http.post( '/data/tests/getTimerTime', { id : testId - 0 } );
				getTimerTimeRes.success( function( data, status, headers, config ) {
					if( data ){
						$scope.timerTime = data;
					}
					else{
						//metam atpakalj uz izvelni
						$scope.forceStopTest( testId );
					}
				});
				
			}
			else{
				//paradam, ka visi jautajumi ir izpilditi
				$scope.forceStopTest( testId );
			}
			
		});
		
		//
		//
		//
		
	};
	
	$scope.loadTestInProgress = function() {
		
		var getTestInProgressRes = $http.post( '/data/tests/getTestInProgress' );
		getTestInProgressRes.success( function( data, status, headers, config ) {
			if( data ){
				$scope.nextQuestion( data.id );
			}
			else{
				//metam atpakalj uz izvelni
				$window.location.href = "/";
			}
		});
	};
	$scope.loadTestInProgress();
	
	$scope.selectRatio = function( value ) {
		$scope.selectedData = value;
	};
	
	$scope.testareaContent = function( value ) {
		$scope.answer_testfield = value;
	};
	
	
	$scope.submitQuestion = function( question_type ) {
		
		switch( question_type ) {
			case 1:
				if( $scope.selectedData ){
					//$window.alert( $scope.selectedData );
					$scope.saveAnswer( $scope.selectedData, question_type );
				}
				else{
					$window.alert("nav sniegta atbilde! - 1");
				}
				break;
			case 2:
			    $scope.albumNameArray = [];
			    angular.forEach($scope.answers, function(answer, question_type){
			      if(answer.selected){
			    	  $scope.albumNameArray.push( answer.id );
			      }
			    });
			    //$window.alert( JSON.stringify( $scope.albumNameArray ) );
			    $scope.saveAnswer( JSON.stringify( $scope.albumNameArray ), question_type );
				break;
			case 3:
				if( $scope.answer_testfield ){
					$scope.saveAnswer( $scope.answer_testfield, question_type );
				}
				else{
					$window.alert("nav sniegta atbilde! - 3" );
				}
				break;
			case 4:
				var id = document.getElementById("sampleBoard");
			    var img = id.toDataURL("image/png");
			    //$scope.saveAnswer( img );
			    $scope.saveAnswer( "image" );
				break;
			default:
				//dsfg
		}
		//$window.alert("submitQuestion");
	};
	
	$scope.saveAnswer = function( user_answers, question_type ) {
		
		if( $scope.question ){
			
			var dataObj = {
				id : $scope.question.id,
				answer : user_answers
			};
			
			var saveAnswerRes = $http.post( '/data/tests/saveAnswer', dataObj );
			saveAnswerRes.success( function( data, status, headers, config ) {
				if( data ){
					//$window.alert( "saved" );
					$scope.loadTestInProgress();
				}
				else{
					//$window.alert( "save error" );
				}
			});
		}
		else{
			$window.alert( "nevar piekljut jautajumam" );
		}
		
	};
	
	$scope.forceStopTest = function( testId ) {
		
		var forceStopTestRes = $http.post( '/data/tests/forceStopTest', { id : testId - 0 } );
		forceStopTestRes.success( function( data, status, headers, config ) {
			if( data ){
				$window.location.href = "/#/question-end";
			}
			else{
				//$window.alert( "save error" );
			}
		});
		
		
	};
	
	$scope.forceStopTestAlert = function( test_id ) {
		
		var confirmThis = $window.confirm("forceStopTestAlert()");
		if( confirmThis == true ) {
			$scope.forceStopTest( test_id );
		}		
		
	};
	

	
	$scope.pinPoint = function( question_id ) {
		
		var forceStopTestRes = $http.post( '/data/tests/pinPoint', { id : question_id - 0 } );
		forceStopTestRes.success( function( data, status, headers, config ) {
			if(data){
				$scope.loadTestInProgress();
				$window.alert( "pinPoint success" );
			}
			else{
				$window.alert( "pinPoint error" );
			}
		});
		
	};
	
	
	$scope.stylePath = 'resources/css/question_themes/theme1.css';

	$scope.changePath = function() {
		$scope.stylePath = 'resources/css/question_themes/theme1.css';
	};

	$scope.changePath2 = function() {
		$scope.stylePath = 'resources/css/question_themes/theme2.css';
	};

	$scope.changePath3 = function() {
		$scope.stylePath = 'resources/css/question_themes/theme3.css';
	};
	
	
});

app.controller( 'QuestionEndController', function( $rootScope, $scope, $window ) {
	
	$scope.backToStart = function() {
		$window.location.href = "/#/home";
	};
});





app.controller( 'TimerController', [ '$scope', '$interval', '$window', function( $scope, $interval, $window ) {
	$scope.timeleft = 0;

    var timer;
    var startTimer = function() {

		if( angular.isDefined( timer ) ) return;
	
		timer = $interval( function() {
			$scope.timeleft = ( new Date().getTime() - $scope.timerTime ) / 1000;
			
			if( $scope.timeleft >= 60 * 60 ) {
				$scope.stopTimer();
				
				
				//
			}
		}, 1000);
    };

    $scope.stopTimer = function( testId ) {
    	if( angular.isDefined( timer ) ) {
    		$interval.cancel( timer );
    		timer = undefined;
    		$scope.forceStopTest( testId );
    	}
    };

    $scope.$on('$destroy', function() {
    	$scope.stopTimer( $scope.question.tests.id );
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
