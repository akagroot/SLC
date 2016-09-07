/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.dto.GroupedExerciseGoal;
import com.performancecarerx.model.dto.UpdateGoalVisibilityModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface ExerciseGoalService {
    public List<GroupedExerciseGoal> getGoalsForUser(Integer userId);
    public ExerciseGoalModel addGoal(ExerciseGoalModel model);
    public Boolean deleteGoal(Integer goalId);
    public Boolean doesGoalBelongToUser(Integer userId, Integer goalId);
    public Boolean doesUserHaveGoal(Integer userId, Integer exerciseId);
}
