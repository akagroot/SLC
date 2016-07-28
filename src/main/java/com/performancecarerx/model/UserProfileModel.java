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
public class UserProfileModel {
    private Integer id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    
    public UserProfileModel(String email) {
        this.email = email;
    }
    
    public UserProfileModel() {
        
    }
    
    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserProfileModel [")
                .append("id=").append(id)
                .append(", email=").append(email)
                .append(", role=").append(role)
                .append(", firstName=").append(firstName) 
                .append(", lastName=").append(lastName)
                .append("]");
        return builder.toString();
    }
}
