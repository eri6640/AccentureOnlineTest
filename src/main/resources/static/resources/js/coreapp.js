
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
	}).otherwise({ redirectTo: '/home' });

	/*$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});


app.controller( 'MainController', function( $rootScope, $scope, $http, $location, $window ) {
	
	//$scope.hidePage = true;
	
	/*$http.get( "/data/loggedin" ).success( function ( data ) {
		$scope.loggedIn = data.response;
		//if( $location.path() != '/login' && data.response == 'false' ){
		//$window.alert( data.response );
		if( ! data.response ){
			$location.path("/login");
		}
		else{
			$scope.hidePage = false;
		}
	});*/
	
	
	//$window.location.href = 'http://eri6640.eu/forum/';
	//$location.path("/login");
	
	
	
});

app.controller( 'LoginController', function( $rootScope, $scope, $http, $location, $window ) {

	//$scope.hideContainer = true;
	
	/*$http.get( "/data/loggedin" ).success( function ( data ) {
		$scope.loggedIn = data.response;
		//$window.alert( data.response );
		if( data.response ){
			$location.path("/home");
		}
	});*/
	
	//$scope.hideContainer = false;
	$scope.error = false;
	$scope.success = false;
	
	$scope.loginSubmit = function() {
		
		if( $scope.email && $scope.password ){
			$window.alert( "email: " + $scope.email + " password: " + $scope.password );
			
			
			
		}
		else{
			$scope.error = { "message" : "Nav aizpilditi visi lauki!" };
		}
		
	};
	
  
});

