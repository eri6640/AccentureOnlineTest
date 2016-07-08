
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
	}).when('/question-end', {
		controller : 'MainController',
		templateUrl : 'page/question_end.html'
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
	$('#myModal').focus();
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
	
	$scope.answer_testfield = null;
	
	$scope.nextQuestion = function( testId ) {
		
		$http.get( "/data/tests/getNextQuestion?testId=" + testId ).success( function ( data ) {
			
			if( data ){
				$scope.question = data.question;
				
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

				$http.get( "/data/tests/getQuestionChoices?questionId=" + $scope.question.id ).success( function ( data ) {
					
					if( data ){
						$scope.answers = data;
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
				$window.location.href = "/#/question-end";
			}
			
		});
		
	};
	
	$scope.loadTestInProgress = function() {
		$http.get( "/data/tests/getTestInProgress" ).success( function ( data ) {
			
			if( data ){
				$scope.nextQuestion( data.test.id );
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
			

			$http.get( "/data/tests/saveAnswer?questionId=" + $scope.question.id + "&answer=" + user_answers ).success( function ( data ) {
			
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
	
	$scope.forceStopTest = function( test_id ) {
		
		$http.get( "/data/tests/forceStopTest?testId=" + test_id ).success( function ( data ) {
			
			//
			
		});
		
		
	};
	
	
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

