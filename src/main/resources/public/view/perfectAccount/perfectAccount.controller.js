'use strict';

angular.module('myApp')

.controller('PerfectAccountCtrl', PerfectAccountCtrl);

function PerfectAccountCtrl(userService, $rootScope, $state, $scope, $stateParams, $log, $q) {

	var viewmodel = this;
	viewmodel.dataLoading = true;
	viewmodel.saving = false;
	viewmodel.successful = false;
	viewmodel.successfulMessage = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;

	if($rootScope.userProfile == null) {
		$scope.$watch(function() {
			return $rootScope.userProfile;
		}, function() {
			if($rootScope.userProfile != null) {
				loadPage();	
			}
		})
	}
	else {
		loadPage();
	}

	viewmodel.setAccount = setAccount;

	function isset(value) {
		return value != null && value != undefined && value.length > 0;
	}

	function loadPage() {
		if($rootScope.userProfile.role != "ADMIN") {
			$state.go('redirectToDashboard');
			return;
		}

		if(!isset(viewmodel.userId)) {
			viewmodel.userId = $rootScope.userProfile.id;
		}

		$q.all([
			userService.getAllUsers().then(function(response) {
				$log.debug("PerfectAccountCtrl.getAllUsers: ", response);
				viewmodel.users = response.data;
			}, function(error) {
				$log.error("PerfectAccountCtrl.getAllUsers: ", error);
			}), 
			userService.getPerfectAccount().then(function(response) {
				$log.debug("PerfectAccountCtrl.getPerfectAccount: ", response);
				viewmodel.selectedAccountId = "" + angular.copy(response.data.userProfileModel.id);
			}, function(error) {
				viewmodel.warning = true;
				viewmodel.warningMessage = "There is not a set perfect account yet.";
			})
		]).finally(function() {
			if(viewmodel.selectedAccountId == undefined || viewmodel.selectedAccountId == null) {
				viewmodel.warning = true;
			}

			viewmodel.dataLoading = false;
		});
	}

	function clearMessages() {
		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.warning = false;
	}

	function setAccount(form) {
		if(!window.confirm("This will change the 'Perfect Account'.  If this new account is not setup correctly, this will make the grades and ratios for all users incorrect.  Are you sure you want to change?")) {
			return;			
		}

		viewmodel.saving = true;
		clearMessages();

		userService.setPerfectAccount(viewmodel.selectedAccountId)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "The new perfect account was set successfully.";
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}
}