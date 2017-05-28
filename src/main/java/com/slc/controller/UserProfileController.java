/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.controller;

import com.slc.constants.Constants;
import com.slc.exception.NotAllowedException;
import com.slc.model.UserProfileModel;
import com.slc.model.dto.AddUserModel;
import com.slc.model.dto.UpdateUserInfoModel;
import com.slc.model.dto.UpdateUserRoleModel;
import com.slc.model.dto.UserDataResponse;
import com.slc.service.SecurityService;
import com.slc.service.UserProfileService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jberroteran
 */
@RestController
@RequestMapping(value = "/api/pub/v1/userProfile")
public class UserProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class); 
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping(value = "", method=RequestMethod.GET)
    public UserProfileModel userProfile() {
        LOGGER.debug("userProfile()");
        String email = securityService.checkUserIsLoggedIn();
        
        UserProfileModel model = userProfileService.getUserByEmail(email);
        return model;
    }
    
    @RequestMapping(value = "/{userId}", method=RequestMethod.GET) 
    public UserProfileModel userProfile(@PathVariable("userId") Integer userId) {
        LOGGER.debug("userProfile() {}", userId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        return userProfileService.getUserById(userId);
    }
    
    @RequestMapping(value = "/allUsers", method=RequestMethod.GET) 
    public List<UserProfileModel> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        String email = securityService.checkUserIsLoggedIn();
        UserProfileModel loggedInUser = securityService.checkUserIsAdmin(email);
        
        return userProfileService.getAllUsers(loggedInUser);
    }
    
    @RequestMapping(value = "/addUser", method=RequestMethod.POST)
    public UserProfileModel addUser(@RequestBody @Valid AddUserModel model) {
        LOGGER.debug("addUser() {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        if(userProfileService.doesUserExist(model.getEmail())) {
            throw new NotAllowedException("This user already exists in the database.");
        }
        
        return userProfileService.createUser(model);
    }
    
    @RequestMapping(value = "/updateUser", method=RequestMethod.POST) 
    public UserProfileModel updateUser(@RequestBody @Valid UpdateUserInfoModel model) {
        LOGGER.debug("updateUser() {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        return userProfileService.updateUser(model);
    }
    
    @RequestMapping(value = "/updateRole", method=RequestMethod.POST)
    public Boolean setUserRole(@RequestBody @Valid UpdateUserRoleModel role) {
        LOGGER.debug("setUserRole() {}", role);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        if(role.getRole().equals(Constants.USER_ROLE_ATHLETE) && 
                userProfileService.getNumberOfAdmins() == 1) {
            throw new NotAllowedException("This action is not allowed because there would be no more admins. Please create another admin account before disabling this admin account.");
        }
        
        return userProfileService.setUserRole(role.getUserId(), role.getRole());
    }
    
    @RequestMapping(value = "/deleteUser/{userId}", method=RequestMethod.GET)
    public Boolean deleteUser(@PathVariable("userId") Integer userId) {
        LOGGER.debug("deleteUser() {}", userId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        UserProfileModel deleteUser = userProfileService.getUserById(userId);
        
        if(deleteUser.getRole().equals(Constants.USER_ROLE_ADMIN) && 
                userProfileService.getNumberOfAdmins() == 1) {
            throw new NotAllowedException("This action is not allowed because there would be no more admins. Please create another admin account before disabling this admin account.");
        }
        
        return userProfileService.deleteUser(userId);
    }
}
