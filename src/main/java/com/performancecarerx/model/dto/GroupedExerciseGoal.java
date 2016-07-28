/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.ExerciseGroupModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class GroupedExerciseGoal {
    private ExerciseGroupModel group;
    private List<ExerciseGoalModel> goals;
    
    public GroupedExerciseGoal(ExerciseGroupModel group, List<ExerciseGoalModel> goals) {
        this.group = group;
        this.goals = goals;
    }
    
    public ExerciseGroupModel getGroup() {
        return group;
    }
    public List<ExerciseGoalModel> getGoals() {
        return goals;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GroupedExerciseGoal [")
                .append("group=").append(group)
                .append(", goals=").append(goals)
                .append("]");
        return builder.toString();
    }
}
