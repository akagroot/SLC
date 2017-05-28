/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model.dto;

import com.slc.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class GoalDataResponse {
    private List<GroupedExerciseGoal> groupedGoals;
    private UserProfileModel user;
    public GoalDataResponse(UserProfileModel user, List<GroupedExerciseGoal> groupedGoals) {
        this.user = user;
        this.groupedGoals = groupedGoals;
    }
    public List<GroupedExerciseGoal> getGroupedGoals() {
        return groupedGoals;
    }
    public UserProfileModel getUser() {
        return user;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GoalDataResponse [")
                .append("user=").append(user)
                .append(", groupedGoals=").append(groupedGoals)
                .append("]");
        return builder.toString();
    }
}
