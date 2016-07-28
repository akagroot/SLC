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
public class AddUserModel {
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    
    public AddUserModel(String email, String firstName, String lastName, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    public AddUserModel() {
        
    }
    
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getRole() {
        return role;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AddUserModel [")
                .append("email=").append(email)
                .append(", firstName=").append(firstName) 
                .append(", lastName=").append(lastName)
                .append(", role=").append(role)
                .append("]");
        return builder.toString();
    }
}
