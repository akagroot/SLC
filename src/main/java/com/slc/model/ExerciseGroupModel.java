/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model;

/**
 *
 * @author jberroteran
 */
public class ExerciseGroupModel {
    private String keyName;
    private String displayName;
    
    public ExerciseGroupModel() {
        
    }
    
    public String getKeyName() {
        return keyName;
    }
    public String getDisplayName() {
        return displayName;
    }
    
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExerciseGroupModel [")
                .append("keyName=").append(keyName)
                .append(", displayName=").append(displayName) 
                .append("]");
        return builder.toString();
    }
}
