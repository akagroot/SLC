/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model;

import com.performancecarerx.model.dto.AddExerciseModel;
import java.util.Date;

/**
 *
 * @author jberroteran
 */
public class ExerciseRecordedModel {
    private Integer id;
    private Integer exerciseId;
    private Date recordedDttm;
    private Integer weight;
    private Integer reps;
    private Integer userId;
    private String note;
    
    private ExerciseModel exerciseModel;
    private UserProfileModel userProfileModel;
    
    public ExerciseRecordedModel(AddExerciseModel model) {
        this.recordedDttm = model.getDate();
        this.weight = model.getWeight();
        this.reps = model.getReps();
        this.exerciseId = model.getExerciseId();
        this.note = model.getNote();
    }
    
    public ExerciseRecordedModel() {
        
    }
    
    public Integer getId() {
        return id;
    }
    public Integer getExerciseId() {
        return exerciseId;
    }
    public Date getRecordedDttm() {
        return recordedDttm;
    }
    public Integer getWeight() {
        return weight;
    }
    public Integer getReps() {
        return reps;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getNote() {
        return note;
    }
    public ExerciseModel getExerciseModel() {
        return exerciseModel;
    }
    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
    public void setRecordedDttm(Date recordedDttm) {
        this.recordedDttm = recordedDttm;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setExerciseModel(ExerciseModel model) {
        this.exerciseModel = model;
    }
    public void setUserProfileModel(UserProfileModel model) {
        this.userProfileModel = model;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExerciseRecordedModel [")
                .append("id=").append(id)
                .append(", exerciseId=").append(exerciseId)
                .append(", recordedDttm=").append(recordedDttm)
                .append(", weight=").append(weight)
                .append(", reps=").append(reps)
                .append(", userId=").append(userId)
                .append(", note=").append(note)
                .append(", exerciseModel=").append(exerciseModel)
                .append(", userProfileModel=").append(userProfileModel)
                .append("]");
        return builder.toString();
    }
}
