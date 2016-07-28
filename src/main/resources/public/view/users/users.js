'use strict';

angular.module('myApp')

.controller('UsersCtrl', UsersCtrl);

function UsersCtrl(userService, $rootScope, $state) {
	var viewmodel = this;
	viewmodel.users = null;
	viewmodel.dataLoading = true;
	viewmodel.error = false;
	viewmodel.errorMessage = null;

	if($rootScope.userProfile == null || $rootScope.userProfile.role != "ADMIN") {
		$state.go('redirectToDashboard');
	}

	loadPage();

	function loadPage() {
		userService.getAllUsers()
		.then(function(response) {
			viewmodel.users = response.data;
			viewmodel.dataLoading = false;
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error;
		});
	}
}