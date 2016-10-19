/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.controller;

import com.performancecarerx.constants.Constants;
import com.performancecarerx.exception.NotAllowedException;
import com.performancecarerx.model.UserProfileModel;
import com.performancecarerx.model.dto.AddUserModel;
import com.performancecarerx.model.dto.UpdateUserInfoModel;
import com.performancecarerx.model.dto.UpdateUserRoleModel;
import com.performancecarerx.model.dto.UserDataResponse;
import com.performancecarerx.service.SecurityService;
import com.performancecarerx.service.UserProfileService;
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
public class UserProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class); 
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping(value = "/api/v1/userProfile", method=RequestMethod.GET)
    public UserProfileModel userProfile() {
        LOGGER.debug("userProfile()");
        String email = securityService.checkUserIsLoggedIn();
        
        UserProfileModel model = userProfileService.getUserByEmail(email);
        return model;
    }
    
    @RequestMapping(value = "/api/v1/userProfile/{userId}", method=RequestMethod.GET) 
    public UserProfileModel userProfile(@PathVariable("userId") Integer userId) {
        LOGGER.debug("userProfile() {}", userId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        return userProfileService.getUserById(userId);
    }
    
    @RequestMapping(value = "/api/v1/allUsers", method=RequestMethod.GET) 
    public List<UserProfileModel> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        String email = securityService.checkUserIsLoggedIn();
        UserProfileModel loggedInUser = securityService.checkUserIsAdmin(email);
        
        return userProfileService.getAllUsers(loggedInUser);
    }
    
    @RequestMapping(value="/api/v1/allCoaches", method=RequestMethod.GET) 
    public List<UserProfileModel> getAllCoaches() {
        LOGGER.debug("getAllCoaches()");
        String email = securityService.checkUserIsLoggedIn();
        return userProfileService.getAllAdmins();
    }
    
    @RequestMapping(value="/api/v1/perfectAccount", method=RequestMethod.GET)
    public UserDataResponse getPerfectAccount() {
        LOGGER.debug("getPerfectAccount()");
        securityService.checkUserIsLoggedIn();
        return userProfileService.getPerfectAccount();
    }
    
    @RequestMapping(value="/api/v1/updatePerfectAccount", method=RequestMethod.POST) 
    public Boolean updatePerfectAccount(@RequestBody Integer newId) {
        LOGGER.debug("updatePerfectAccount {}", newId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        userProfileService.setPerfectAccountId(newId);
        return true;
    }
    
    @RequestMapping(value = "/api/v1/userData", method=RequestMethod.GET)
    public UserDataResponse getUserData() {
        LOGGER.debug("getUserData()");
        String email = securityService.checkUserIsLoggedIn();
        return userProfileService.buildUserDataResponse(email);
    }
    
    @RequestMapping(value = "/api/v1/userData/{userId}", method=RequestMethod.GET)
    public UserDataResponse getUserDataByUserId(@PathVariable("userId") Integer userId) {
        LOGGER.debug("getUserDataByUserId() {}", userId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        UserProfileModel user = userProfileService.getUserById(userId);
        return userProfileService.buildUserDataResponse(user.getEmail());
    }
    
    @RequestMapping(value = "/api/v1/addUser", method=RequestMethod.POST)
    public UserProfileModel addUser(@RequestBody @Valid AddUserModel model) {
        LOGGER.debug("addUser() {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        if(userProfileService.doesUserExist(model.getEmail())) {
            throw new NotAllowedException("This user already exists in the database.");
        }
        
        return userProfileService.createUser(model);
    }
    
    @RequestMapping(value = "/api/v1/updateUser", method=RequestMethod.POST) 
    public UserProfileModel updateUser(@RequestBody @Valid UpdateUserInfoModel model) {
        LOGGER.debug("updateUser() {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        return userProfileService.updateUser(model);
    }
    
    @RequestMapping(value = "/api/v1/updateRole", method=RequestMethod.POST)
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
    
    @RequestMapping(value = "/api/v1/deleteUser/{userId}", method=RequestMethod.GET)
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
