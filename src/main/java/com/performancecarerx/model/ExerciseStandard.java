/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model;

/**
 *
 * @author jberroteran
 */
public class ExerciseStandard {
    private int userId;
    private int exerciseId;
    private int reps;
    private int weight;
    
    private UserProfileModel user;
    private ExerciseModel exerciseModel;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setUser(UserProfileModel user) {
        this.user = user;
    }

    public void setExerciseModel(ExerciseModel exerciseModel) {
        this.exerciseModel = exerciseModel;
    }

    public int getUserId() {
        return userId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public UserProfileModel getUser() {
        return user;
    }

    public ExerciseModel getExerciseModel() {
        return exerciseModel;
    }

    @Override
    public String toString() {
        return "ExerciseStandard [" + 
                "userId=" + userId + 
                ", exerciseId=" + exerciseId + 
                ", reps=" + reps + 
                ", weight=" + weight + 
                ", user=" + user + 
                ", exerciseModel=" + exerciseModel + 
            ']';
    }
    
    
}
