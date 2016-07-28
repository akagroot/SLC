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
public class UpdateUserRoleModel {
    private Integer userId;
    private String role;
    public UpdateUserRoleModel() {
        
    }
    public Integer getUserId() {
        return userId;
    }
    public String getRole() {
        return role;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UpdateUserRoleModel [")
                .append("userId=").append(userId)
                .append(", role=").append(role)
                .append("]");
        return builder.toString();
    }
}
