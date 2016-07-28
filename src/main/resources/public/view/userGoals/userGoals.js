'use strict';

angular.module('myApp')

.controller('UserGoalsCtrl', UserGoalsCtrl);

function UserGoalsCtrl(userService, exerciseService, $rootScope, $state, $scope, $stateParams) {

	var viewmodel = this;
	viewmodel.userId = $stateParams.userId;
	viewmodel.data = null;
	viewmodel.dataLoading = true;
	viewmodel.saving = false;
	viewmodel.successful = false;
	viewmodel.successfulMessage = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;

	viewmodel.addGoalModel = null;

	viewmodel.addGoal = addGoal;
	viewmodel.deleteGoal = deleteGoal;

	viewmodel.groupedExercises = null;
	viewmodel.weights = exerciseService.getWeights();
	viewmodel.reps = exerciseService.getReps();

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

	function loadData() {
		userService.getGroupedGoals(viewmodel.userId)
		.then(function(response) {
			viewmodel.dataLoading = false;
			viewmodel.data = response.data;
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		});
	}

	function loadPage() {
		if($rootScope.userProfile.role != "ADMIN") {
			$state.go('redirectToDashboard');
			return;
		}

		if(!isset(viewmodel.userId)) {
			viewmodel.userId = $rootScope.userProfile.id;
		}

		exerciseService.getGroupedExercises().then(function(exercises) {
			viewmodel.groupedExercises = exercises;
			console.log("UserGoalsCtrl.loadData groupedExercises: ", viewmodel.groupedExercises);
		}, function(error) {
			console.log("exerciseService.getGroupedExercises error: ", error);
		});

		loadData();
	}

	function isset(value) {
		return value != null && value != undefined && value.length > 0;
	}

	function isAddGoalModelValid() {
		console.log("isAddGoalModelValid? ", viewmodel.addGoalModel);
		return viewmodel.addGoalModel != null && 
			viewmodel.addGoalModel.exerciseId != null && 
			viewmodel.addGoalModel.reps != null && 
			viewmodel.addGoalModel.weight != null;
	}

	function addGoal() {
		if(!isAddGoalModelValid()) {
			viewmodel.error = true;
			viewmodel.errorMessage = "Please fill out all fields.";
			return;
		}

		viewmodel.saving = true;
		viewmodel.error = false;
		viewmodel.successful = false;
		viewmodel.addGoalModel.userId = viewmodel.userId;
		userService.addGoal(viewmodel.addGoalModel)
		.then(function(response) {
			viewmodel.addGoalModel = null;
			viewmodel.successful = true;
			viewmodel.successfulMessage = "The goal was added!";
			loadData();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function deleteGoal(goalId) {
		if(window.confirm("Are you sure you want to delete this goal?")) {
			viewmodel.saving = true;
			viewmodel.error = false;
			viewmodel.successful = false;
			userService.deleteGoal(goalId)
			.then(function(response) {
				viewmodel.successful = true;
				viewmodel.successfulMessage = "The goal was removed!";
				loadData();
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.data.error;
			}).finally(function() {
				viewmodel.saving = false;
			});
		}
	}
}