'use strict';

angular.module('myApp')

.controller('StandardsCtrl', StandardsCtrl);

function StandardsCtrl(standardsService, exerciseService, userService, $rootScope, $state, $scope, $stateParams, $log, $q) {

	var viewmodel = this;
	viewmodel.userId = $stateParams.userId;
	viewmodel.data = null;
	viewmodel.dataLoading = true;
	viewmodel.saving = false;
	viewmodel.successful = false;
	viewmodel.successfulMessage = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;
	viewmodel.warning = false;

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

	viewmodel.exerciseChanged = exerciseChanged;
	viewmodel.setStandard = setStandard;

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

		viewmodel.weights = exerciseService.getWeights();
		viewmodel.reps = exerciseService.getReps();

		$q.all([
			exerciseService.getGroupedExercises().then(function(exercises) {
				$log.debug("StandardsCtrl.getGroupedExercises: ", exercises);
				viewmodel.groupedExercises = exercises;
				viewmodel.selectedStandard
			}, function(error) {
				$log.error("exerciseService.getGroupedExercises error: ", error);
			}), 
			userService.getUserData(viewmodel.userId).then(function(response) {
				$log.debug("StandardsCtrl.getUserData: ", response);
				viewmodel.userProfile = response.data;
			}, function(error) {
				$log.error("StandardsCtrl.getUserData error: ", error);
			})
		]).finally(function() {
			setSelectedStandard(viewmodel.userProfile.standard);

			if(viewmodel.selectedStandard == undefined || viewmodel.selectedStandard == null) {
				viewmodel.warning = true;
			}

			viewmodel.dataLoading = false;
		});
	}

	function setSelectedStandard(object) {
		$log.debug("setSelectedStandard: ", object);

		if(object == null) {
			viewmodel.selectedStandard = null;
			return; 
		}

		viewmodel.selectedStandard = new Object();
		viewmodel.selectedStandard.exerciseId = "" + angular.copy(object.exerciseId);
		viewmodel.selectedStandard.weight = "" + angular.copy(object.weight);
		viewmodel.selectedStandard.reps = "" + angular.copy(object.reps);
	}

	function exerciseChanged() {
		$log.debug("selectedStandard: ", viewmodel.selectedStandard);
		$.each(viewmodel.userProfile.exercisesByDate, function(i, d) {
			$.each(d.exercises, function(i, e) {
				if(e.exerciseId == viewmodel.selectedStandard.exerciseId) {
					setSelectedStandard(e);
					$log.debug("Found existing exercise for user.");
				}
			});
		});
		$log.debug("selectedStandard: ", viewmodel.selectedStandard);
	}

	function clearMessages() {
		viewmodel.successful = false;
		viewmodel.error = false;
		viewmodel.warning = false;
	}

	function setStandard(form) {
		viewmodel.saving = true;
		clearMessages();

		viewmodel.selectedStandard.userId = angular.copy(viewmodel.userId);

		standardsService.updateStandard(viewmodel.selectedStandard)
		.then(function(response) {
			viewmodel.successful = true;
			viewmodel.successfulMessage = "The new standard was set successfully.";
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}
}