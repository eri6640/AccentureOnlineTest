
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
		
		var emaill = "repeat?";
		$http.get("/data/repeat?field1="+emaill).success(function (data){
			$scope.repeat = data;
		})
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

