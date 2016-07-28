/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import com.performancecarerx.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class UserDataResponse {
    private UserProfileModel userProfileModel;
    private List<ExercisesByDate> exercisesByDate;
    private List<GroupedGradedExercises> gradedExercises;
    
    public UserDataResponse(UserProfileModel userProfileModel, List<ExercisesByDate> exercisesByDate, List<GroupedGradedExercises> gradedExercises) {
        this.userProfileModel = userProfileModel;
        this.exercisesByDate = exercisesByDate;
        this.gradedExercises = gradedExercises;
    }
    
    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }
    public List<ExercisesByDate> getExercisesByDate() {
        return exercisesByDate;
    }
    public List<GroupedGradedExercises> getGradedExercises() {
        return gradedExercises;
    }
    
    @Override
    public String toString() { 
        StringBuilder builder = new StringBuilder();
        builder.append("UserDataResponse [")
                .append("userProfileModel=").append(userProfileModel) 
                .append(", exercisesByDate=").append(exercisesByDate)
                .append(", gradedExercises=").append(gradedExercises)
                .append("]");
        return builder.toString();
    }
}
