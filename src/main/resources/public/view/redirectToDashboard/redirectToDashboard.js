'use strict';

angular.module('myApp')

.controller('RedirectToDashboardCtrl', RedirectToDashboardCtrl);
	
function RedirectToDashboardCtrl($rootScope, $scope, $state, $log) {
	if($rootScope.userProfile != null ) {
		$log.info("RedirectToDashboardCtrl redirectUser");
		redirectUser();	
	} else {
		$scope.$watch(function() {
			return $rootScope.userProfile;
		}, function() {
			redirectUser();
		});
	}

	function redirectUser() {
		console.log("redirectUser: ", $rootScope.isAdmin);

		if($rootScope.userProfile != null) {
			if($rootScope.isAdmin) {
				$state.go('users');
			} else {
				$state.go('userProfile');
			}	
		}
	}
}