
var app = angular.module( 'CoreAPP', [ 'ngRoute' ] );

app.config( function( $routeProvider, $httpProvider ) {
	
	$routeProvider.when('/', {
		controller : 'login',
		templateUrl : 'login.html'
	}).when('/login', {
		controller : 'login',
		templateUrl : 'login.html'
	}).when('/home', {
		controller : 'home',
		templateUrl : 'home.html'
	}).otherwise({ redirectTo: '/' });

	/*$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';
	$httpProvider.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded';

});

app.run( function( $location ) {
	
	if( $location.path() != '/login' ){
		//$location.path("/login");
	}
	
});

app.controller( 'home', function( $rootScope, $scope, $http, $location ) {
	
	
	//$window.location.href = 'http://eri6640.eu/forum/';
	//$location.path("/login");
	
	//authenticate
	var authenticate = function( credentials, callback ) {

		var headers = credentials ? { authorization : "Basic " + btoa( credentials.username + ":" + credentials.password ) } : {};
		
		var emaill = "repeat?";
		//$http.get("/data/repeat?field1="+emaill).success(function (data){
			//$scope.repeat = data;
		//})
		//$scope.users[0].email

	}//authenticate
	
	
	authenticate();
	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				//$location.path("/");
				$scope.error = false;
			}
			else {
				//$location.path("/login");
				$scope.error = true;
			}
		});
	};
	
	
});//controller('home'

app.controller('login', function($rootScope, $scope, $http, $location) {

  
});

