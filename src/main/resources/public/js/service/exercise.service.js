'use strict';

angular.module('myApp')
.service('exerciseService', exerciseService);

function exerciseService($q, $log, commonService, $rootScope) {

	var service = {};

  service.addExerciseToUser = addExerciseToUser;
  service.getExercises = getExercises;
  service.getGroupedExercises = getGroupedExercises;
  service.getWeights = getWeights;
  service.getReps = getReps;
  service.deleteExerciseRecorded = deleteExerciseRecorded;

  service.addExercise = addExercise;
  service.addExerciseGroup = addExerciseGroup;
  service.deleteExercise = deleteExercise;
  service.deleteExerciseGroup = deleteExerciseGroup;
  service.updateExercise = updateExercise;
  service.updateExerciseGroup = updateExerciseGroup;

  var addExerciseRoute = "/addExercise";
  var addExerciseGroupRoute = "/addExerciseGroup";
  var deleteExerciseRoute = "/deleteExercise/{id}";
  var deleteExerciseGroupRoute = "/deleteExerciseGroup/{keyname}";
  var updateExerciseRoute = "/updateExercise";
  var updateExerciseGroupRoute = "/updateExerciseGroup"

  var getGroupedExercisesRoute = "/groupedExercises";
  var getExercisesRoute = "/exercises";
  var addExerciseToUserRoute = "/addExerciseToUser";
  var deleteExerciseRecordedRoute = "/deleteExerciseRecorded/{userId}/{exerciseId}";

  function updateExercise(model) {
    return commonService.post(updateExerciseRoute, model);
  }

  function updateExerciseGroup(model) {
    return commonService.post(updateExerciseGroupRoute, model);
  }

  function getExercises(){
    return commonService.get(getExercisesRoute);
  }

  function getGroupedExercises(forceReload) {
    if(forceReload == undefined) {
      forceReload = false;
    }

    if(forceReload || $rootScope.groupedExercises == null || $rootScope.groupedExercises == undefined) {
        return commonService.get(getGroupedExercisesRoute).then(function(response) {
          $rootScope.groupedExercises = response.data;
          return response.data;
        });
    } 

    var deferred = $q.defer();
    deferred.resolve($rootScope.groupedExercises);
    return deferred.promise;
  }

  function addExercise(model) {
    console.log("exerciseService.addExercise: ", model);
    return commonService.post(addExerciseRoute, model);
  }

  function addExerciseGroup(model) {
    console.log("exerciseService.addExerciseGroup: ", model);
    return commonService.post(addExerciseGroupRoute, model);
  }

  function deleteExercise(id) {
    return commonService.get(deleteExerciseRoute.replace("{id}", id));
  }

  function deleteExerciseGroup(keyname) {
    return commonService.get(deleteExerciseGroupRoute.replace("{keyname}", keyname));
  }

  function addExerciseToUser(userId, form) {
    console.log("exerciseService.addExerciseToUser: form: ", form);
    if(userId == undefined || userId == null || userId.length == 0) {
      userId = $rootScope.userProfile.id;
    }

    var data = {};
    data.userId = userId;
    data.weight = form.weight;
    data.reps = form.reps;
    data.exerciseId = form.exercise;
    data.date = form.date;
    data.note = form.note;
    console.log("exerciseService.addExercise: data: ", data);

    return commonService.post(addExerciseToUserRoute, data);
  }

  function deleteExerciseRecorded(userId, exerciseRecordedId) {
    if(userId == undefined || userId == null || userId.length == 0) {
      userId = $rootScope.userProfile.id;
    }

    return commonService.get(
        deleteExerciseRecordedRoute.replace('{userId}', userId)
          .replace('{exerciseId}', exerciseRecordedId)
      );
  }

  function getWeights() {
    var weights = [1, 2, 3, 5, 8, 10, 12];
    for(var i = 15; i <= 700; i+=5) {
      weights.push(i);
    }
    return weights;
  }

  function getReps() {
    var reps = new Array();
    for(var i = 1; i <= 20; i++) {
      reps.push(i);
    }
    return reps;
  }
  
  return service;
}