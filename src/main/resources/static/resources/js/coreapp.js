
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
	
	$scope.startTest = function( testId ) {
		
		$http.get( "/data/tests/startTest?testId=" + testId ).success( function ( data ) {
			
			if( data ){
				//parmetam uz atbilzu lapu
			}
			else{
				//atjaunojam esosho + izvadam zinju, ka ir jau sakts vai izpildits attiecigais tests
			}
			
		});
		
	};
	
});

app.controller( 'QuestionController', function( $rootScope, $scope, $http, $location, $window ) {
	
	$scope.testDescription = function( testId ) {
		
		$http.get( "/data/tests/getQuestion?testId=" + testId ).success( function ( data ) {
			
			if( data ){
				
			}
			else{
				//metam atpakalj uz izvelni
			}
			
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

