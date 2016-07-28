/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository.impl;

import com.performancecarerx.model.ExerciseGroupModel;
import com.performancecarerx.model.ExerciseModel;
import com.performancecarerx.model.ExerciseRecordedModel;
import com.performancecarerx.model.dto.AddExerciseModel;
import static com.performancecarerx.persistence.tables.ExerciseGroups.EXERCISE_GROUPS;
import static com.performancecarerx.persistence.tables.Exercises.EXERCISES;
import com.performancecarerx.persistence.tables.ExercisesRecorded;
import static com.performancecarerx.persistence.tables.ExercisesRecorded.EXERCISES_RECORDED;
import static com.performancecarerx.persistence.tables.Users.USERS;
import com.performancecarerx.persistence.tables.records.ExercisesRecord;
import com.performancecarerx.persistence.tables.records.ExercisesRecordedRecord;
import com.performancecarerx.repository.ExerciseRepository;
import java.sql.Timestamp;
import java.util.List;
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
public class ExerciseRepositoryImpl implements ExerciseRepository {
    
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public ExerciseRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    public ExerciseModel addExercise(ExerciseModel model) {
        LOGGER.debug("ExerciseRepositoryImpl.addExercise {}", model);
        ExercisesRecord record = dslContext.insertInto(EXERCISES)
                .columns(EXERCISES.NAME, 
                        EXERCISES.EXERCISE_GROUP_KEY_NAME) 
                .values(model.getName(), 
                        model.getExerciseGroupKeyName())
                .returning(EXERCISES.ID)
                .fetchOne();
        model.setId(record.getValue(EXERCISES.ID));
        return model;
    }
    
