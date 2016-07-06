
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








app.controller('ExampleController', ['$scope', '$interval',
                                      function($scope, $interval) {
    $scope.format = 'M/d/yy h:mm:ss a';
    $scope.blood_1 = 100;
    $scope.blood_2 = 120;

    var stop;
    $scope.fight = function() {
      // Don't start a new fight if we are already fighting
      if ( angular.isDefined(stop) ) return;

      stop = $interval(function() {
        if ($scope.blood_1 > 0 && $scope.blood_2 > 0) {
          $scope.blood_1 = $scope.blood_1 - 3;
          $scope.blood_2 = $scope.blood_2 - 4;
        } else {
          $scope.stopFight();
        }
      }, 100);
    };

    $scope.stopFight = function() {
      if (angular.isDefined(stop)) {
        $interval.cancel(stop);
        stop = undefined;
      }
    };

    $scope.resetFight = function() {
      $scope.blood_1 = 100;
      $scope.blood_2 = 120;
    };

    $scope.$on('$destroy', function() {
      // Make sure that the interval is destroyed too
      $scope.stopFight();
    });
  }])
// Register the 'myCurrentTime' directive factory method.
// We inject $interval and dateFilter service since the factory method is DI.
.directive('myCurrentTime', ['$interval', 'dateFilter',
  function($interval, dateFilter) {
    // return the directive link function. (compile function not needed)
    return function(scope, element, attrs) {
      var format,  // date format
          stopTime; // so that we can cancel the time updates

      // used to update the UI
      function updateTime() {
        element.text(dateFilter(new Date(), format));
      }

      // watch the expression, and update the UI on change.
      scope.$watch(attrs.myCurrentTime, function(value) {
        format = value;
        updateTime();
      });

      stopTime = $interval(updateTime, 1000);

      // listen on DOM destroy (removal) event, and cancel the next UI update
      // to prevent updating time after the DOM element was removed.
      element.on('$destroy', function() {
        $interval.cancel(stopTime);
      });
    }
  }]);

