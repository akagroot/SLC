/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository;

import com.slc.model.ExerciseGoalModel;
import com.slc.model.dto.UpdateGoalVisibilityModel;
import com.slc.repository.impl.ExerciseGoalRepositoryImpl.ExerciseGoalToRecordedModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface ExerciseGoalRepository {
    public List<ExerciseGoalModel> getGoalsForUser(Integer userId);
    public ExerciseGoalModel addGoal(ExerciseGoalModel model);
    public Boolean deleteGoal(Integer goalId);
    public Boolean doesGoalBelongToUser(Integer userId, Integer goalId);
    public Boolean doesUserHaveGoal(Integer userId, Integer exerciseId);
    public List<ExerciseGoalToRecordedModel> getGroupedGoalsForUser(String email);
}
