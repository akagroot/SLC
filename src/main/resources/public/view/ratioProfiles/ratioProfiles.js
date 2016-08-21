'use strict';

angular.module('myApp')

.controller('RatioProfilesCtrl', RatioProfilesCtrl);

function RatioProfilesCtrl(ratioProfileService, $rootScope, $state, $scope, $stateParams) {

	var viewmodel = this;
	viewmodel.profiles = null;
	viewmodel.selectedProfile = null;
	viewmodel.dataLoading = true;
	viewmodel.saving = false;
	viewmodel.successful = false;
	viewmodel.successfulMessage = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;
	viewmodel.editProfile = false;

	viewmodel.addRatioProfileModel = new Object();
	viewmodel.addRatioProfileValueModel = new Object();

	viewmodel.updateRatioProfileModel = new Object();
	viewmodel.updateRatioValueModel = new Object();

	viewmodel.selectedProfileChanged = selectedProfileChanged;
	viewmodel.cancelEditRatioProfile = cancelEditRatioProfile;
	viewmodel.cancelEditRatioValue = cancelEditRatioValue;

	viewmodel.addRatioProfile = addRatioProfile;
	viewmodel.deleteRatioProfile = deleteRatioProfile;
	viewmodel.updateRatioProfile = updateRatioProfile;

	viewmodel.addRatioProfileValue = addRatioProfileValue;
	viewmodel.deleteRatioProfileValue = deleteRatioProfileValue;
	viewmodel.updateRatioProfileValue = updateRatioProfileValue;

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

	function loadPage() {
		if($rootScope.userProfile.role != "ADMIN") {
			$state.go('redirectToDashboard');
			return;
		}

		ratioProfileService.getRatioProfiles(false)
		.then(function(response) {
			viewmodel.profiles = response.data;

			if(viewmodel.selectedProfile != null) {
				$.each(viewmodel.profiles, function(i, e) {
					if(e.id === viewmodel.selectedProfile.id) {
						setSelectedProfile(e);
					}
				});
				console.log("RatioProfilesCtrl.selectedProfile: ", viewmodel.selectedProfile);
			} else if(viewmodel.profiles.length > 0) {
				setSelectedProfile(viewmodel.profiles[0]);
			}
			console.log("RatioProfilesCtrl.loadPage getRatioProfiles: ", viewmodel.profiles);
		}, function(error) {
			console.log("RatioProfilesCtrl.loadPage error: ", error);
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.dataLoading = false;
		});
	}

	function setSelectedProfile(profile) {
		viewmodel.selectedProfile = profile;
		selectedProfileChanged();
	}

	function resetAddRatioProfileModel() {
		viewmodel.addRatioProfileModel = new Object();
	}

	function resetAddRatioProfileValueModel() {
		viewmodel.addRatioProfileValueModel = new Object();
	}

	function addRatioProfile() {
		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;
		ratioProfileService.createRatioProfile(viewmodel.addRatioProfileModel)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Added new ratio profile - " + viewmodel.addRatioProfileModel.name;
			resetAddRatioProfileModel();
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function selectedProfileChanged() {
		viewmodel.updateRatioProfileModel = angular.copy(viewmodel.selectedProfile);

		var updateModel;
		$.each(viewmodel.selectedProfile.values, function(i, e) {
			updateModel = angular.copy(e);
			e.updateModel = updateModel;
			e.editing = false;
		});
	}

	function cancelEditRatioProfile() {
		viewmodel.updateRatioProfileModel = angular.copy(viewmodel.selectedProfile);
		viewmodel.editProfile = false;
	}

	function cancelEditRatioValue(originalModel) {
		delete originalModel.updateModel;
		var updateModel = angular.copy(originalModel);
		originalModel.updateModel = updateModel;
		originalModel.editing = false;
	}

	function updateRatioProfile() {
		if(viewmodel.updateRatioProfileModel == null) {
			viewmodel.error = true;
			viewmodel.errorMessage = "Select a profile to update.";
			return;
		}

		viewmodel.updateRatioProfileModel.id = viewmodel.selectedProfile.id;

		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;

		ratioProfileService.updateRatioProfile(viewmodel.updateRatioProfileModel)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Updated profile - " + viewmodel.updateRatioProfileModel.name;
			viewmodel.editProfile = false;
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function deleteRatioProfile() {
		if(viewmodel.selectedProfile == null) {
			viewmodel.error = true;
			viewmodel.errorMessage = "Select a profile to delete.";
			return;
		}

		if(!window.confirm("Are you sure you want to delete this profile?")) {
			return;
		}

		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;

		ratioProfileService.deleteRatioProfile(viewmodel.selectedProfile.id)
		.then(function(response) {
			viewmodel.selectedProfile = null;
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Deleted the ratio profile";
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function addRatioProfileValue() {
		if(viewmodel.selectedProfile == null) {
			viewmodel.error = true;
			viewmodel.errorMessage = "Select a ratio profile first.";
			return;
		}

		viewmodel.addRatioProfileValueModel.ratioProfileId = viewmodel.selectedProfile.id;

		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;

		ratioProfileService.createRatioProfileValue(viewmodel.addRatioProfileValueModel)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Added new ratio value";
			resetAddRatioProfileValueModel();

			loadPage();
			$('#addReps').focus();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function updateRatioProfileValue(model) {
		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;

		ratioProfileService.updateRatioProfileValue(model)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Updated ratio value";
			model.editing = false;
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function deleteRatioProfileValue(model) {
		if(!window.confirm("Are you sure you want to delete this value?")) {
			return;
		}

		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.saving = true;

		ratioProfileService.deleteRatioProfileValue(model.id)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "Deleted ratio value";
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}
}