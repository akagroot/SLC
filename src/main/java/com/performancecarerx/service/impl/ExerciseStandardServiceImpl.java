/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.model.ExerciseStandard;
import com.performancecarerx.repository.ExerciseStandardRepository;
import com.performancecarerx.service.ExerciseStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jberroteran
 */
@Service
public class ExerciseStandardServiceImpl implements ExerciseStandardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseStandardServiceImpl.class);
    
    @Autowired
    private ExerciseStandardRepository exerciseStandardRepository;
    
    @Override
    public ExerciseStandard getStandardForUser(Integer userId) {
        LOGGER.debug("getStandardForUser: {}", userId);
        return exerciseStandardRepository.getStandardForUser(userId);
    }

    @Override
    public ExerciseStandard updateStandardForUser(ExerciseStandard standard) {
        LOGGER.debug("updateStandard: {}", standard);
        
        ExerciseStandard currentStandard = exerciseStandardRepository.getStandardForUser(standard.getUserId());
        
        if(currentStandard == null) {
            standard = exerciseStandardRepository.addStandard(standard);
        } else {
            standard = exerciseStandardRepository.updateStandard(standard);
        }
         
        return standard;
    }
    
}
