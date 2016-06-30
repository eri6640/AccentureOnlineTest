
var app = angular.module( 'acc_teamb', [ 'ngRoute' ] ); // ... omitted code

app.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

});
app.controller('home', function($scope, $http) {
	$scope.userdata = {content: 'ddddddd'}
	$http.get('/data/userdata/?first=kkk').success(function(data) {
		$scope.userdata = data;
	})
});

app.controller('navigation', function($rootScope, $scope, $http, $location) {

  
});