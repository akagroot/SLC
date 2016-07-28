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
public class GroupedGradedExercises {
    private ExerciseGroupModel group;
    private List<ExerciseRecordedModel> exercises;
    private float averageGrade;
    
    public GroupedGradedExercises(ExerciseGroupModel group, List<ExerciseRecordedModel> exercises, float averageGrade) {
        this.group = group;
        this.exercises = exercises;
        this.averageGrade = averageGrade;
    }
    
    public ExerciseGroupModel getGroup() {
        return group;
    }
    
    public List<ExerciseRecordedModel> getExercises() {
        return exercises;
    }
    
    public float getAverageGrade() {
        return averageGrade;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GroupedExercises [")
                .append("group=").append(group)
                .append(", exercises=").append(exercises)
                .append(", averageGrade=").append(averageGrade)
                .append("]");
        return builder.toString();
    }
}
