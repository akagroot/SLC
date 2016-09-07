/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

/**
 *
 * @author jberroteran
 */
public class UpdateGoalVisibilityModel {
    private Integer exerciseGoalId;
    private Boolean visibility;
    
    public UpdateGoalVisibilityModel() {
        
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public Integer getExerciseGoalId() {
        return exerciseGoalId;
    }

    public void setExerciseGoalId(Integer exerciseGoalId) {
        this.exerciseGoalId = exerciseGoalId;
    }

    
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "UpdateGoalVisibilityModel [" + "exerciseGoalId=" + exerciseGoalId + ", visibility=" + visibility + ']';
    }
    
    
}
