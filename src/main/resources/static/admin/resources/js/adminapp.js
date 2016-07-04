
var app = angular.module( 'AdminAPP', [ 'ngRoute' ] );

app.config( function( $routeProvider, $httpProvider, $locationProvider ) {
	
	$routeProvider.when('/home', {
		controller : 'MainController',
		templateUrl : '/admin/page/admin_main.html'
	}).when('/', {
		redirectTo: '/home'
	}).when('/ui', {
		controller : 'MainController',
		templateUrl : '/admin/page/ui.html'
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
	
	if( $location.path == "/login" ){
		$location.path('/');
	}
	
	$scope.logout = function() {
		
		$http.get( "/data/auth/isloggedin" ).success( function ( data ) {
			$scope.loggedIn = data;
		});
		
		if( $scope.loggedIn ){
			$http.get( "/data/auth/logout" ).success( function ( data ) {
				$scope.loggedIn = data;
			});
		}
		
		$location.path('/');
		
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
				$window.location.href = "/";
				//$location.path( '/' );
			});
			
		}
		else{
			$scope.error = { "message" : "Nav aizpilditi visi lauki!" };
		}
		
	};
	
  
});

