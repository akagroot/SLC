/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository;

/**
 *
 * @author jberroteran
 */
public interface ParameterRepository {
    public String getParameterValue(String name);
    public void setParameterValue(String name, String value);
    public void insertParameterValue(String name, String value);
}
