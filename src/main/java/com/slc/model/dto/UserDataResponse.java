/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model.dto;

import com.slc.model.ExerciseRecordedModel;
import com.slc.model.ExerciseStandard;
import com.slc.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class UserDataResponse {
    private UserProfileModel userProfileModel;
    private List<ExercisesByDate> exercisesByDate;
    private List<GroupedRecordedExercises> exercisesByGroup;
    private ExerciseRecordedModel standard;
    
    public UserDataResponse(UserProfileModel userProfileModel, List<ExercisesByDate> exercisesByDate, List<GroupedRecordedExercises> exercisesByGroup, ExerciseRecordedModel standard) {
        this.userProfileModel = userProfileModel;
        this.exercisesByDate = exercisesByDate;
        this.exercisesByGroup = exercisesByGroup;
        this.standard = standard;
    }
    
    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }
    public List<ExercisesByDate> getExercisesByDate() {
        return exercisesByDate;
    }
    public List<GroupedRecordedExercises> getExercisesByGroup() {
        return exercisesByGroup;
    }
    public ExerciseRecordedModel getStandard() {
        return standard;
    }
    
    @Override
    public String toString() { 
        StringBuilder builder = new StringBuilder();
        builder.append("UserDataResponse [")
                .append("userProfileModel=").append(userProfileModel) 
                .append(", exercisesByDate=").append(exercisesByDate)
                .append(", exercisesByGroup=").append(exercisesByGroup)
                .append(", standard=").append(standard)
                .append("]");
        return builder.toString();
    }
}
