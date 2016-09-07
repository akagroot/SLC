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
    private Boolean visibility = true;
    private Date createdOnDttm;
    
    private ExerciseModel exerciseModel;
    private ExerciseRecordedModel recordedModel;
    
    private int monthsAgo;
    private int goal;
    private int estimated1RM;
    private float grade;
    
    public ExerciseGoalModel() {
        
    }

    public ExerciseRecordedModel getRecordedModel() {
        return recordedModel;
    }

    public void setRecordedModel(ExerciseRecordedModel recordedModel) {
        this.recordedModel = recordedModel;
    }
    
    
    public int getMonthsAgo() {
        return monthsAgo;
    }
    public int getGoal() {
        return goal;
    }
    public int getEstimated1RM() {
        return estimated1RM;
    }
    public float getGrade() {
        return grade;
    }
    public void setMonthsAgo(int monthsAgo) {
        this.monthsAgo = monthsAgo;
    }
    public void setGoal(int goal) {
        this.goal = goal;
    }
    public void setEstimated1RM(int estimated1RM) {
        this.estimated1RM = estimated1RM;
    }
    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
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
                .append(", recordedModel=").append(recordedModel)
                .append(", visibility=").append(visibility)
                .append(", monthsAgo=").append(monthsAgo)
                .append(", goal=").append(goal)
                .append(", estimated1RM=").append(estimated1RM)
                .append(", grade=").append(grade)
                .append("]");
        return builder.toString();
    }
}
