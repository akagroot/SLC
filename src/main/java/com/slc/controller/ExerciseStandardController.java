/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.controller;

import com.slc.model.ExerciseRecordedModel;
import com.slc.model.ExerciseStandard;
import com.slc.service.ExerciseStandardService;
import com.slc.service.SecurityService;
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
public class ExerciseStandardController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseStandardController.class); 
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private ExerciseStandardService exerciseStandardService;
    
    @RequestMapping(value="/api/v1/getStandard/{userId}", method=RequestMethod.GET)
    public ExerciseRecordedModel getStandardForUser(@PathVariable("userId") Integer userId) {
        LOGGER.debug("/getStandard/{}", userId);
        // Check user is logged in
        securityService.getLoggedInUser();
        return exerciseStandardService.getStandardForUser(userId);
    }
    
    @RequestMapping(value="/api/v1/updateStandard", method=RequestMethod.POST) 
    public Boolean updateStandard(@RequestBody ExerciseRecordedModel standard) {
        LOGGER.debug("/updateStandard: {}", standard);
        String loggedInEmail = securityService.getLoggedInUser();
        securityService.checkUserIsAdmin(loggedInEmail);
        exerciseStandardService.updateStandardForUser(standard);
        return true;
    }
}
