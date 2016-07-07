var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope) {
  $scope.name = 'World';

});

app.controller('headController', function($scope) {
  $scope.stylePath = 'style.css';
  
  $scope.changePath = function() {
    $scope.stylePath = 'style2.css';
  };
});

