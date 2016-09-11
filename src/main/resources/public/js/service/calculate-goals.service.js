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
  var perfectAccountValues = null;

  service.analyze = analyze;
  service.analyze2 = analyze2;
  service.getRatioProfiles = getRatioProfiles;
  service.setRatioProfiles = setRatioProfiles;
  service.setPerfectAccount = setPerfectAccount;
  service.getEstimated1RM = getEstimated1RM;

  function setPerfectAccount(account) {
    perfectAccountValues = new Object();

    $.each(account.exercisesByGroup, function(i, l) {
      $.each(l.exercises, function(i, e) {
        perfectAccountValues[e.exerciseId] = angular.copy(e);
      });
    });

    $log.debug("calculateGoalsService.setPerfectAccount: ", perfectAccountValues);
  }

  function setRatioProfiles(profiles) {
    ratioProfiles = new Object();

    $.each(profiles, function(i, e) {
      var mappedValues = new Object();
      $.each(e.values, function(i, v) {
        mappedValues[v.reps] = v.multiplier;
      });

      ratioProfiles[e.id] = mappedValues;
    });

    $log.debug("calculateGoalsService.ratioProfiles: ", ratioProfiles);
  }

  function getRatioProfiles() {
    return ratioProfiles;
  }

  function analyze2(userProfile) {
    var usersStandard = angular.copy(userProfile.selectedStandard);
    $log.debug("usersStandard: ", usersStandard);

    var perfectStandard = perfectAccountValues[usersStandard.exerciseId];
    $log.debug("perfectStandard: ", perfectStandard);

    var multipliers = ratioProfiles[perfectStandard.exerciseModel.ratioProfileId];
    var perfectStandard1RM = getEstimated1RM(perfectStandard.reps, perfectStandard.weight, multipliers);
    var usersStandard1RM = getEstimated1RM(usersStandard.reps, usersStandard.weight, multipliers);
    
    var forceCompare = true;

    $.each(userProfile.exercisesByGroup, function(i, g) {
      $.each(g.exercises, function(i, e) {
        if(e.comparing) {
          forceCompare = false;
        }
      });
    });

    $.each(userProfile.exercisesByGroup, function(i, g) {
      var average = 0;
      var exercisesCalculated = 0;
      
      g.averageGrade = 0;

      $.each(g.exercises, function(i, e) {
        if(forceCompare || e.comparing) {
          multipliers = ratioProfiles[e.exerciseModel.ratioProfileId];

          var perfectExercise = perfectAccountValues[e.exerciseId];
          var nextPerfectStandard1RM = getEstimated1RM(perfectExercise.reps, perfectExercise.weight, multipliers);
          var nextPerfectRatio = perfectStandard1RM/nextPerfectStandard1RM;

          $log.info(perfectStandard.exerciseModel.name + " :: " + e.exerciseModel.name);
          $log.debug("Ideal: " + perfectStandard1RM + "/" + nextPerfectStandard1RM + " = ", nextPerfectRatio);

          var nextUserEstimated1RM = getEstimated1RM(e.reps, e.weight, multipliers);
          var nextUserRatio = usersStandard1RM/nextUserEstimated1RM;

          $log.debug("Users: " + usersStandard1RM + "/" + nextUserEstimated1RM + " = ", nextUserRatio);

          // var standardDeviation = calcStandardDeviation([nextPerfectRatio, nextUserRatio]);
          // $log.debug("standardDeviation: ", standardDeviation);
          // var grade = null;
          // var factor = null;
          // if(nextPerfectRatio >= 1) {
          //   factor = (nextUserRatio <= nextPerfectRatio ? 1:-1);
          // } else {
          //   factor = (nextUserRatio <= nextPerfectRatio ? -1:1);
          // }
          // $log.debug("factor: ", factor);
          // grade = 1 + (factor*standardDeviation);
          var grade = nextPerfectRatio/nextUserRatio;

          e.grade = grade;
          e.goal = nextUserEstimated1RM/grade;
          e.estimated1RM = nextUserEstimated1RM;

          if(e.estimated1RM != undefined && e.goal != undefined) {
            $log.debug("add grade to avg");
            average += e.grade; 
            exercisesCalculated++;
          } else {
            e.grade = NaN;
          }
        }
      });

      if(exercisesCalculated == 0) {
        g.average = 0;
      } else {
        g.averageGrade = average/exercisesCalculated; 
      }
      $log.debug("averageGrade: ", g.averageGrade);
    });
  }

  function calcStandardDeviation(listOfNumbers) {
    if(listOfNumbers == null) {
      return null;
    }

    var average = 0; 
    $.each(listOfNumbers, function(i, e) {
      average += e;
    });
    average = average/listOfNumbers.length;

    var numerator = 0;
    $.each(listOfNumbers, function(i, e) {
      var nextNumber = e - average;
      nextNumber = nextNumber * nextNumber;
      numerator += nextNumber;
    });

    numerator = numerator/(listOfNumbers.length - 1);

    return Math.sqrt(numerator);
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
      
      entry.averageGrade = 0;

      $.each(entry.goals, function(i, e) {
        if(e.comparing) {
          forceCompare = false;
        }
      });

      $.each(entry.goals, function(i, e) {
        $log.info("goal entry: ", e);
        if(forceCompare || e.comparing) {
          var useMultipliers = null;

          if(e.exerciseModel.ratioProfileId != null) {
            useMultipliers = ratioProfiles[e.exerciseModel.ratioProfileId];
          }

          $log.debug("useMultipliers: ", useMultipliers);
          var rm = e.recordedModel;
          e.estimated1RM = getEstimated1RM(rm.reps, rm.weight, useMultipliers);
          e.goal = getEstimated1RM(e.reps, e.weight, useMultipliers);
          if(e.estimated1RM != undefined && e.goal != undefined) {
            $log.debug("add grade to avg");
            e.grade = e.estimated1RM/e.goal; 
            average += e.grade; 
            exercisesCalculated++;
          } else {
            e.grade = NaN;
          }
        }
      });

      if(exercisesCalculated == 0) {
        entry.average = 0;
      } else {
        entry.averageGrade = average/exercisesCalculated; 
      }
      $log.debug("averageGrade: ", entry.averageGrade);
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

    var m = multipliers[reps];

    if(m == undefined) {
      return undefined;
    }

    var oneRm = (weight/m);
    // $log.debug("multiplier: ", m);
    // $log.debug("weight/m = ", oneRm);
    return oneRm;
  }
  
  return service;
}