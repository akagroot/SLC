/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model;

import java.util.Date;

/**
 *
 * @author jberroteran
 */
public class ExerciseGoalModel {
    private Integer id;
    private Integer exerciseId;
    private Integer weight;
    private Integer reps;
    private Integer userId;
    private Date createdOnDttm;
    
    private ExerciseModel exerciseModel;
    
    public ExerciseGoalModel() {
        
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getExerciseId() {
        return exerciseId;
    }
    public Date getCreatedOnDttm() {
        return createdOnDttm;
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
    public ExerciseModel getExerciseModel() {
        return exerciseModel;
    }
    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
    public void setCreatedOnDttm(Date createdOnDttm) {
        this.createdOnDttm = createdOnDttm;
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
    public void setExerciseModel(ExerciseModel model) {
        this.exerciseModel = model;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExerciseGoal [")
                .append("id=").append(id)
                .append(", exerciseId=").append(exerciseId)
                .append(", createdOnDttm=").append(createdOnDttm)
                .append(", weight=").append(weight)
                .append(", reps=").append(reps)
                .append(", userId=").append(userId)
                .append(", exerciseModel=").append(exerciseModel)
                .append("]");
        return builder.toString();
    }
}
