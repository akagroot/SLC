/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.constants.Constants;
import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.StormpathAccount;
import com.performancecarerx.model.UserProfileModel;
import com.performancecarerx.model.dto.AddUserModel;
import com.performancecarerx.model.dto.ExercisesByDate;
import com.performancecarerx.model.dto.GroupedGradedExercises;
import com.performancecarerx.model.dto.UpdateUserInfoModel;
import com.performancecarerx.model.dto.UserDataResponse;
import com.performancecarerx.repository.ExerciseGoalRepository;
import com.performancecarerx.repository.UserRepository;
import com.performancecarerx.repository.impl.ExerciseGoalRepositoryImpl;
import com.performancecarerx.service.ExerciseService;
import com.performancecarerx.service.StormpathService;
import com.performancecarerx.service.UserProfileService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ExerciseGoalRepository exerciseGoalRepository;
    
    @Autowired
    private StormpathService stormpathService;
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Override
    public UserProfileModel getUserByEmail(String email) {
        UserProfileModel model = userRepository.getUserByEmail(email);
        
        if(model == null) {
            createUser(email);
        }
        
        return model;
    }
    
    private UserProfileModel createUser(String email) {
        LOGGER.debug("Create user: {}", email);
        StormpathAccount account = stormpathService.getStormpathAccount(email);
        
        UserProfileModel model = new UserProfileModel(email);
        model.setFirstName(account.getFirstName());
        model.setLastName(account.getLastName());
        model.setRole(Constants.USER_ROLE_ATHLETE);
        return createUser(model);
    }
    
    @Override
    public UserProfileModel createUser(UserProfileModel model) {
        return userRepository.insertUser(model);
    }
    
    @Override
    public UserProfileModel createUser(AddUserModel model) {
        UserProfileModel userProfileModel = new UserProfileModel(model.getEmail());
        userProfileModel.setFirstName(model.getFirstName());
        userProfileModel.setLastName(model.getLastName());
        userProfileModel.setRole(model.getRole());
        return createUser(userProfileModel);
    }
    
    @Override
    public UserProfileModel updateUser(UpdateUserInfoModel model) {
        UserProfileModel upModel = userRepository.getUserById(model.getUserId());
        upModel.setFirstName(model.getFirstName());
        upModel.setLastName(model.getLastName());
        return updateUser(upModel);
    }
    
    private UserProfileModel updateUser(UserProfileModel model) {
        return userRepository.updateUser(model);
    }

    @Override
    public UserProfileModel getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<UserProfileModel> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Boolean setUserRole(Integer userId, String role) {
        return userRepository.setUserRole(userId, role);
    }
    
    @Override
    public Integer getNumberOfAdmins() {
        return userRepository.getNumberOfAdmins();
    }
    
    @Override
    public Boolean deleteUser(Integer userId) {
        return userRepository.deleteUser(userId);
    }
    
    @Override
    public UserDataResponse buildUserDataResponse(String email) {
        UserProfileModel model = userRepository.getUserByEmail(email);
        List<ExercisesByDate> exercisesByDateList = getExercisesByDateForUser(email);
        List<GroupedGradedExercises> groupedGradedExercisesList = getGroupedGradedExercisesForUser(email);
        
        UserDataResponse response = new UserDataResponse(model, exercisesByDateList, groupedGradedExercisesList);
        return response;
    }
    
    public List<GroupedGradedExercises> getGroupedGradedExercisesForUser(String email) {
        List<GroupedGradedExercises> groupedGradedList = new ArrayList();
        List<ExerciseGoalRepositoryImpl.ExerciseGoalToRecordedModel> goalsToRecords = exerciseGoalRepository.getGroupedGoalsForUser(email);
        LOGGER.debug("getGroupedGoalsForUser() {}", goalsToRecords);
        
        GroupedGradedExercises grouped = null;
        ExerciseGroupModel lastGroupModel = null;
        String atGroup = null;
        List<ExerciseRecordedModel> currentList = new ArrayList();
        float average, grade;
        int estimated1RM, goal1RM, monthsAgo;
        ExerciseGoalModel currentGoal;
        ExerciseRecordedModel currentRecord;
        
        for(ExerciseGoalRepositoryImpl.ExerciseGoalToRecordedModel e : goalsToRecords) {
            if(!e.goal.getExerciseModel().getExerciseGroupKeyName().equals(atGroup)) {
                if(atGroup != null) {
                    average = 0.0f;
                    for(ExerciseRecordedModel r : currentList) {
                        average += r.getGrade();
                    }
                    average = average/currentList.size();
                    
                    lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
                    grouped = new GroupedGradedExercises(lastGroupModel, currentList, average);
                    groupedGradedList.add(grouped);
                    
                    currentList = new ArrayList();
                }
                
                atGroup = e.goal.getExerciseModel().getExerciseGroupKeyName();
            }
            
            monthsAgo = getMonthsPassed(e.recorded.getRecordedDttm());
            estimated1RM = getEstimated1RM(e.recorded.getReps(), e.recorded.getWeight());
            goal1RM = getEstimated1RM(e.goal.getReps(), e.goal.getWeight());
            
            e.recorded.setEstimated1RM(estimated1RM);
            e.recorded.setGoal(goal1RM);
            e.recorded.setMonthsAgo(monthsAgo);
            
            grade = (float)estimated1RM/goal1RM;
            e.recorded.setGrade(grade);
            currentList.add(e.recorded);
        }
        
        if(atGroup != null) {
            average = 0.0f;
            for(ExerciseRecordedModel r : currentList) {
                average += r.getGrade();
            }
            average = average/currentList.size();
                    
            lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
            grouped = new GroupedGradedExercises(lastGroupModel, currentList, average);
            groupedGradedList.add(grouped);
        }
        
        return groupedGradedList;
    }
    
    private final float[] multipliers = new float[] {1.0f, 0.94f, 0.91f, 0.88f, 0.858f, 0.838f, 
        0.808f, 0.788f, 0.768f, 0.748f, 0.725f, 0.704f, 0.689f, 0.676f, 0.662f, 0.649f, 
        0.637f, 0.628f, 0.617f, 0.606f};
    
    private int getEstimated1RM(int reps, int weight) {
        float multiplier = multipliers[reps-1];
        return (int)(weight/multiplier);
    }
    
    private int getMonthsPassed(Date fromMonth) {
        Calendar now = Calendar.getInstance();
        Calendar from = Calendar.getInstance();
        from.setTime(fromMonth);
        
        int diffYear = now.get(Calendar.YEAR) - from.get(Calendar.YEAR);
        int diffMonth = diffYear*12 + now.get(Calendar.MONTH) - from.get(Calendar.MONTH);
        return diffMonth;
    }
    
    public List<ExercisesByDate> getExercisesByDateForUser(String email) {
        List<ExercisesByDate> exercisesByDateList = new ArrayList();
        List<ExerciseRecordedModel> exercisesRecorded = exerciseService.getExercisesForUser(email);
        Calendar atDate = null;
        ExercisesByDate nextItem;
        List<ExerciseRecordedModel> currentList = new ArrayList();
        
        for(ExerciseRecordedModel er : exercisesRecorded) {
            Calendar erCal = Calendar.getInstance();
            erCal.setTime(er.getRecordedDttm());
            
            if(!sameDay(erCal, atDate)) {
                if(atDate != null) {
                    nextItem = new ExercisesByDate(atDate.getTime(), currentList);
                    exercisesByDateList.add(nextItem);
                    currentList = new ArrayList();
                }
                
                atDate = erCal;
            }
            
            currentList.add(er);
        }
        
        if(atDate != null) {
            nextItem = new ExercisesByDate(atDate.getTime(), currentList);
            exercisesByDateList.add(nextItem);
        }
        
        return exercisesByDateList;
    }
    
    private Boolean sameDay(Calendar cal1, Calendar cal2) {
        if(cal1 == null && cal2 == null) 
            return true;
        
        if(cal1 == null) 
            return false;
        
        if(cal2 == null) 
            return false;
        
        return (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) && 
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && 
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
    }
}
