/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.model.StormpathAccount;
import com.performancecarerx.service.StormpathService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("stormpathService")
public class StormpathServiceImpl implements StormpathService {

//    private final String STORMPATH_CLIENT_APIKEY_ID = "V99UNQJIXOSTC0LFWV1GGBR8X";
//    private final String STORMPATH_CLIENT_APIKEY_SECRET = "MZ1+TiXSakXnzVirkfI9U1YB9h737NgGOxze4yq7mbk";
//    private final String STORMPATH_APPLICATION_HREF = "https://api.stormpath.com/v1/applications/5GzpRvRW6ainb6yVyzu6Qz";
//    private final String STORMPATH_APPLICATION_HREF = "https://api.stormpath.com/v1/applications/3XesVyJpcvbphcEUBwZNvw";

    private static final Logger LOGGER = LoggerFactory.getLogger(StormpathServiceImpl.class);
    
    @Autowired
    private Application application;
    
    @Override
    public StormpathAccount getStormpathAccount(String email) {
        LOGGER.debug("getStormpathAccount: {}", email);
        
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("username", email);
        AccountList accounts = application.getAccounts(queryParams);

        StormpathAccount account = null;
        for (Account acct : accounts) {
            account = new StormpathAccount(acct.getEmail(), acct.getGivenName(), acct.getSurname());
        }
        return account;
    }
    
}
