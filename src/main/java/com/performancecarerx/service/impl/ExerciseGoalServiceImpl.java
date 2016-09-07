/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.exception.NotAllowedException;
import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.dto.ExercisesByDate;
import com.performancecarerx.model.dto.GroupedExerciseGoal;
import com.performancecarerx.model.dto.GroupedGradedExercises;
import com.performancecarerx.model.dto.UpdateGoalVisibilityModel;
import com.performancecarerx.repository.ExerciseGoalRepository;
import com.performancecarerx.service.ExerciseGoalService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author jberroteran
 */
@Component("exerciseGoalService")
public class ExerciseGoalServiceImpl implements ExerciseGoalService {

    @Autowired
    private ExerciseGoalRepository exerciseGoalRepository;
    
    @Override
    public List<GroupedExerciseGoal> getGoalsForUser(Integer userId) {
        List<ExerciseGoalModel> goals = exerciseGoalRepository.getGoalsForUser(userId);
        List<GroupedExerciseGoal> groupedList = new ArrayList();
        
        GroupedExerciseGoal grouped = null;
        ExerciseGroupModel lastGroupModel = null;
        String atGroup = null;
        List<ExerciseGoalModel> currentList = new ArrayList();
        
        for(ExerciseGoalModel g : goals) {
            if(!g.getExerciseModel().getExerciseGroupKeyName().equals(atGroup)) {
                if(atGroup != null) {
                    lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
                    grouped = new GroupedExerciseGoal(lastGroupModel, currentList);
                    groupedList.add(grouped);
                    
                    currentList = new ArrayList();
                }
                
                atGroup = g.getExerciseModel().getExerciseGroupKeyName();
            }
            
            currentList.add(g);
        }
        
        if(atGroup != null) {
            lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
            grouped = new GroupedExerciseGoal(lastGroupModel, currentList);
            groupedList.add(grouped);
        }
        
        return groupedList;
    }

    @Override
    public ExerciseGoalModel addGoal(ExerciseGoalModel model) {
        if(exerciseGoalRepository.doesUserHaveGoal(model.getUserId(), model.getExerciseId())) {
            throw new NotAllowedException("This user already has this exercise goal.  Delete the existing goal and create a new one.");
        }
        return exerciseGoalRepository.addGoal(model);
    }

    @Override
    public Boolean deleteGoal(Integer goalId) {
        return exerciseGoalRepository.deleteGoal(goalId);
    }

    @Override
    public Boolean doesGoalBelongToUser(Integer userId, Integer goalId) {
        return exerciseGoalRepository.doesGoalBelongToUser(userId, goalId);
    }

    @Override
    public Boolean doesUserHaveGoal(Integer userId, Integer exerciseId) {
        return exerciseGoalRepository.doesUserHaveGoal(userId, exerciseId);
    }
    
}
