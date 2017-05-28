/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.service.impl;

import com.slc.constants.Constants;
import com.slc.exception.NotFoundException;
import com.slc.model.ExerciseGoalModel;
import com.slc.model.ExerciseGroupModel;
import com.slc.model.ExerciseModel;
import com.slc.model.ExerciseRecordedModel;
import com.slc.model.ExerciseStandard;
import com.slc.model.StormpathAccount;
import com.slc.model.UserProfileModel;
import com.slc.model.dto.AddUserModel;
import com.slc.model.dto.ExercisesByDate;
import com.slc.model.dto.GroupedGradedExercises;
import com.slc.model.dto.GroupedRecordedExercises;
import com.slc.model.dto.UpdateUserInfoModel;
import com.slc.model.dto.UserDataResponse;
import com.slc.repository.ExerciseGoalRepository;
import com.slc.repository.ParameterRepository;
import com.slc.repository.UserRepository;
import com.slc.repository.impl.ExerciseGoalRepositoryImpl;
import com.slc.service.ExerciseService;
import com.slc.service.ExerciseStandardService;
import com.slc.service.StormpathService;
import com.slc.service.UserProfileService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
    private ParameterRepository parameterRepository;
    
    @Autowired
    private ExerciseGoalRepository exerciseGoalRepository;
    
    @Autowired
    private ExerciseStandardService exerciseStandardService;
    
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
        UserProfileModel userProfileModel = model.toUserProfileModel();
        return createUser(userProfileModel);
    }
    
    @Override
    public UserProfileModel updateUser(UpdateUserInfoModel model) {
        UserProfileModel upModel = userRepository.getUserById(model.getUserId());
        upModel.setFirstName(model.getFirstName());
        upModel.setLastName(model.getLastName());
        upModel.setCoachId(model.getCoachId());
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
    public List<UserProfileModel> getAllUsers(UserProfileModel loggedInUser) {
        return userRepository.getAllUsers();
    }
    
    @Override
    public List<UserProfileModel> getAllAdmins() {
        return userRepository.getAllAdmins();
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
        userRepository.removeCoachId(userId);
        return userRepository.deleteUser(userId);
    }
    
    @Override
    public Boolean doesUserExist(String email) {
        UserProfileModel user = userRepository.getUserByEmail(email);
        return user != null;
    }
    
    @Override
    public void setPerfectAccountId(Integer id) {
        String value = String.valueOf(id);
        
        String currentValue = parameterRepository.getParameterValue(Constants.PARAMETER_PERFECT_ACCOUNT_ID);
        if(currentValue == null) {
            parameterRepository.insertParameterValue(Constants.PARAMETER_PERFECT_ACCOUNT_ID, value);
        } else {
            parameterRepository.setParameterValue(Constants.PARAMETER_PERFECT_ACCOUNT_ID, value);   
        }
    }
    
    @Override
    public UserDataResponse getPerfectAccount() {
        String value = parameterRepository.getParameterValue(Constants.PARAMETER_PERFECT_ACCOUNT_ID);
        if(value == null) {
            throw new NotFoundException("The perfect account PARAMETER has not been set up.");
        }
        
        Integer perfectAccountId = Integer.parseInt(value);
        UserProfileModel model = userRepository.getUserById(perfectAccountId);
        
        if(model == null) {
            throw new NotFoundException("The perfect account USER has not been set up.");
        }
        
        return buildUserDataResponse(model.getEmail());
    }
    
    @Override
    public UserDataResponse buildUserDataResponse(String email) {
        UserProfileModel model = userRepository.getUserByEmail(email);
        List<ExercisesByDate> exercisesByDateList = getExercisesByDateForUser(email);
        List<GroupedRecordedExercises> groupedRecordedExercisesList = getGroupedRecordedExercisesForUser(email);
        ExerciseRecordedModel standard = getExerciseStandardForUser(model.getId());
        
        UserDataResponse response = new UserDataResponse(model, exercisesByDateList, groupedRecordedExercisesList, standard);
        return response;
    }
    
    public ExerciseRecordedModel getExerciseStandardForUser(Integer userId) {
        ExerciseRecordedModel standard = exerciseStandardService.getStandardForUser(userId);
        if(standard != null) {
            ExerciseModel exercise = exerciseService.getExercise(standard.getExerciseId());
            standard.setExerciseModel(exercise);
        }
        return standard;
    }
    
    public List<GroupedRecordedExercises> getGroupedRecordedExercisesForUser(String email) {
        List<GroupedRecordedExercises> groupedRecordedList = new ArrayList();
        List<ExerciseRecordedModel> exercisesRecorded = exerciseService.getExercisesForUser(email);
        
        GroupedRecordedExercises grouped = null;
        ExerciseGroupModel lastGroupModel = null;
        String atGroup = null;
        List<ExerciseRecordedModel> currentList = new ArrayList();
        int monthsAgo;
        ExerciseRecordedModel currentRecord;
        
        exercisesRecorded.sort(new Comparator<ExerciseRecordedModel>() {
            @Override
            public int compare(ExerciseRecordedModel o1, ExerciseRecordedModel o2) {
                ExerciseGroupModel o1gm = o1.getExerciseModel().getExerciseGroupModel();
                ExerciseGroupModel o2gm = o2.getExerciseModel().getExerciseGroupModel();
                
                if(o1gm.getDisplayName().compareToIgnoreCase(o2gm.getDisplayName()) != 0) {
                    return o1gm.getDisplayName().compareToIgnoreCase(o2gm.getDisplayName());
                }
                
                String o1Name = o1.getExerciseModel().getName();
                String o2Name = o2.getExerciseModel().getName();
                
                if(o1Name.compareToIgnoreCase(o2Name) != 0) {
                    return o1Name.compareToIgnoreCase(o2Name);
                }
                
                return o1.getRecordedDttm().compareTo(o2.getRecordedDttm());
            }
        } );
        
        for(ExerciseRecordedModel ex : exercisesRecorded) {
            ExerciseGroupModel exgm = ex.getExerciseModel().getExerciseGroupModel();
            if(!exgm.getKeyName().equals(atGroup)) {
                if(atGroup != null) {
                    lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
                    grouped = new GroupedRecordedExercises(lastGroupModel, currentList);
                    groupedRecordedList.add(grouped);
                    
                    currentList = new ArrayList();
                }
                
                atGroup = exgm.getKeyName();
            }
            
            monthsAgo = getMonthsPassed(ex.getRecordedDttm());
            ex.setMonthsAgo(monthsAgo);
            currentList.add(ex);
        }
        
        if(atGroup != null) {
            lastGroupModel = currentList.get(0).getExerciseModel().getExerciseGroupModel();
            grouped = new GroupedRecordedExercises(lastGroupModel, currentList);
            groupedRecordedList.add(grouped);
        }
        
        return groupedRecordedList;
    }
    
    public List<GroupedGradedExercises> getGroupedGradedExercisesForUser(String email, Boolean isAdmin) {
        List<GroupedGradedExercises> groupedGradedList = new ArrayList();
        List<ExerciseGoalRepositoryImpl.ExerciseGoalToRecordedModel> goalsToRecords = exerciseGoalRepository.getGroupedGoalsForUser(email);
        LOGGER.debug("getGroupedGoalsForUser() {}", goalsToRecords);
        
        GroupedGradedExercises grouped = null;
        ExerciseGroupModel lastGroupModel = null;
        String atGroup = null;
        List<ExerciseGoalModel> currentList = new ArrayList();
        float average, grade;
        int estimated1RM, goal1RM, monthsAgo;
        ExerciseGoalModel currentGoal;
        ExerciseRecordedModel currentRecord;
        
        for(ExerciseGoalRepositoryImpl.ExerciseGoalToRecordedModel e : goalsToRecords) {
            if(!e.goal.getExerciseModel().getExerciseGroupKeyName().equals(atGroup)) {
                if(atGroup != null) {
                    average = 0.0f;
                    for(ExerciseGoalModel r : currentList) {
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
            
//            e.goal.setEstimated1RM(estimated1RM);
//            e.goal.setGoal(goal1RM);
            e.goal.setMonthsAgo(monthsAgo);
            
            grade = (float)estimated1RM/goal1RM;
//            e.goal.setGrade(grade);
            currentList.add(e.goal);
        }
        
        if(atGroup != null) {
            average = 0.0f;
            for(ExerciseGoalModel r : currentList) {
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
