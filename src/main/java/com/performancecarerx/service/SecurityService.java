package com.performancecarerx.service;

import com.performancecarerx.model.UserProfileModel;

/**
 *
 * @author jberroteran
 */
public interface SecurityService {
    public String getLoggedInUser();
    public String checkUserIsLoggedIn();   
    public UserProfileModel checkUserIsAdmin(String email);
}