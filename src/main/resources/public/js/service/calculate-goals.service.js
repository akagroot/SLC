'use strict';

angular.module('myApp')
.service('calculateGoalsService', calculateGoalsService);

function calculateGoalsService($q, $log, ratioProfileService) {

	var service = {};

  var defaultMultipliers = [
    1.0, 0.94, 0.91, 0.88, 0.858, 0.838, 0.808, 
    0.788, 0.768, 0.748, 0.725, 0.704, 0.689, 0.676, 
    0.662, 0.649, 0.637, 0.628, 0.617, 0.606
  ];

  var ratioProfiles = null;

  service.analyze = analyze;
  service.setRatioProfiles = setRatioProfiles;

  function setRatioProfiles(profiles) {
    ratioProfiles = new Object();

    $.each(profiles, function(i, e) {
      ratioProfiles[e.id] = e.values;
    });

    $log.debug("calculateGoalsService.ratioProfiles: ", ratioProfiles);
  }

  function analyze(groupedExercises) {
    // $log.debug("groupedExercises: ", groupedExercises);
    // $log.debug("checkForComparing: ", checkForComparing);
    // if(checkForComparing == undefined) {
    //   checkForComparing = false;
    // }

    $.each(groupedExercises, function(i, entry) {
      var average = 0;
      var exercisesCalculated = 0;
      var forceCompare = true;

      $.each(entry.goals, function(i, e) {
        if(e.comparing) {
          forceCompare = false;
        }
      });

      $.each(entry.goals, function(i, e) {
        // $log.debug("goal entry: ", e);
        if(forceCompare || e.comparing) {
          var useMultipliers = null;

          if(e.exerciseModel.ratioProfileId != null) {
            useMultipliers = ratioProfiles[e.exerciseModel.ratioProfileId];
          }

          // $log.info("useMultipliers: ", useMultipliers);
          var rm = e.recordedModel;
          e.estimated1RM = getEstimated1RM(rm.reps, rm.weight, useMultipliers);
          e.goal = getEstimated1RM(e.reps, e.weight, useMultipliers);
          e.grade = e.estimated1RM/e.goal;
          average += e.grade;
          exercisesCalculated++;
        }
      });

      entry.averageGrade = average/exercisesCalculated;
    });

    $log.info("Finished calculateGoalsService.analyze");
    $log.debug("groupedExercises: ", groupedExercises);

    return groupedExercises;
  }

  function getEstimated1RM(reps, weight, multipliers) {
    // $log.debug("getEstimated1RM: " + reps + ", " + weight);

    if(multipliers == null || multipliers == undefined) {
      multipliers = defaultMultipliers;
    }

    var m = multipliers[reps-1].multiplier;
    var oneRm = (weight/m);
    // $log.debug("multiplier: ", m);
    // $log.debug("weight/m = ", oneRm);
    return oneRm;
  }
  
  return service;
}