/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository;

import com.slc.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface UserRepository {
    public UserProfileModel getUserById(Integer userId);
    public UserProfileModel getUserByEmail(String email);
    public UserProfileModel insertUser(UserProfileModel userModel);
    public UserProfileModel updateUser(UserProfileModel userModel);
    public Boolean deleteUser(Integer userId);
    public List<UserProfileModel> getAllUsers();
    public List<UserProfileModel> getAllAdmins();
    public List<UserProfileModel> getAllUsersAthletes(Integer userId);
    public Integer removeCoachId(Integer coachId);
    public Boolean setUserRole(Integer userId, String role);
    public Boolean setCoach(Integer userId, Integer coachId);
    public Integer getNumberOfAdmins();
}
