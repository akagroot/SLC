package com.slc.controller;

import com.slc.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class); 
    
    @Autowired
    private SecurityService securityService;

    public class IsLoggedInResponse {
        public boolean isLoggedIn = false;
        public IsLoggedInResponse(boolean isLoggedIn) {
            this.isLoggedIn = isLoggedIn;
        }
    }
    
    @RequestMapping(value = "/api/v1/isLoggedIn", method=RequestMethod.GET)
    public IsLoggedInResponse isLoggedIn() {
        LOGGER.debug("isLoggedIn()");
        return new IsLoggedInResponse(securityService.getLoggedInUser() != null);
    }
}
