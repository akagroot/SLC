/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.service;

import com.slc.model.UserProfileModel;
import com.slc.model.dto.AddUserModel;
import com.slc.model.dto.UpdateUserInfoModel;
import com.slc.model.dto.UserDataResponse;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface UserProfileService {
    public UserProfileModel getUserByEmail(String email);
    public UserProfileModel getUserById(Integer userId);
    public List<UserProfileModel> getAllUsers(UserProfileModel loggedInUser);
    public List<UserProfileModel> getAllAdmins();
    public UserProfileModel createUser(UserProfileModel model);
    public UserProfileModel createUser(AddUserModel model);
    public UserProfileModel updateUser(UpdateUserInfoModel model);
    public Boolean deleteUser(Integer userId);
    public Boolean setUserRole(Integer userId, String role);
    public Boolean doesUserExist(String email);
    public Integer getNumberOfAdmins();
}
