
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
	}).when('/tests', {
		controller : 'lister',
		templateUrl : 'page/tests.html'
	}).otherwise({ redirectTo: '/home' });

	/*$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});*/

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
	
	
	/*var emaill = "repeat?";
	$http.get("/data/repeat?field1="+emaill).success(function (data){
		$scope.repeat = data;
	});*/
	
	
	$scope.loginSubmit = function() {
		
		if( $scope.email && $scope.password ){
			$window.alert( "email: " + $scope.email + " password: " + $scope.password );
			
			
			
		}
		else{
			$scope.error = { "message" : "Nav aizpilditi visi lauki!" };
		}
		
	};
	
  
});

