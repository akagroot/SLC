'use strict';

angular.module('myApp')

.controller('UsersCtrl', UsersCtrl);

function UsersCtrl(userService, $rootScope, $state, $log, storageService) {
	var LS_SELECTED_ATHLETE_FILTER_KEY = "selectedAthleteFilter";

	var viewmodel = this;
	viewmodel.users = null;
	viewmodel.dataLoading = true;
	viewmodel.error = false;
	viewmodel.errorMessage = null;
	viewmodel.userProfile = null;
	viewmodel.isAdmin = false;
	viewmodel.selectedAthleteFilter = "MY";

	viewmodel.selectedAthleteFilterChanged = selectedAthleteFilterChanged;

	if($rootScope.userProfile == null || !$rootScope.isAdmin) {
		$log.info("UsersCtrl.redirectToDashboard");
		$state.go('redirectToDashboard');
	}

	loadPage();

	function loadPage() {
		var previouslySetFilter = storageService.get(LS_SELECTED_ATHLETE_FILTER_KEY);
		if(previouslySetFilter != null && previouslySetFilter != undefined) {
			viewmodel.selectedAthleteFilter = previouslySetFilter;
		}

		viewmodel.userProfile = $rootScope.userProfile;
		viewmodel.isAdmin = $rootScope.isAdmin;

		userService.getAllUsers()
		.then(function(response) {
			$log.debug("UsersCtrl.getAllUsers: ", response.data);
			viewmodel.users = response.data;
			viewmodel.dataLoading = false;
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error;
		});
	}

	function selectedAthleteFilterChanged() {
		console.log("Selected: ", viewmodel.selectedAthleteFilter);
		storageService.set(LS_SELECTED_ATHLETE_FILTER_KEY, viewmodel.selectedAthleteFilter);
	}
}