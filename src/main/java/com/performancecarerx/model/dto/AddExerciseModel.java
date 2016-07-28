/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import java.util.Date;

/**
 *
 * @author jberroteran
 */
public class AddExerciseModel {
    private Integer userId, weight, reps, exerciseId;
    private Date date;
    private String note;
    
    public AddExerciseModel(Integer userId, Integer weight, Integer reps, Integer exerciseId, Date date, String note) {
        this.userId = userId;
        this.weight = weight;
        this.reps = reps;
        this.exerciseId = exerciseId;
        this.date = date;
        this.note = note;
    }
    public AddExerciseModel() {
        
    }
    public Integer getUserId() {
        return userId;
    }
    public Integer getWeight() {
        return weight;
    }
    public Integer getReps() {
        return reps;
    }
    public Integer getExerciseId() {
        return exerciseId;
    }
    public Date getDate() {
        return date;
    }
    public String getNote() {
        return note;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setNode(String note) {
        this.note = note;
    }
    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AddExerciseModel [") 
                .append("userId=").append(userId)
                .append(", weight=").append(weight)
                .append(", reps=").append(reps)
                .append(", date=").append(date)
                .append(", exerciseId=").append(exerciseId) 
                .append(", note=").append(note)
                .append("]");
        return builder.toString();
    }
}
