'use strict';

angular.module('myApp')
	.directive('menu', function() {
    return {
        scope: {
        }, 
        templateUrl: 'js/directive/menu/menu.html', 
        controller: function($rootScope, $scope, $log) {
            $scope.userProfile = $rootScope.userProfile;

            $scope.$watch(function() {
                return $rootScope.userProfile;
            }, function() {
                console.log("menu.directive.js $rootScope.currentUser: ", $rootScope.userProfile);
                $scope.userProfile = $rootScope.userProfile;
            });
        }
    };
  });
