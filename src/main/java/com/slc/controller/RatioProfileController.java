/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.controller;

import com.slc.model.RatioProfileModel;
import com.slc.model.RatioProfileValueModel;
import com.slc.model.UserProfileModel;
import com.slc.service.RatioProfileService;
import com.slc.service.SecurityService;
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
public class RatioProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatioProfileController.class); 
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private RatioProfileService ratioProfileService;
    
    @RequestMapping(value = "/api/v1/ratioProfiles/{load}", method=RequestMethod.GET)
    public List<RatioProfileModel> ratioProfiles(@PathVariable("load") String load) {
        LOGGER.debug("ratioProfiles() {}", load);
        String email = securityService.checkUserIsLoggedIn();
        
        if(load.equals("s")) {
            return ratioProfileService.getRatioProfiles(false);
        }
        
        return ratioProfileService.getRatioProfiles(true);
    }
    
    @RequestMapping(value = "/api/v1/ratioProfile/{id}", method=RequestMethod.GET)
    public RatioProfileModel getRatioProfile(@PathVariable("id") Integer profileId) {
        LOGGER.debug("getRatioProfile {}", profileId);
        String email = securityService.checkUserIsLoggedIn();
        return ratioProfileService.getRatioProfile(profileId);
    }
    
    @RequestMapping(value = "/api/v1/createRatioProfile", method=RequestMethod.POST)
    public RatioProfileModel createRatioProfile(@RequestBody @Valid RatioProfileModel model) {
        LOGGER.debug("createRatioProfile {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.createRatioProfile(model);
    }
    
    @RequestMapping(value = "/api/v1/deleteRatioProfile/{id}", method=RequestMethod.GET)
    public Boolean deleteRatioProfile(@PathVariable("id") Integer profileId) {
        LOGGER.debug("deleteRatioProfile {}", profileId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.deleteRatioProfile(profileId);
    }
    
    @RequestMapping(value = "/api/v1/updateRatioProfile", method=RequestMethod.POST)
    public RatioProfileModel updateRatioProfile(@RequestBody @Valid RatioProfileModel model) {
        LOGGER.debug("updateRatioProfile {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.updateRatioProfile(model);
    }
    
    @RequestMapping(value = "/api/v1/createRatioProfileValue", method=RequestMethod.POST)
    public RatioProfileValueModel createRatioValueProfile(@RequestBody @Valid RatioProfileValueModel model) {
        LOGGER.debug("createRatioValueProfile {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.createRatioProfileValue(model);
    }
    
    @RequestMapping(value = "/api/v1/deleteRatioProfileValue/{id}", method=RequestMethod.GET)
    public Boolean deleteRatioProfileValue(@PathVariable("id") Integer valueId) {
        LOGGER.debug("deleteRatioProfileValue {}", valueId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.deleteRatioProfileValue(valueId);
    }
    
    @RequestMapping(value = "/api/v1/updateRatioProfileValue", method=RequestMethod.POST)
    public RatioProfileValueModel updateRatioProfileValue(@RequestBody @Valid RatioProfileValueModel model) {
        LOGGER.debug("updateRatioProfileValue {}", model);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        return ratioProfileService.updateRatioProfileValue(model);
    }
}
