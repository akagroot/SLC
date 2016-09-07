'use strict';

angular.module('myApp')

.controller('UserProfileCtrl', UserProfileCtrl);

function UserProfileCtrl($stateParams, userService, $q, $log, exerciseService, ratioProfileService, calculateGoalsService, $rootScope, $scope, $state){
	var viewmodel = this;

	console.log("stateParams: ", $stateParams);
	viewmodel.currentUser = $rootScope.userProfile;
	viewmodel.userId = $stateParams.userId;
	viewmodel.userData = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;
	viewmodel.showExerciseAdded = false;
	viewmodel.exerciseAddedError = false;
	viewmodel.exerciseAddedErrorMessage = null;
	viewmodel.loadingData = true;
	viewmodel.saving = false;
	viewmodel.selectedUserRole = null;
	viewmodel.setRoleError = false;
	viewmodel.setRoleErrorMessage = null;
	viewmodel.editUser = false;
	viewmodel.updateUserModel = null;
	viewmodel.updateUserSuccess = false;
	viewmodel.updateUserError = false;
	viewmodel.updateUserErrorMessage = null;
	viewmodel.deleteError = false;
	viewmodel.deleteErrorMessage = null;
	viewmodel.isAdmin = false;
	
	viewmodel.groupedExercises = null;
	viewmodel.weights = exerciseService.getWeights();
	viewmodel.reps = exerciseService.getReps();

	if(viewmodel.userId == null) {
		viewmodel.userId = viewmodel.currentUser.id;
	}

	resetAddExerciseModel();
	loadData()
	.then(function() {
		loadUserData();
	});

	viewmodel.compareGoal = compareGoal;
	viewmodel.addExercise = addExercise;
	viewmodel.setGoalVisibility = setGoalVisibility;
	viewmodel.countVisibleEntries = countVisibleEntries;
	viewmodel.deleteExerciseRecorded = deleteExerciseRecorded;
	viewmodel.roleChanged = roleChanged;
	viewmodel.cancelEditUser = cancelEditUser;
	viewmodel.updateUser = updateUser;
	viewmodel.deleteUser = deleteUser;
	viewmodel.tableRowClicked = tableRowClicked;

	if($rootScope.userProfile == null) {
		$scope.$watch(function() {
			return $rootScope.userProfile;
		}, function() {
			console.log("userProfile.js $rootScope.currentUser: ", $rootScope.userProfile);
			setRootscopeVars();
			// viewmodel.currentUser = $rootScope.userProfile;
			// // ng-if="UserProfile.currentUser != null && UserProfile.currentUser.role=='ADMIN'"
			// viewmodel.isAdmin = viewmodel.currentUser.role == 'ADMIN';
		});	
	} else {
		setRootscopeVars();
	}

	function tableRowClicked(entry, goal) {
		if(!viewmodel.isAdmin) {
			return;
		}

		goal.comparing = !goal.comparing;
		compareGoal(entry, goal);
	}
	
	function setRootscopeVars() {
		if($rootScope.userProfile != undefined && $rootScope.userProfile != null) {
			viewmodel.currentUser = $rootScope.userProfile;
			viewmodel.isAdmin = viewmodel.currentUser.role == 'ADMIN';	
		}
	}

	function loadData() {
		var deferred = $q.defer();

		$q.all([
			ratioProfileService.getRatioProfiles()
			.then(function(response) {
				console.log("UserProfileCtrl.setRatioProfiles: ", response);
				calculateGoalsService.setRatioProfiles(response.data);
			}), 
			exerciseService.getGroupedExercises()
			.then(function(response) {
				console.log("UserProfileCtrl.getGroupedExercises: ", response);
				viewmodel.groupedExercises = calculateGoalsService.analyze(response);
			}, function(error) {
				console.log("UserProfileCtrl.getGroupedExercises error: ", error);
			})
		]).finally(function() {
			deferred.resolve();
		});

		return deferred.promise;
	}

	function loadUserData() {
		userService.getUserData(viewmodel.userId)
		.then(function(response) {
			$log.debug("UserProfileCtrl.loadData.response: ", response);
			calculateGoalsService.analyze(response.data.gradedExercises);
			viewmodel.userData = response.data;
			resetUpdateUserModel();
			viewmodel.selectedUserRole = response.data.userProfileModel.role;
			viewmodel.loadingData = false;
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error;
		});
	}

	function resetAddExerciseModel() {
		viewmodel.addExerciseModel = new Object();
		viewmodel.addExerciseModel.date = new Date();
	}

	function updateUser() {
		viewmodel.updateUserError = false;
		viewmodel.saving = true;

		userService.updateUser(viewmodel.updateUserModel)
		.then(function(response) {
			viewmodel.editUser = false;
			viewmodel.updateUserSuccess = true;
			loadUserData();
		}, function(error) {
			viewmodel.updateUserError = true;
			viewmodel.updateUserErrorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function compareGoal(entry, goal) {
		console.log("compareGoal: ", goal);
		console.log("entry: ", entry);

		var comparing = false;

		$.each(entry.goals, function(i, e) {
			if(e.comparing) {
				comparing = true;
			}
		});
		entry.comparing = comparing;
		calculateGoalsService.analyze(viewmodel.userData.gradedExercises);
	} 

	function setGoalVisibility(goalId, visibility) {
		viewmodel.saving = true;
		viewmodel.updateGoalVisibilityError = false;
		viewmodel.successfullyUpdatedVisibility = false;

		userService.setGoalVisibility(goalId, visibility) 
		.then(function(response) {
			viewmodel.successfullyUpdatedVisibility = true;
		}, function(error) {
			viewmodel.updateGoalVisibilityError = true;
			viewmodel.updateGoalVisibilityErrorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function resetUpdateUserModel() {
		viewmodel.updateUserModel = new Object();
		viewmodel.updateUserModel.userId = viewmodel.userId;
		viewmodel.updateUserModel.firstName = viewmodel.userData.userProfileModel.firstName;
		viewmodel.updateUserModel.lastName = viewmodel.userData.userProfileModel.lastName;
	}

	function cancelEditUser() {
		viewmodel.editUser = false;
		resetUpdateUserModel();
	}

	function deleteUser(userId) {
		if(($rootScope.userProfile.id == viewmodel.userId && 
			!window.confirm("You are deleting your own account.  Are you sure you want to do this?")) || 
			(!window.confirm("Are you sure you want to delete this account? This cannot be undone."))) {
			return;
		} 

		viewmodel.deleteError = false;
		viewmodel.saving = true;

		userService.deleteUser(viewmodel.userId)
		.then(function(response) {
			alert("The user was deleted successfully.");
			$state.go('users');
		}, function(error) {
			viewmodel.deleteError = true;
			viewmodel.deleteErrorMessage = error.data.error;
			viewmodel.saving = false;
			alert("The user was NOT deleted.");
		});
	}

	function roleChanged() {
		if($rootScope.userProfile.id == viewmodel.userId && 
			!window.confirm("Are you sure you want to remove ADMIN priviledges from your account?")) {
			return;
		}

		viewmodel.saving = true;

		userService.setRole(viewmodel.userId, viewmodel.selectedUserRole)
		.then(function(response) {
			viewmodel.setRoleError = false;
			viewmodel.userData.userProfileModel.role = viewmodel.selectedUserRole;
		}, function(error) {
			viewmodel.setRoleError = true;
			viewmodel.setRoleErrorMessage = error.data.error;
			viewmodel.selectedUserRole = viewmodel.userData.userProfileModel.role;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function countVisibleEntries(exercises) {
		if(exercises.length == 0) {
			return 0;
		}

		var length = exercises.length;
		$.each(exercises, function(i, e) {
			if(e.deleted) {
				length--;
			}
		});
		return length;
	}

	function deleteExerciseRecorded(exerciseRecorded) {
		viewmodel.saving = true;
		viewmodel.deleteExerciseError = false;
		console.log("deleteExerciseRecorded: " + viewmodel.userId, exerciseRecorded);
		exerciseService.deleteExerciseRecorded(viewmodel.userId, exerciseRecorded.id)
		.then(function(response) {
			loadUserData();
			// exerciseRecorded.deleted = true;
		}, function(error) {
			viewmodel.deleteExerciseError = true;
			viewmodel.deleteExerciseErrorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function addExercise(form) {
		if(form.$valid) {
			viewmodel.showExerciseAdded = false;
			viewmodel.exerciseAddedError = false;
			viewmodel.saving = true;
			console.log("addExercise: form: ", form);
			console.log("addExercise: viewmodel.addExerciseModel", viewmodel.addExerciseModel);
			exerciseService.addExerciseToUser(viewmodel.userId, viewmodel.addExerciseModel)
			.then(function(response) {	
				viewmodel.showExerciseAdded = true;
				resetAddExerciseModel();
				loadUserData();
			}, function(error) {
				viewmodel.exerciseAddedError = true;
				viewmodel.exerciseAddedErrorMessage = error.data.error;
			}).finally(function() {
				viewmodel.saving = false;
			});
		}
	}
}