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
public class CreateAccountModel {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    
    public CreateAccountModel(String firstName, String lastName, String username, String email, String password) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.username = username;
    	this.email = email;
    	this.password = password;
    }

    public String getFirstName() {
    	return firstName;
    }

    public String getLastName() {
    	return lastName;
    }

    public String getUsername() {
    	return username;
    }

    public String getEmail() {
    	return email;
    }

    public String getPassword() {
    	return password;
    }
}
