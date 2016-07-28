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
    
    private ExerciseGroupModel exerciseGroupModel;
    
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
    public ExerciseGroupModel getExerciseGroupModel() {
        return exerciseGroupModel;
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
    public void setExerciseGroupModel(ExerciseGroupModel model) {
        this.exerciseGroupModel = model;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExerciseModel [")
                .append("id=").append(id)
                .append(", name=").append(name)
                .append(", exerciseGroupKeyName=").append(exerciseGroupKeyName)
                .append(", exerciseGroupModel=").append(exerciseGroupModel)
                .append("]");
        return builder.toString();
    }
}
