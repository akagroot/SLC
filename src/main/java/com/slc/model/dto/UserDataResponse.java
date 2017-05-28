/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.model.dto;

import com.slc.model.UserProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class UserDataResponse {
    private UserProfileModel userProfileModel;
    
    public UserDataResponse(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
    }
    
    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }
    
    @Override
    public String toString() { 
        StringBuilder builder = new StringBuilder();
        builder.append("UserDataResponse [")
                .append("userProfileModel=").append(userProfileModel)
                .append("]");
        return builder.toString();
    }
}
