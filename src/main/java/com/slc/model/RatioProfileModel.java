/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model;

import java.util.List;

/**
 *
 * @author jberroteran
 */
public class RatioProfileModel {
    private Integer id;
    private String name;
    
    private List<RatioProfileValueModel> values;
    
    public RatioProfileModel() {
        
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValues(List<RatioProfileValueModel> values) {
        this.values = values;
    }
    
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<RatioProfileValueModel> getValues() {
        return values;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RatioProfileModel [")
                .append("id=").append(id)
                .append(", name=").append(name)
                .append(", values=").append(values)
                .append("]");
        return builder.toString();
    }
}
