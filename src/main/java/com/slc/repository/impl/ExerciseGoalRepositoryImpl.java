/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository.impl;

import com.slc.model.ExerciseGoalModel;
import com.slc.model.ExerciseGroupModel;
import com.slc.model.ExerciseModel;
import com.slc.model.ExerciseRecordedModel;
import static com.performancecarerx.persistence.tables.ExerciseGoals.EXERCISE_GOALS;
import static com.performancecarerx.persistence.tables.ExerciseGroups.EXERCISE_GROUPS;
import static com.performancecarerx.persistence.tables.Exercises.EXERCISES;
import com.performancecarerx.persistence.tables.ExercisesRecorded;
import static com.performancecarerx.persistence.tables.ExercisesRecorded.EXERCISES_RECORDED;
import static com.performancecarerx.persistence.tables.Users.USERS;
import com.performancecarerx.persistence.tables.records.ExerciseGoalsRecord;
import com.slc.repository.ExerciseGoalRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jberroteran
 */
@Repository
public class ExerciseGoalRepositoryImpl implements ExerciseGoalRepository {
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseGoalRepositoryImpl.class);

    @Autowired
    public ExerciseGoalRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<ExerciseGoalModel> getGoalsForUser(Integer userId) {
        LOGGER.debug("ExerciseGoalRepositoryImpl.getGoalsForUser {}", userId);
        List<Condition> conditions = new ArrayList(Arrays.asList(
            EXERCISE_GOALS.USER_ID.eq(userId)
        ));
        return dslContext.select(EXERCISE_GOALS.ID, 
                EXERCISE_GOALS.EXERCISE_ID, 
                EXERCISE_GOALS.USER_ID, 
                EXERCISE_GOALS.WEIGHT, 
                EXERCISE_GOALS.REPS, 
                EXERCISE_GOALS.CREATED_ON_DTTM, 
                EXERCISES.EXERCISE_GROUP_KEY_NAME, 
                EXERCISES.NAME, 
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISE_GOALS)
                .join(EXERCISES).on(EXERCISES.ID.eq(EXERCISE_GOALS.EXERCISE_ID))
                .join(EXERCISE_GROUPS).on(EXERCISE_GROUPS.KEY_NAME.eq(EXERCISES.EXERCISE_GROUP_KEY_NAME))
                .where(conditions)
                .fetch(new ExerciseGoalModelRecordMapper());
    }

    @Override
    public ExerciseGoalModel addGoal(ExerciseGoalModel model) {
        LOGGER.debug("ExerciseGoalRepositoryImpl.addGoal {}", model);
        ExerciseGoalsRecord record = dslContext.insertInto(EXERCISE_GOALS)
                .columns(EXERCISE_GOALS.USER_ID, 
                        EXERCISE_GOALS.WEIGHT, 
                        EXERCISE_GOALS.REPS, 
                        EXERCISE_GOALS.EXERCISE_ID)
                .values(model.getUserId(), 
                        model.getWeight(), 
                        model.getReps(), 
                        model.getExerciseId())
                .returning(EXERCISE_GOALS.ID)
                .fetchOne();
        model.setId(record.getValue(EXERCISE_GOALS.ID));
        return model;
    }

    @Override
    public Boolean deleteGoal(Integer goalId) {
        LOGGER.debug("ExerciseGoalRepositoryImpl.deleteGoal {}", goalId);
        return dslContext.deleteFrom(EXERCISE_GOALS)
                .where(EXERCISE_GOALS.ID.eq(goalId))
                .execute() == 1;
    }

    @Override
    public Boolean doesGoalBelongToUser(Integer userId, Integer goalId) {
        LOGGER.debug("ExerciseGoalRepositoryImpl.doesGoalBelongToUser {} {}", userId, goalId);
        return dslContext.selectCount()
                .from(EXERCISE_GOALS)
                .where(EXERCISE_GOALS.ID.eq(goalId))
                .and(EXERCISE_GOALS.USER_ID.eq(userId))
                .fetchOne(0, int.class) == 1;
    }

    @Override
    public Boolean doesUserHaveGoal(Integer userId, Integer exerciseId) {
        LOGGER.debug("ExerciseGoalRepositoryImpl.doesUserHaveGoal {} {}", userId, exerciseId);
        return dslContext.selectCount()
                .from(EXERCISE_GOALS)
                .where(EXERCISE_GOALS.EXERCISE_ID.eq(exerciseId))
                .and(EXERCISE_GOALS.USER_ID.eq(userId))
                .fetchOne(0, int.class) == 1;
    }
    

    private final ExercisesRecorded e2 = EXERCISES_RECORDED.as("e2");
    
    @Override
    public List<ExerciseGoalToRecordedModel> getGroupedGoalsForUser(String email) {
        LOGGER.debug("ExerciseRepositoryImpl.getGroupedExercisesForUser {}", email);
        List<Condition> conditions = new ArrayList(Arrays.asList(
                USERS.EMAIL.eq(email), 
                EXERCISES_RECORDED.RECORDED_DTTM.isNull().or(EXERCISES_RECORDED.RECORDED_DTTM.eq(dslContext.select(
                        e2.RECORDED_DTTM.max())
                        .from(e2)
                        .where(EXERCISES_RECORDED.EXERCISE_ID.eq(e2.EXERCISE_ID))))
        ));
        
        return dslContext.select(
                EXERCISE_GOALS.ID,
                EXERCISE_GOALS.EXERCISE_ID, 
                EXERCISE_GOALS.CREATED_ON_DTTM, 
                EXERCISE_GOALS.REPS, 
                EXERCISE_GOALS.WEIGHT, 
                EXERCISE_GOALS.REPS,
                EXERCISE_GOALS.USER_ID,
                EXERCISES_RECORDED.ID, 
                EXERCISES_RECORDED.EXERCISE_ID, 
                EXERCISES_RECORDED.RECORDED_DTTM, 
                EXERCISES_RECORDED.WEIGHT, 
                EXERCISES_RECORDED.REPS,
                EXERCISES_RECORDED.NOTE,
                EXERCISES_RECORDED.USER_ID,
                EXERCISES.NAME, 
                EXERCISES.RATIO_PROFILE_ID,
                EXERCISES.EXERCISE_GROUP_KEY_NAME,
                EXERCISE_GROUPS.DISPLAY_NAME) 
                .from(EXERCISE_GOALS)
                .join(EXERCISES).on(EXERCISE_GOALS.EXERCISE_ID.eq(EXERCISES.ID))
                .join(EXERCISE_GROUPS).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME))
                .join(USERS).on(USERS.ID.eq(EXERCISE_GOALS.USER_ID))
                .join(EXERCISES_RECORDED).on(EXERCISE_GOALS.EXERCISE_ID.eq(EXERCISES_RECORDED.EXERCISE_ID)
                    .and(EXERCISE_GOALS.USER_ID.eq(EXERCISES_RECORDED.USER_ID)))
                .where(conditions)
                .orderBy(EXERCISES.EXERCISE_GROUP_KEY_NAME.asc(), EXERCISES.NAME.asc())
                .fetch(new ExerciseGoalToRecordedModelRecordMapper());
    }
    
    public class ExerciseGoalToRecordedModel {
        public ExerciseGoalModel goal;
        public ExerciseRecordedModel recorded;
        public ExerciseGoalToRecordedModel(ExerciseGoalModel goal, ExerciseRecordedModel recorded) {
            this.goal = goal;
            this.recorded = recorded;
        }
    }
    
    public class ExerciseGoalToRecordedModelRecordMapper implements RecordMapper<Record, ExerciseGoalToRecordedModel> {
        @Override
        public ExerciseGoalToRecordedModel map(Record rec) {
            ExerciseGoalModel goal = rec.map(new ExerciseGoalModelRecordMapper());
            ExerciseRecordedModel recorded = rec.map(new ExerciseRecordedModelRecordMapper());
            goal.setExerciseModel(recorded.getExerciseModel());
            goal.setRecordedModel(recorded);
            
            return new ExerciseGoalToRecordedModel(goal, recorded);
        }
    }
    
    public class ExerciseRecordedModelRecordMapper implements RecordMapper<Record, ExerciseRecordedModel> {
        @Override
        public ExerciseRecordedModel map(Record rec) {
            ExerciseRecordedModel model = new ExerciseRecordedModel();
            model.setId(rec.getValue(EXERCISES_RECORDED.ID));
            model.setExerciseId(rec.getValue(EXERCISES_RECORDED.EXERCISE_ID));
            model.setRecordedDttm(rec.getValue(EXERCISES_RECORDED.RECORDED_DTTM));
            model.setUserId(rec.getValue(EXERCISES_RECORDED.USER_ID));
            model.setWeight(rec.getValue(EXERCISES_RECORDED.WEIGHT));
            model.setReps(rec.getValue(EXERCISES_RECORDED.REPS));
            model.setNote(rec.getValue(EXERCISES_RECORDED.NOTE));
            
            ExerciseGroupModel gModel = new ExerciseGroupModel();
            gModel.setKeyName(rec.getValue(EXERCISES.EXERCISE_GROUP_KEY_NAME));
            gModel.setDisplayName(rec.getValue(EXERCISE_GROUPS.DISPLAY_NAME));
            
            ExerciseModel eModel = new ExerciseModel();
            eModel.setId(rec.getValue(EXERCISES_RECORDED.EXERCISE_ID));
            eModel.setName(rec.getValue(EXERCISES.NAME));
            eModel.setExerciseGroupKeyName(rec.getValue(EXERCISES.EXERCISE_GROUP_KEY_NAME));
            eModel.setExerciseGroupModel(gModel);
            eModel.setRatioProfileId(rec.getValue(EXERCISES.RATIO_PROFILE_ID));
            
            model.setExerciseModel(eModel);
            
            return model;
        }
    }
    
    public class ExerciseGoalModelRecordMapper implements RecordMapper<Record, ExerciseGoalModel> {
        @Override
        public ExerciseGoalModel map(Record rec) {
            ExerciseGoalModel model = new ExerciseGoalModel();
            model.setId(rec.getValue(EXERCISE_GOALS.ID));
            model.setExerciseId(rec.getValue(EXERCISE_GOALS.EXERCISE_ID));
            model.setUserId(rec.getValue(EXERCISE_GOALS.USER_ID));
            model.setWeight(rec.getValue(EXERCISE_GOALS.WEIGHT));
            model.setReps(rec.getValue(EXERCISE_GOALS.REPS));
            model.setCreatedOnDttm(rec.getValue(EXERCISE_GOALS.CREATED_ON_DTTM));
            
            ExerciseGroupModel gModel = new ExerciseGroupModel();
            gModel.setKeyName(rec.getValue(EXERCISES.EXERCISE_GROUP_KEY_NAME));
            gModel.setDisplayName(rec.getValue(EXERCISE_GROUPS.DISPLAY_NAME));
            
            ExerciseModel eModel = new ExerciseModel();
            eModel.setExerciseGroupModel(gModel);
            eModel.setId(rec.getValue(EXERCISE_GOALS.EXERCISE_ID));
            eModel.setName(rec.getValue(EXERCISES.NAME));
            eModel.setExerciseGroupKeyName(rec.getValue(EXERCISES.EXERCISE_GROUP_KEY_NAME));
            
            model.setExerciseModel(eModel);
            
            return model;
        }
    }
}
