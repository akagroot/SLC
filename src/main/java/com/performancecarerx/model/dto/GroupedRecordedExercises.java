/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class GroupedRecordedExercises {
    private ExerciseGroupModel group;
    private List<ExerciseRecordedModel> exercises;
    
    public GroupedRecordedExercises(ExerciseGroupModel group, List<ExerciseRecordedModel> exercises) {
        this.group = group;
        this.exercises = exercises;
    }

    public ExerciseGroupModel getGroup() {
        return group;
    }

    public List<ExerciseRecordedModel> getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        return "GroupedRecordedExercises [" + 
                "group=" + group + 
                ", exercises=" + exercises + 
            ']';
    }
    
    
}
