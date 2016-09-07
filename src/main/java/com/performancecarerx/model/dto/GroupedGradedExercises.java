/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class GroupedGradedExercises {
    private ExerciseGroupModel group;
    private List<ExerciseGoalModel> goals;
    private float averageGrade;
    
    public GroupedGradedExercises(ExerciseGroupModel group, List<ExerciseGoalModel> goals, float averageGrade) {
        this.group = group;
        this.goals = goals;
        this.averageGrade = averageGrade;
    }
    
    public ExerciseGroupModel getGroup() {
        return group;
    }
    
    public List<ExerciseGoalModel> getGoals() {
        return goals;
    }
    
    public float getAverageGrade() {
        return averageGrade;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GroupedExercises [")
                .append("group=").append(group)
                .append(", goals=").append(goals)
                .append(", averageGrade=").append(averageGrade)
                .append("]");
        return builder.toString();
    }
}
