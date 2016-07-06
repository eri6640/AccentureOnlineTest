
var app = angular.module( 'CoreAPP', [ 'ngRoute' ] );

app.config( function( $routeProvider, $httpProvider, $locationProvider ) {
	
	$routeProvider.when('/home', {
		controller : 'MainController',
		templateUrl : 'page/home.html'
	}).when('/', {
		redirectTo: '/home'
	}).when('/login', {
		controller : 'LoginController',
		templateUrl : 'page/login.html'
	}).when('/answers', {
		controller : 'MainController',
		templateUrl : 'page/answers.html'
	}).otherwise({ redirectTo: '/home' });

	//$locationProvider.html5Mode({
	//	enabled: true,
	//	requireBase: false
	//});

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});

app.controller('lister', function($scope, $http) {
    var urlBase="";
    $scope.toggle=true;
    $scope.selection = [];
    $scope.statuses=['ACTIVE','COMPLETED'];
    $scope.priorities=['HIGH','LOW','MEDIUM'];
    $http.defaults.headers.post["Content-Type"] = "application/json";
	$http.get(urlBase + '/data/tests/selectallTests').success(function (data) {

 	
    	if (data != undefined) {
        	$scope.tests = data;
    	} else {
        	$scope.tests = [];
    	}
   	 
    	
	});
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
	
	$scope.logout = function() {
		
		$http.get( "/data/auth/isloggedin" ).success( function ( data ) {
			$scope.loggedIn = data;
		});
		
		if( $scope.loggedIn ){
			$http.get( "/data/auth/logout" ).success( function ( data ) {
				$scope.loggedIn = data;
			});
		}

		$window.location.href = "/";
		
	};
	
	$scope.testDescription = function( testId ) {
		
		$http.get( "/data/tests/getTest?testId=" + testId ).success( function ( data ) {
			if( data ){
				
			}
			else{
				
			}
		});
		
	};
	
	
});

app.controller( 'TestsController', function( $rootScope, $scope, $http, $location, $window ) {
	
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

    var stop;
    var startTimer = function() {

		if( angular.isDefined( stop ) ) return;
	
		stop = $interval( function() {
			$scope.timeleft = ( new Date().getTime() - $scope.timerTime ) / 1000;
			
			if( $scope.timeleft >= 10 ) {
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
    	if( angular.isDefined( stop ) ) {
    		$interval.cancel( stop );
    		stop = undefined;
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

