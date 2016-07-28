'use strict';

angular.module('myApp')

.controller('RedirectToDashboardCtrl', RedirectToDashboardCtrl);
	
function RedirectToDashboardCtrl($rootScope, $scope, $state) {
	if($rootScope.userProfile != null ) {
		redirectUser();	
	}

	function redirectUser() {
		if($rootScope.userProfile.role == 'ADMIN') {
			$state.go('users');
		} else {
			$state.go('userProfile');
		}	
	}
}