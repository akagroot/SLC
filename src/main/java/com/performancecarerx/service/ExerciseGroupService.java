/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface ExerciseGroupService {
    public List<ExerciseGroupModel> getExerciseGroups();
    public ExerciseGroupModel addExerciseGroup(ExerciseGroupModel model);
    public ExerciseGroupModel updateExerciseGroup(ExerciseGroupModel model);
    public Boolean deleteExerciseGroup(String exerciseGroupKey);
    public Boolean isKeyNameUnique(String keyName);
}
