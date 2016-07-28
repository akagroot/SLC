/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.constants.Constants;
import com.performancecarerx.exception.UnauthorizedException;
import com.performancecarerx.model.UserProfileModel;
import com.performancecarerx.service.SecurityService;
import com.performancecarerx.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("securityService")
public class SecurityServiceImpl implements SecurityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private UserProfileService userService;
    
    @Override
    public String getLoggedInUser() {
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();

            if (principal == null) {
                throw new UnauthorizedException();
            }

            LOGGER.debug("Found user: " + principal.getName());
            return principal.getName();
    }

    @Override
    public String checkUserIsLoggedIn() {
        LOGGER.debug("checkUserIsLoggedIn()");
        String email = getLoggedInUser();
        
        if(email == null) {
            throw new UnauthorizedException();
        }
        
        return email;
    }

    @Override
    public UserProfileModel checkUserIsAdmin(String email) {
        LOGGER.debug("checkUserIsAdmin()");
        UserProfileModel loggedInUser = userService.getUserByEmail(email);
        
        if(!loggedInUser.getRole().equals(Constants.USER_ROLE_ADMIN)) {
            throw new UnauthorizedException();
        }
        
        return loggedInUser;
    }
}

