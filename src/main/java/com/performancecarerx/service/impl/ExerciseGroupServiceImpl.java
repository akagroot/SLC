/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.exception.NotAllowedException;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.repository.ExerciseGroupRepository;
import com.performancecarerx.repository.ExerciseRepository;
import com.performancecarerx.service.ExerciseGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("exerciseGroupService")
public class ExerciseGroupServiceImpl implements ExerciseGroupService {
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private ExerciseGroupRepository exerciseGroupRepository;

    @Override
    public List<ExerciseGroupModel> getExerciseGroups() {
        return exerciseGroupRepository.getExerciseGroups();
    }
    
    private Boolean isKeyInCorrectFormat(String key) {
        if(key.contains(" ")) {
            return false;
        }
        for(Character c : key.toCharArray()) {
            if(!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ExerciseGroupModel addExerciseGroup(ExerciseGroupModel model) {
        if(!isKeyInCorrectFormat(model.getKeyName())) {
            throw new NotAllowedException("This key name is not in correct format.  Spaces and special characters are not allowed.  Alphanumeric characters only.");
        }
        if(!exerciseGroupRepository.isKeyNameUnique(model.getKeyName())) {
            throw new NotAllowedException("This key name is not unique.");
        }
        model.setKeyName(model.getKeyName().toUpperCase());
        return exerciseGroupRepository.addExerciseGroup(model);
    }
    
    @Override
    public ExerciseGroupModel updateExerciseGroup(ExerciseGroupModel model) {
        if(exerciseGroupRepository.getExerciseGroup(model.getKeyName()) == null) {
            throw new NotAllowedException("This is not a valid exercise group.");
        }
        return exerciseGroupRepository.updateExerciseGroup(model);
    }

    @Override
    public Boolean deleteExerciseGroup(String exerciseGroupKey) {
        if(exerciseRepository.countExercisesForGroupKey(exerciseGroupKey) > 0) {
            throw new NotAllowedException("This group has exercises assigned to it.  You cannot delete this group name.");
        }
        
        return exerciseGroupRepository.deleteExerciseGroup(exerciseGroupKey);
    }

    @Override
    public Boolean isKeyNameUnique(String keyName) {
        return exerciseGroupRepository.isKeyNameUnique(keyName);
    }
    
}
