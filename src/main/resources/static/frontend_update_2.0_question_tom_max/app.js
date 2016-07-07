var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope) {
  $scope.name = 'World';

});

app.controller('headController', function($scope) {
  $scope.stylePath = 'css/style.css';
  
  $scope.changePath = function() {
    $scope.stylePath = 'css/style2.css';
  };

 $scope.changePath2 = function() {
    $scope.stylePath = 'css/style.css';
  };
});
