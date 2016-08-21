'use strict';

angular.module('myApp')

.controller('ExercisesCtrl', ExercisesCtrl);

function ExercisesCtrl(exerciseService, ratioProfileService, $q, $rootScope, $state, $scope) {

	var viewmodel = this;
	viewmodel.users = null;
	viewmodel.finishedLoading = false;
	viewmodel.dataLoading = true;
	viewmodel.saving = false;
	viewmodel.successful = false;
	viewmodel.successfulMessage = null;
	viewmodel.error = false;
	viewmodel.errorMessage = null;

	viewmodel.addExerciseGroupModel = null;
	viewmodel.addExerciseModel = null;
	viewmodel.selectedExercises = new Array();

	viewmodel.addExerciseGroup = addExerciseGroup;
	viewmodel.addExercise = addExercise;
	viewmodel.updateExerciseGroup = updateExerciseGroup;
	viewmodel.updateExercise = updateExercise;
	viewmodel.deleteExerciseGroup = deleteExerciseGroup;
	viewmodel.deleteExercise = deleteExercise;
	viewmodel.edit = edit;
	viewmodel.cancelEdit = cancelEdit;
	viewmodel.saveEditGroup = saveEditGroup;
	viewmodel.saveEditExercise = saveEditExercise;

	viewmodel.selectAllExercises = selectAllExercises;
	viewmodel.selectNoExercises = selectNoExercises;
	viewmodel.setSelectedToProfile = setSelectedToProfile;

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

	function forEachExercise(callback) {
		$.each(viewmodel.groupedExercises, function(i, e) {
			$.each(e.exercises, function(j, ex) {
				callback(ex);
			});
		});
	}

	function setSelectedToProfile(form) {
		console.log(form);
		console.log(form.selectedProfile.$modelValue);

		var selectedProfile = form.selectedProfile.$modelValue;

		if(selectedProfile == null || selectedProfile == undefined) {
			viewmodel.error = true;
			viewmodel.errorMessage = "Please select a profile to set all of the selected exercises.";
			return;
		}

		viewmodel.error = false;

		var requests = new Array();

		forEachExercise(function(exercise) {
			if(exercise.isSelected) {
				var model = {};
				model.id = exercise.id;
				model.name = exercise.name_editable;
				model.ratioProfileId = selectedProfile;
				model.exerciseGroupKeyName = exercise.exerciseGroupKeyName;

				requests.push(exerciseService.updateExercise(model));
			}
		});

		if(requests.length > 0) {
			viewmodel.saving = true;

			$q.all(requests)
			.finally(function() {
				loadPage();
				viewmodel.saving = false;
			});
		} else {
			viewmodel.error = true;
			viewmodel.errorMessage = "Please select exercises to set a ratio profile.";
		}
	}

	function selectAllExercises() {
		forEachExercise(function(ex) {
			ex.isSelected = true;
		});
	}

	function selectNoExercises() {
		forEachExercise(function(ex) {
			ex.isSelected = false;
		});
	}

	function loadPage() {
		if($rootScope.userProfile.role != "ADMIN") {
			$state.go('redirectToDashboard');
			return;
		}

		viewmodel.dataLoading = true;
		$q.all([
			ratioProfileService.getRatioProfiles('s')
			.then(function(response) {
				viewmodel.ratioProfiles = response.data;
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.data.error;
			}), 

			exerciseService.getGroupedExercises(true)
			.then(function(response) {
				viewmodel.groupedExercises = cleanGroupedDataResponse(response);
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.data.error;
			})
		]).finally(function() {
			viewmodel.dataLoading = false;
			viewmodel.finishedLoading = true;
		});
	}

	function edit(object, id) {
		console.log("edit object: ", object);
		console.log('edit object id: ', id);
		object.editing = true;
		setTimeout(function(){$('#'+id).select()}, 0);
	}

	function cancelEdit(object, originalValue, editValue) {
		object.editing = false;
	}

	function saveEditGroup(group) {
		console.log("save group", group);

		if(group.displayName_editable.length == 0) {
			viewmodel.error = true;
			viewmodel.errorMessage = "There must be a group name";
			alert("There must be a group name!");
			return;
		}

		viewmodel.error = false;
		viewmodel.saving = true;

		var model = {};
		model.keyName = group.keyName;
		model.displayName = group.displayName_editable;

		exerciseService.updateExerciseGroup(model)
		.then(function(response) {
			group.displayName = angular.copy(group.displayName_editable);
			group.editing = false;
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
			alert(error.data.error);
		}).finally(function(){
			viewmodel.saving = false;
		});
	}

	function saveEditExercise(exercise, reloadPage) {
		if(reloadPage == null || reloadPage == undefined) {
			reloadPage = true;
		}

		console.log("save exercise", exercise);

		if(exercise.name_editable.length == 0) {
			viewmodel.error = true;
			viewmodel.errorMessage = "There must be an exercise name!";
			alert("There must be an exercise name!");
			return;
		}

		viewmodel.error = false;
		viewmodel.saving = true;

		var model = {};
		model.id = exercise.id;
		model.name = exercise.name_editable;
		model.ratioProfileId = exercise.ratioProfileId;
		model.exerciseGroupKeyName = exercise.exerciseGroupKeyName;

		exerciseService.updateExercise(model)
		.then(function(response) {
			exercise.editing = false;
			if(reloadPage) {
				loadPage();
			}
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
			alert(error.data.error);
		}).finally(function() {
			viewmodel.saving = false;
		});
	}

	function cleanGroupedDataResponse(data) {
		$.each(data, function(i, e) {
			e.group.editing = false;
			e.group.displayName_editable = angular.copy(e.group.displayName);
			e.group.htmlId = 'group' + e.group.keyName;

			$.each(e.exercises, function(i, ex) {
				ex.htmlId = 'exercise' + ex.id;
				ex.name_editable = angular.copy(ex.name);
				ex.editing = false;
				ex.isSelected = false;
			});
		});
		console.log("Data: ", data);
		return data;
	}

	function addExerciseGroup() {
		viewmodel.saving = true;
		viewmodel.error = false;
		viewmodel.successful = false;
		exerciseService.addExerciseGroup(viewmodel.addExerciseGroupModel)
		.then(function(response) {
			viewmodel.addExerciseGroupModel = null;
			viewmodel.successful = true;
			viewmodel.successfulMessage = "The exercise group was added!";
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}
	function addExercise() {
		viewmodel.saving = true;
		viewmodel.error = false;
		viewmodel.successful = false;
		exerciseService.addExercise(viewmodel.addExerciseModel)
		.then(function(response) {
			viewmodel.addExerciseModel = null;
			viewmodel.successful = true;
			viewmodel.successfulMessage = "The exercise was added!";
			loadPage();
		}, function(error) {
			viewmodel.error = true;
			viewmodel.errorMessage = error.data.error;
		}).finally(function() {
			viewmodel.saving = false;
		});
	}
	function updateExerciseGroup() {

	}
	function updateExercise() {

	}
	function deleteExerciseGroup(exerciseGroupId) {
		if(window.confirm("Are you sure you want to delete this group?")) {
			viewmodel.saving = true;
			viewmodel.error = false;
			viewmodel.successful = false;
			exerciseService.deleteExerciseGroup(exerciseGroupId)
			.then(function(response) {
				viewmodel.successful = true;
				viewmodel.successfulMessage = "The exercise group was removed!";
				loadPage();
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.data.error;
			}).finally(function() {
				viewmodel.saving = false;
			});
		}
	}
	function deleteExercise(exerciseId) {
		if(window.confirm("Are you sure you want to delete this exercise?")) {
			viewmodel.saving = true;
			viewmodel.error = false;
			viewmodel.successful = false;
			exerciseService.deleteExercise(exerciseId)
			.then(function(response) {
				viewmodel.successful = true;
				viewmodel.successfulMessage = "The exercise was removed!";
				loadPage();
			}, function(error) {
				viewmodel.error = true;
				viewmodel.errorMessage = error.data.error;
			}).finally(function() {
				viewmodel.saving = false;
			});
		}
	}
}