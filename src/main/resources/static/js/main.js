
var app = angular.module( 'CoreAPP', [ 'ngRoute' ] );

app.config(function( $routeProvider, $httpProvider ) {

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
app.controller('home', function( $rootScope, $scope, $http ) {
	
	//authenticate
	var authenticate = function( credentials, callback ) {

		var headers = credentials ? { authorization : "Basic " + btoa( credentials.username + ":" + credentials.password ) } : {};



	}//authenticate
	
	//$scope.userdata = { content: 'ddddddd' };
	//var jqxhr = $http.post('/data/repeat').success(function(data) {
	//	$scope.userdata = data;
	//})
	/*
	var req = {
		method: 'POST',
		url: 'http://localhost:8080/data/repeat.html',
		headers: {
			'Content-Type': undefined
		},
		data: { field1: 'test' }
	}

	$http(req).then( function( data ){
		$scope.repeat = data;
	});*/
	
	var res = $http.post('/data/repeat.html', { field1: 'test' } );
	res.success(function( data, status ) {
		$scope.repeat = data;
	});
	res.error(function(data, status) {
		alert( "failure message: " + JSON.stringify({data: data}));
	});	
	
	
	
	authenticate();
	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				$location.path("/");
				$scope.error = false;
			}
			else {
				$location.path("/login");
				$scope.error = true;
			}
		});
	};
	
	
});//controller('home'

app.controller('login', function($rootScope, $scope, $http, $location) {

  
});