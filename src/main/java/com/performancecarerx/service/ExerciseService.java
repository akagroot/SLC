/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.dto.AddExerciseModel;
import com.performancecarerx.model.dto.GroupedExercises;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface ExerciseService {
    public List<GroupedExercises> getGroupedExercises();
    public List<ExerciseModel> getExercises();
    public ExerciseModel addExercise(ExerciseModel model);
    public ExerciseModel updateExercise(ExerciseModel model);
    public ExerciseModel getExercise(Integer exerciseId);
    public Boolean deleteExercise(Integer exerciseId);
    public List<ExerciseGroupModel> getExerciseGroups();
    public List<ExerciseRecordedModel> getExercisesForUser(String email);
    public ExerciseRecordedModel getExerciseRecorded(Integer id);
    public Boolean doesExerciseRecordedBelongToUser(Integer userId, Integer exerciseRecordedId);
    public ExerciseRecordedModel addExerciseToUser(AddExerciseModel exercise);
    public Boolean deleteExerciseRecorded(Integer exerciseRecordedId);
}
