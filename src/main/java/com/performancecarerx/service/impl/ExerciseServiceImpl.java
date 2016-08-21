/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.exception.NotFoundException;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.RatioProfileModel;
import com.performancecarerx.model.dto.AddExerciseModel;
import com.performancecarerx.model.dto.GroupedExercises;
import com.performancecarerx.repository.ExerciseGroupRepository;
import com.performancecarerx.repository.ExerciseRepository;
import com.performancecarerx.repository.RatioProfileRepository;
import com.performancecarerx.service.ExerciseService;
import com.performancecarerx.service.RatioProfileService;
import com.performancecarerx.service.UserProfileService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private UserProfileService userProfileService;
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private ExerciseGroupRepository exerciseGroupRepository;
    
    @Autowired
    private RatioProfileService ratioProfileService;
    
    @Override
    public List<GroupedExercises> getGroupedExercises() {
        List<ExerciseModel> exercises = exerciseRepository.getGroupedExercises();
        
        List<GroupedExercises> groupedExerciseList = new ArrayList();
        List<ExerciseModel> currentGroupList = new ArrayList();
        ExerciseGroupModel groupModel = null;
        GroupedExercises nextGroup = null;
        
        Map<Integer, RatioProfileModel> ratioProfiles = ratioProfileService.getMappedRatioProfiles(false);
        
        for(ExerciseModel e : exercises) {
            if(groupModel == null || !groupModel.getKeyName().equals(e.getExerciseGroupKeyName())) {
                if(groupModel != null) {
                    nextGroup = new GroupedExercises(groupModel, currentGroupList);
                    groupedExerciseList.add(nextGroup);
                    currentGroupList = new ArrayList();
                }
                
                groupModel = e.getExerciseGroupModel();
            }
            if(e.getId() != null) {
                e.setRatioProfileModel(ratioProfiles.get(e.getRatioProfileId()));
                currentGroupList.add(e);
            }
        }
        
        if(groupModel != null) {
            nextGroup = new GroupedExercises(groupModel, currentGroupList);
            groupedExerciseList.add(nextGroup);
        }
        
        return groupedExerciseList;
    }
    
    @Override
    public List<ExerciseModel> getExercises() {
        return exerciseRepository.getExercises();
    }
    
    @Override
    public ExerciseModel getExercise(Integer id) {
        return exerciseRepository.getExerciseById(id);
    }
    
    @Override
    public ExerciseModel addExercise(ExerciseModel model) {
        return exerciseRepository.addExercise(model);
    }
    
    @Override
    public ExerciseModel updateExercise(ExerciseModel model) {
        if(getExercise(model.getId()) == null) {
            throw new NotFoundException("The exercise was not found.");
        }
        return exerciseRepository.updateExercise(model);
    }

    @Override
    public Boolean deleteExercise(Integer exerciseId) {
        if(getExercise(exerciseId) == null) {
            throw new NotFoundException("The exercise was not found.");
        }
        return exerciseRepository.deleteExercise(exerciseId);
    }
    
    @Override
    public List<ExerciseGroupModel> getExerciseGroups() {
        return exerciseGroupRepository.getExerciseGroups();
    }

    @Override
    public List<ExerciseRecordedModel> getExercisesForUser(String email) {
        return exerciseRepository.getExercisesForUser(email);
    }
    
    @Override
    public ExerciseRecordedModel getExerciseRecorded(Integer id) {
        return exerciseRepository.getExerciseRecorded(id);
    }
    
    @Override
    public Boolean doesExerciseRecordedBelongToUser(Integer userId, Integer exerciseRecordedId) {
        return exerciseRepository.exerciseRecordedBelongsToUser(userId, exerciseRecordedId);
    }
    
    @Override
    public Boolean deleteExerciseRecorded(Integer exerciseRecordedId) {
        return exerciseRepository.deleteExerciseRecorded(exerciseRecordedId);
    }

    @Override
    public ExerciseRecordedModel addExerciseToUser(AddExerciseModel exercise) {
        Integer newId = exerciseRepository.addExerciseToUser(exercise);
        
        ExerciseModel exerciseModel = exerciseRepository.getExerciseById(exercise.getExerciseId());
        
        ExerciseRecordedModel model = new ExerciseRecordedModel(exercise);
        model.setId(newId);
        model.setUserId(exercise.getUserId());
        model.setExerciseModel(exerciseModel);
        
        return model;
    }
}
