/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.UserProfileModel;
import com.performancecarerx.model.dto.AddUserModel;
import com.performancecarerx.model.dto.UpdateUserInfoModel;
import com.performancecarerx.model.dto.UserDataResponse;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface UserProfileService {
    public UserProfileModel getUserByEmail(String email);
    public UserProfileModel getUserById(Integer userId);
    public List<UserProfileModel> getAllUsers();
    public UserDataResponse buildUserDataResponse(String email);
    public UserProfileModel createUser(UserProfileModel model);
    public UserProfileModel createUser(AddUserModel model);
    public UserProfileModel updateUser(UpdateUserInfoModel model);
    public Boolean deleteUser(Integer userId);
    public Boolean setUserRole(Integer userId, String role);
    public Integer getNumberOfAdmins();
}
