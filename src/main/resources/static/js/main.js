
var app = angular.module( 'CoreAPP', [ 'ngRoute' ] );

app.config( function( $routeProvider, $httpProvider ) {
	
	$routeProvider.when('/home', {
		controller : 'MainController',
		templateUrl : 'home.html'
	}).when('/', {
		redirectTo: '/home'
	}).when('/login', {
		controller : 'LoginController',
		templateUrl : 'login.html'
	}).otherwise({ redirectTo: '/home' });

	/*$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});


app.controller( 'MainController', function( $rootScope, $scope, $http, $location ) {
	
	$scope.hidePage = true;
	
	$http.get( "/data/loggedin/" ).success( function ( data ) {
		$scope.loggedIn = data.response;
		if( $location.path() != '/login' && data.response == 'false' ){
			$location.path("/login");
		}
		else{
			$scope.hidePage = false;
		}
	})
	
	
	//$window.location.href = 'http://eri6640.eu/forum/';
	//$location.path("/login");
	
	
	
});//controller('home'

app.controller( 'LoginController', function( $rootScope, $scope, $http, $location, $window ) {

	$http.get( "/data/loggedin/" ).success( function ( data ) {
		$scope.loggedIn = data.response;
	})
	
	$scope.hideContainer = false;
	
	$scope.loginSubmit = function() {
		$window.alert( "email: " + $scope.email + " password: " + $scope.password );
	};
  
});

