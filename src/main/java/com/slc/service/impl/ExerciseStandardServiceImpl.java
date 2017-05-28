/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.service.impl;

import com.slc.model.ExerciseRecordedModel;
import com.slc.model.ExerciseStandard;
import com.slc.repository.ExerciseStandardRepository;
import com.slc.service.ExerciseStandardService;
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
    public ExerciseRecordedModel getStandardForUser(Integer userId) {
        LOGGER.debug("getStandardForUser: {}", userId);
        return exerciseStandardRepository.getStandardForUser(userId);
    }

    @Override
    public ExerciseRecordedModel updateStandardForUser(ExerciseRecordedModel standard) {
        LOGGER.debug("updateStandard: {}", standard);
        
        ExerciseRecordedModel currentStandard = exerciseStandardRepository.getStandardForUser(standard.getUserId());
        
        if(currentStandard == null) {
            standard = exerciseStandardRepository.addStandard(standard);
        } else {
            standard = exerciseStandardRepository.updateStandard(standard);
        }
         
        return standard;
    }
    
}