    @Override
    public ExerciseModel getExerciseById(Integer exerciseId) {
        LOGGER.debug("ExerciseRepositoryImpl.getExerciseById {}", exerciseId);
        return dslContext.select(
                EXERCISES.ID, 
                EXERCISES.NAME, 
                EXERCISE_GROUPS.KEY_NAME, 
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISES)
                .join(EXERCISE_GROUPS).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME))
                .where(EXERCISES.ID.eq(exerciseId))
                .fetchOne(new ExerciseModelRecordMapper());
    }

    @Override
    public List<ExerciseModel> getExercises() {
        LOGGER.debug("ExerciseRepositoryImpl.getExercises()");
        return dslContext.select(
                EXERCISES.ID, 
                EXERCISES.NAME, 
                EXERCISE_GROUPS.KEY_NAME,
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISES)
                .join(EXERCISE_GROUPS).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME))
                .orderBy(EXERCISE_GROUPS.DISPLAY_NAME, EXERCISES.NAME)
                .fetch(new ExerciseModelRecordMapper());
    }
    
    @Override
    public List<ExerciseModel> getGroupedExercises() {
        LOGGER.debug("ExerciseRepositoryImpl.getGroupedExercises()");
        return dslContext.select(
                EXERCISES.ID, 
                EXERCISES.NAME, 
                EXERCISE_GROUPS.KEY_NAME,
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISE_GROUPS)
                .leftJoin(EXERCISES).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME)
                        .and(EXERCISES.IS_DELETED.eq(false)))
                .orderBy(EXERCISE_GROUPS.DISPLAY_NAME, EXERCISES.NAME)
                .fetch(new ExerciseModelRecordMapper());
    }

    @Override
    public List<ExerciseRecordedModel> getExercisesForUser(String email) {
        LOGGER.debug("ExerciseRepositoryImpl.getExercisesForUser() {}", email);
        return dslContext.select(
                EXERCISES_RECORDED.ID, 
                EXERCISES_RECORDED.EXERCISE_ID, 
                EXERCISES.NAME, 
                EXERCISES.EXERCISE_GROUP_KEY_NAME,
                EXERCISE_GROUPS.DISPLAY_NAME,
                EXERCISES_RECORDED.RECORDED_DTTM, 
                EXERCISES_RECORDED.WEIGHT, 
                EXERCISES_RECORDED.REPS,
                EXERCISES_RECORDED.RECORDED_DTTM, 
                EXERCISES_RECORDED.NOTE,
                EXERCISES_RECORDED.USER_ID) 
                .from(EXERCISES_RECORDED)
                .join(EXERCISES).on(EXERCISES_RECORDED.EXERCISE_ID.eq(EXERCISES.ID))
                .join(EXERCISE_GROUPS).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME))
                .join(USERS).on(EXERCISES_RECORDED.USER_ID.eq(USERS.ID))
                .where(USERS.EMAIL.eq(email))
                .orderBy(EXERCISES_RECORDED.RECORDED_DTTM.desc())
                .fetch(new ExerciseRecordedModelRecordMapper());
    }
    
    @Override
    public ExerciseRecordedModel getExerciseRecorded(Integer id) {
        LOGGER.debug("ExerciseRepositoryImpl.getExerciseRecorded() {}", id);
        return dslContext.select(
                EXERCISES_RECORDED.ID, 
                EXERCISES_RECORDED.EXERCISE_ID, 
                EXERCISES.NAME, 
                EXERCISES.EXERCISE_GROUP_KEY_NAME,
                EXERCISE_GROUPS.DISPLAY_NAME,
                EXERCISES_RECORDED.RECORDED_DTTM, 
                EXERCISES_RECORDED.WEIGHT, 
                EXERCISES_RECORDED.REPS,
                EXERCISES_RECORDED.NOTE,
                EXERCISES_RECORDED.USER_ID) 
                .from(EXERCISES_RECORDED)
                .join(EXERCISES).on(EXERCISES_RECORDED.EXERCISE_ID.eq(EXERCISES.ID))
                .join(EXERCISE_GROUPS).on(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(EXERCISE_GROUPS.KEY_NAME))
                .where(EXERCISES_RECORDED.USER_ID.eq(id))
                .orderBy(EXERCISES_RECORDED.RECORDED_DTTM)
                .fetchOne(new ExerciseRecordedModelRecordMapper());
    }
    
    @Override
    public Boolean exerciseRecordedBelongsToUser(Integer userId, Integer exerciseRecordedId) {
        LOGGER.debug("ExerciseRepositoryImpl.exerciseRecordedBelongsToUser {} {}", userId, exerciseRecordedId);
        return (dslContext.selectCount()
                .from(EXERCISES_RECORDED)
                .where(EXERCISES_RECORDED.USER_ID.eq(userId)) 
                .and(EXERCISES_RECORDED.ID.eq(exerciseRecordedId)) 
                .fetchOne(0, int.class) == 1);
    }
    
    @Override
    public Boolean deleteExerciseRecorded(Integer exerciseRecordedId) {
        LOGGER.debug("ExerciseRepositoryImpl.deleteExerciseRecorded {}", exerciseRecordedId);
        return (dslContext.deleteFrom(EXERCISES_RECORDED)
                .where(EXERCISES_RECORDED.ID.eq(exerciseRecordedId))
                .execute() == 1);
    }
    
    @Override
    public Integer countExercisesForGroupKey(String keyName) {
        LOGGER.debug("ExerciseRepositoryImpl.countExercisesForKey {}", keyName);
        return (dslContext.selectCount()
                .from(EXERCISES)
                .where(EXERCISES.EXERCISE_GROUP_KEY_NAME.eq(keyName))
                .fetchOne(0, int.class));
    }

    @Override
    public Integer addExerciseToUser(AddExerciseModel exercise) {
        LOGGER.debug("ExerciseRepositoryImpl.addExerciseToUser: {}", exercise);
        Timestamp date = new Timestamp(exercise.getDate().getTime());
        
        ExercisesRecordedRecord record = dslContext.insertInto(EXERCISES_RECORDED)
                .columns(EXERCISES_RECORDED.WEIGHT, 
                        EXERCISES_RECORDED.REPS, 
                        EXERCISES_RECORDED.EXERCISE_ID, 
                        EXERCISES_RECORDED.USER_ID, 
                        EXERCISES_RECORDED.RECORDED_DTTM, 
                        EXERCISES_RECORDED.NOTE)
                .values(exercise.getWeight(), 
                        exercise.getReps(), 
                        exercise.getExerciseId(), 
                        exercise.getUserId(), 
                        date, 
                        exercise.getNote())
                .returning(EXERCISES_RECORDED.ID)
                .fetchOne();
        
        return record.getValue(EXERCISES_RECORDED.ID);
    }

    @Override
    public Boolean deleteExercise(Integer id) {
        LOGGER.debug("ExerciseRepositoryImpl.deleteExercise {}", id);
        int numberOfExercises = dslContext.selectCount()
                .from(EXERCISES_RECORDED)
                .where(EXERCISES_RECORDED.EXERCISE_ID.eq(id))
                .fetchOne(0, int.class);
        
        ExercisesRecord record = null;
        
        if(numberOfExercises == 0) {
            record = dslContext.deleteFrom(EXERCISES)
                    .where(EXERCISES.ID.eq(id))
                    .returning(EXERCISES.ID)
                    .fetchOne();
        } else {
            record = dslContext.update(EXERCISES)
                .set(EXERCISES.IS_DELETED, true)
                .where(EXERCISES.ID.eq(id))
                .returning(EXERCISES.ID)
                .fetchOne();
        }
        
        return record != null;
    }
    
    @Override
    public ExerciseModel updateExercise(ExerciseModel model) {
        LOGGER.debug("ExerciseRepositoryImpl.updateExercise {}", model);
        dslContext.update(EXERCISES)
                .set(EXERCISES.NAME, model.getName())
                .set(EXERCISES.EXERCISE_GROUP_KEY_NAME, model.getExerciseGroupKeyName())
                .where(EXERCISES.ID.eq(model.getId()))
                .execute();
        return model;
    }
    
    public class ExerciseModelRecordMapper implements RecordMapper<Record, ExerciseModel> {
        @Override
	public ExerciseModel map(Record rec) {
            ExerciseModel model = new ExerciseModel();
            model.setId(rec.getValue(EXERCISES.ID));
            model.setName(rec.getValue(EXERCISES.NAME));
            model.setExerciseGroupKeyName(rec.getValue(EXERCISE_GROUPS.KEY_NAME));
            
            ExerciseGroupModel groupModel = new ExerciseGroupModel();
            groupModel.setKeyName(rec.getValue(EXERCISE_GROUPS.KEY_NAME));
            groupModel.setDisplayName(rec.getValue(EXERCISE_GROUPS.DISPLAY_NAME));
            model.setExerciseGroupModel(groupModel);
            
            return model;
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
            
            model.setExerciseModel(eModel);
            
            return model;
        }
    }
}
