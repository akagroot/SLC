/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model.dto;

import com.slc.model.ExerciseGroupModel;
import com.slc.model.ExerciseModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class GroupedExercises {
    private ExerciseGroupModel group;
    private List<ExerciseModel> exercises;
    
    public GroupedExercises(ExerciseGroupModel group, List<ExerciseModel> exercises) {
        this.group = group;
        this.exercises = exercises;
    }
    
    public ExerciseGroupModel getGroup() {
        return group;
    }
    
    public List<ExerciseModel> getExercises() {
        return exercises;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GroupedExercises [")
                .append("group=").append(group)
                .append(", exercises=").append(exercises)
                .append("]");
        return builder.toString();
    }
}
