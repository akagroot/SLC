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
public class UpdateUserInfoModel {
    private Integer userId;
    private String firstName;
    private String lastName; 
    private Integer coachId;
    
    public UpdateUserInfoModel() {
        
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public Integer getCoachId() {
        return coachId;
    }
    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UpdateUserInfoModel [")
                .append("userId=").append(userId)
                .append(", firstName=").append(firstName)
                .append(", lastName=").append(lastName)
                .append(", coachId=").append(coachId)
                .append("]");
        return builder.toString();
    }
}
