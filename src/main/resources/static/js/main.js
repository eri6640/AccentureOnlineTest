
var app = angular.module( 'CoreAPP', [ 'ngRoute' ] );

app.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'login'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});
app.controller('home', function($scope, $http) {
	
	//authenticate
	var authenticate = function(credentials, callback) {

		var headers = credentials ? { authorization : "Basic " + btoa( credentials.username + ":" + credentials.password ) } : {};

		$http.get( 'user', { headers : headers} ).success( function( data ) {
			if( data.name ) {
				$rootScope.authenticated = true;
			}
			else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}).error(function() {
			$rootScope.authenticated = false;
			callback && callback();
		});

	}//authenticate
	
	$scope.userdata = {content: 'ddddddd'}
	$http.get('/data/userdata/?first=kkk').success(function(data) {
		$scope.userdata = data;
	})
});

app.controller('login', function($rootScope, $scope, $http, $location) {

  
});