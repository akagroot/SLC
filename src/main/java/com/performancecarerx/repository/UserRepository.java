/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository;

import com.performancecarerx.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface UserRepository {
    public UserProfileModel getUserById(Integer userId);
    public UserProfileModel getUserByEmail(String email);
    public UserProfileModel insertUser(UserProfileModel userModel);
    public List<UserProfileModel> getAllUsers();
    public Boolean setUserRole(Integer userId, String role);
    public Integer getNumberOfAdmins();
}
