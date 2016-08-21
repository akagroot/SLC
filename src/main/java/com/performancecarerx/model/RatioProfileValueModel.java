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
public class RatioProfileValueModel {
    private Integer id;
    private Integer ratioProfileId;
    private Integer reps;
    private Double multiplier;
    
    public RatioProfileValueModel() {
        
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public void setRatioProfileId(Integer id) {
        this.ratioProfileId = id;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }
    public Integer getId() {
        return id;
    }
    public Integer getRatioProfileId() {
        return ratioProfileId;
    }
    public Integer getReps() {
        return reps;
    }
    public Double getMultiplier() {
        return multiplier;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RatioProfileValueModel [")
                .append("id=").append(id) 
                .append(", ratioProfileId=").append(ratioProfileId)
                .append(", reps=").append(reps)
                .append(", multiplier=").append(multiplier)
                .append("]");
        return builder.toString();
    }
}
