/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.controller;

import com.performancecarerx.constants.Constants;
import com.performancecarerx.exception.NotAllowedException;
import com.performancecarerx.exception.NotFoundException;
import com.performancecarerx.model.ExerciseGoalModel;
import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.UserProfileModel;
import com.performancecarerx.model.dto.AddExerciseModel;
import com.performancecarerx.model.dto.GoalDataResponse;
import com.performancecarerx.model.dto.GroupedExerciseGoal;
import com.performancecarerx.model.dto.GroupedExercises;
import com.performancecarerx.service.ExerciseGoalService;
import com.performancecarerx.service.ExerciseGroupService;
import com.performancecarerx.service.ExerciseService;
import com.performancecarerx.service.SecurityService;
import com.performancecarerx.service.UserProfileService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jberroteran
 */
@RestController
public class ExerciseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class); 
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserProfileService userService;
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private ExerciseGroupService exerciseGroupService;
    
    @Autowired
    private ExerciseGoalService exerciseGoalService;
    
    @RequestMapping(value="/api/v1/addGoal", method=RequestMethod.POST)
    public ExerciseGoalModel addExerciseGoal(@RequestBody @Valid ExerciseGoalModel model) {
        LOGGER.debug("addExerciseGoal() {}", model);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseGoalService.addGoal(model);
    }
    
    @RequestMapping(value="/api/v1/deleteGoal/{goalId}", method=RequestMethod.GET)
    public Boolean deleteExerciseGoal(@PathVariable("goalId") Integer goalId) {
        LOGGER.debug("deleteExerciseGoal() {}", goalId);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseGoalService.deleteGoal(goalId);
    }
    
    @RequestMapping(value="/api/v1/groupedGoals/{userId}", method=RequestMethod.GET)
    public GoalDataResponse getGroupedExerciseGoals(@PathVariable("userId") Integer userId) {
        LOGGER.debug("getGroupedExerciseGoals() {}", userId);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        List<GroupedExerciseGoal> goals = exerciseGoalService.getGoalsForUser(userId);
        UserProfileModel userModel = userService.getUserById(userId);
        return new GoalDataResponse(userModel, goals);
    }
    
    @RequestMapping(value = "/api/v1/exercises", method=RequestMethod.GET)
    public List<ExerciseModel> getExercises() {
        LOGGER.debug("getExercises()");
        securityService.checkUserIsLoggedIn();
        
        List<ExerciseModel> exercises = exerciseService.getExercises();
        return exercises;
    }
    
    @RequestMapping(value = "/api/v1/addExercise", method=RequestMethod.POST)
    public ExerciseModel addExercise(@RequestBody @Valid ExerciseModel model) {
        LOGGER.debug("addExercise()");
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        
        return exerciseService.addExercise(model);
    }
    
    @RequestMapping(value="/api/v1/deleteExercise/{id}", method=RequestMethod.GET)
    public Boolean deleteExercise(@PathVariable("id") Integer exerciseId) {
        LOGGER.debug("deleteExercise() {}", exerciseId);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        
        return exerciseService.deleteExercise(exerciseId);
    }
    
    @RequestMapping(value="/api/v1/updateExercise", method=RequestMethod.POST)
    public ExerciseModel updateExercise(@RequestBody @Valid ExerciseModel model) {
        LOGGER.debug("updateExercise() {}", model);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseService.updateExercise(model);
    }
    
    @RequestMapping(value = "/api/v1/exerciseGroups", method=RequestMethod.GET)
    public List<ExerciseGroupModel> getExerciseGroups() {
        LOGGER.debug("getExerciseGroups()");
        securityService.checkUserIsLoggedIn();
        
        List<ExerciseGroupModel> exerciseGroups = exerciseService.getExerciseGroups();
        return exerciseGroups;
    }
    
    @RequestMapping(value = "/api/v1/groupedExercises", method=RequestMethod.GET)
    public List<GroupedExercises> getGroupedExercises() {
        LOGGER.debug("getGroupedExercises()");
        securityService.checkUserIsLoggedIn();
        
        List<GroupedExercises> groupedExercises = exerciseService.getGroupedExercises();
        return groupedExercises;
    }
    
    @RequestMapping(value = "/api/v1/addExerciseGroup", method=RequestMethod.POST)
    public ExerciseGroupModel addExerciseGroup(@RequestBody @Valid ExerciseGroupModel model) {
        LOGGER.debug("addExerciseGroup() {}", model);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseGroupService.addExerciseGroup(model);
    }
    
    @RequestMapping(value = "/api/v1/updateExerciseGroup", method=RequestMethod.POST)
    public ExerciseGroupModel updateExerciseGroup(@RequestBody @Valid ExerciseGroupModel model) {
        LOGGER.debug("updateExerciseGroup() {}", model);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseGroupService.updateExerciseGroup(model);
    }
    
    @RequestMapping(value = "/api/v1/deleteExerciseGroup/{keyname}", method=RequestMethod.GET)
    public Boolean deleteExerciseGroup(@PathVariable("keyname") String keyname) {
        LOGGER.debug("deleteExerciseGroup() {}", keyname);
        String user = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(user);
        return exerciseGroupService.deleteExerciseGroup(keyname);
    }
    
    @RequestMapping(value = "/api/v1/exercisesRecorded/{userId}", method=RequestMethod.GET)
    public List<ExerciseRecordedModel> getExercisesRecorded(@PathVariable("userId") Integer userId) {
        LOGGER.debug("getExercisesRecorded(userId) {}", userId);
        String email = securityService.checkUserIsLoggedIn();
        securityService.checkUserIsAdmin(email);
        
        UserProfileModel queriedFor = userService.getUserById(userId);
        
        if(queriedFor == null) {
            throw new NotFoundException();
        }
        
        return exerciseService.getExercisesForUser(queriedFor.getEmail());
    }
    
    @RequestMapping(value = "/api/v1/exercisesRecorded", method=RequestMethod.GET)
    public List<ExerciseRecordedModel> getExercisesRecordedForLoggedInUser() {
        LOGGER.debug("getExercisesRecorded()");
        String email = securityService.checkUserIsLoggedIn();
        return exerciseService.getExercisesForUser(email);
    }
    
    @RequestMapping(value = "/api/v1/addExerciseToUser", method=RequestMethod.POST)
    public ExerciseRecordedModel addExerciseToUser(@RequestBody @Valid AddExerciseModel model) {
        LOGGER.debug("addExerciseToUser() {}", model);
        String email = securityService.checkUserIsLoggedIn();
        UserProfileModel loggedInUser = userService.getUserByEmail(email);
        
        if(!Objects.equals(loggedInUser.getId(), model.getUserId())) {
            securityService.checkUserIsAdmin(email);
        }
        
        UserProfileModel queriedFor = userService.getUserById(model.getUserId());
        
        if(queriedFor == null) {
            throw new NotFoundException();
        }
        
        return exerciseService.addExerciseToUser(model);
    }
    
    @RequestMapping(value = "/api/v1/deleteExerciseRecorded/{userId}/{exerciseId}", method=RequestMethod.GET)
    public Boolean deleteExerciseRecorded(@PathVariable(value="userId") Integer userId, @PathVariable(value="exerciseId") Integer exerciseRecordedId) {
        LOGGER.debug("deleteExerciseRecorded() {} {}", userId, exerciseRecordedId);
        String email = securityService.checkUserIsLoggedIn();
        UserProfileModel loggedInUser = userService.getUserByEmail(email);
        
        if(!loggedInUser.getRole().equals(Constants.USER_ROLE_ADMIN) && 
                !exerciseService.doesExerciseRecordedBelongToUser(userId, exerciseRecordedId)) {
            throw new NotAllowedException("This exercise does not belong to your account.");
        }
        
        exerciseService.deleteExerciseRecorded(exerciseRecordedId);
        return true;
    }
}
