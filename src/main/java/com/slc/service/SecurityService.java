package com.slc.service;

import com.slc.model.UserProfileModel;

/**
 *
 * @author jberroteran
 */
public interface SecurityService {
    public String getLoggedInUser();
    public String checkUserIsLoggedIn();   
    public UserProfileModel checkUserIsAdmin(String email);
}