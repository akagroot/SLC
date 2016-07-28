'use strict';

angular.module('myApp')

.controller('AddUserCtrl', AddUserCtrl);

function AddUserCtrl(userService, $rootScope, $state) {
	var viewmodel = this;

	viewmodel.saving = false;
	viewmodel.error = false;
	viewmodel.errorMessage = null;
	viewmodel.success = false;

	if($rootScope.userProfile == null || $rootScope.userProfile.role != "ADMIN") {
		$state.go('redirectToDashboard');
	}

	viewmodel.addUser = addUser;
	
	resetAddUserModel();

	function resetAddUserModel() {
		var holdRole = null;
		if(viewmodel.addUserModel != null && viewmodel.addUserModel.role != undefined) {
			holdRole = viewmodel.addUserModel.role;
		}

		viewmodel.addUserModel = new Object();

		if(holdRole != null) {
			viewmodel.addUserModel.role = holdRole;	
		} else {
			viewmodel.addUserModel.role = "ATHLETE";
		}
	}

	function addUser(form) {
		if(form.$valid) {
			viewmodel.success = false;
			viewmodel.error = false;
			viewmodel.saving = true;
			userService.addUser(viewmodel.addUserModel)
			.then(function(response) {
				viewmodel.success = true;
				resetAddUserModel();
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.error;
			}).finally(function() {
				viewmodel.saving = false;
			});
		}
	}
}