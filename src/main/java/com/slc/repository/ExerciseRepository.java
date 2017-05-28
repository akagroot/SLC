/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository;

import com.slc.model.ExerciseModel;
import com.slc.model.ExerciseRecordedModel;
import com.slc.model.dto.AddExerciseModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface ExerciseRepository {
    public ExerciseModel addExercise(ExerciseModel model);
    public Boolean deleteExercise(Integer id);
    public ExerciseModel updateExercise(ExerciseModel model);
    
    public ExerciseModel getExerciseById(Integer exerciseId);
    public List<ExerciseModel> getExercises();
    public List<ExerciseModel> getGroupedExercises();
    public Integer countExercisesForGroupKey(String keyName);
    
    public List<ExerciseRecordedModel> getExercisesForUser(String email);
    public ExerciseRecordedModel getExerciseRecorded(Integer id);
    
    public Boolean exerciseRecordedBelongsToUser(Integer userId, Integer exerciseRecordedId);
    public Integer addExerciseToUser(AddExerciseModel exercise);
    public Boolean deleteExerciseRecorded(Integer exerciseId);
}
