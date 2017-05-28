/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.service.impl;

import com.slc.constants.Constants;
import com.slc.model.StormpathAccount;
import com.slc.model.UserProfileModel;
import com.slc.model.dto.AddUserModel;
import com.slc.model.dto.UpdateUserInfoModel;
import com.slc.repository.ParameterRepository;
import com.slc.repository.UserRepository;
import com.slc.service.StormpathService;
import com.slc.service.UserProfileService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParameterRepository parameterRepository;
    
    @Autowired
    private StormpathService stormpathService;
    
    @Override
    public UserProfileModel getUserByEmail(String email) {
        UserProfileModel model = userRepository.getUserByEmail(email);
        
        if(model == null) {
            createUser(email);
        }
        
        return model;
    }
    
    private UserProfileModel createUser(String email) {
        LOGGER.debug("Create user: {}", email);
        StormpathAccount account = stormpathService.getStormpathAccount(email);
        
        UserProfileModel model = new UserProfileModel(email);
        model.setFirstName(account.getFirstName());
        model.setLastName(account.getLastName());
        model.setRole(Constants.USER_ROLE_ATHLETE);
        return createUser(model);
    }
    
    @Override
    public UserProfileModel createUser(UserProfileModel model) {
        return userRepository.insertUser(model);
    }
    
    @Override
    public UserProfileModel createUser(AddUserModel model) {
        UserProfileModel userProfileModel = model.toUserProfileModel();
        return createUser(userProfileModel);
    }
    
    @Override
    public UserProfileModel updateUser(UpdateUserInfoModel model) {
        UserProfileModel upModel = userRepository.getUserById(model.getUserId());
        upModel.setFirstName(model.getFirstName());
        upModel.setLastName(model.getLastName());
        return updateUser(upModel);
    }
    
    private UserProfileModel updateUser(UserProfileModel model) {
        return userRepository.updateUser(model);
    }

    @Override
    public UserProfileModel getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<UserProfileModel> getAllUsers(UserProfileModel loggedInUser) {
        return userRepository.getAllUsers();
    }
    
    @Override
    public List<UserProfileModel> getAllAdmins() {
        return userRepository.getAllAdmins();
    }

    @Override
    public Boolean setUserRole(Integer userId, String role) {
        return userRepository.setUserRole(userId, role);
    }
    
    @Override
    public Integer getNumberOfAdmins() {
        return userRepository.getNumberOfAdmins();
    }
    
    @Override
    public Boolean deleteUser(Integer userId) {
        return userRepository.deleteUser(userId);
    }
    
    @Override
    public Boolean doesUserExist(String email) {
        UserProfileModel user = userRepository.getUserByEmail(email);
        return user != null;
    }
}
