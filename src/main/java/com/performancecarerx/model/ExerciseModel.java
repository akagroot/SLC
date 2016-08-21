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
public class ExerciseModel {
    private Integer id;
    private String name;
    private String exerciseGroupKeyName;
    private Integer ratioProfileId;
    
    private ExerciseGroupModel exerciseGroupModel;
    private RatioProfileModel ratioProfileModel;
    
    public ExerciseModel() {
        
    }
    
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getExerciseGroupKeyName() {
        return exerciseGroupKeyName;
    }
    public Integer getRatioProfileId() {
        return ratioProfileId;
    }
    public ExerciseGroupModel getExerciseGroupModel() {
        return exerciseGroupModel;
    }
    public RatioProfileModel getRatioProfileModel() {
        return ratioProfileModel;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setExerciseGroupKeyName(String keyName) {
        this.exerciseGroupKeyName = keyName;
    }
    public void setRatioProfileId(Integer ratioProfileId) {
        this.ratioProfileId = ratioProfileId;
    }
    public void setExerciseGroupModel(ExerciseGroupModel model) {
        this.exerciseGroupModel = model;
    }
    public void setRatioProfileModel(RatioProfileModel model) {
        this.ratioProfileModel = model;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExerciseModel [")
                .append("id=").append(id)
                .append(", name=").append(name)
                .append(", exerciseGroupKeyName=").append(exerciseGroupKeyName)
                .append(", ratioProfileId=").append(ratioProfileId)
                .append(", exerciseGroupModel=").append(exerciseGroupModel)
                .append(", ratioProfileModel=").append(ratioProfileModel)
                .append("]");
        return builder.toString();
    }
}
