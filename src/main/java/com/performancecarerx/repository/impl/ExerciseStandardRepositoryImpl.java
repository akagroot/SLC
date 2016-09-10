/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository.impl;

import com.performancecarerx.model.ExerciseRecordedModel;
import static com.performancecarerx.persistence.tables.ExerciseStandards.EXERCISE_STANDARDS;
import com.performancecarerx.repository.ExerciseStandardRepository;
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
public class ExerciseStandardRepositoryImpl implements ExerciseStandardRepository {
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseStandardRepositoryImpl.class);
    
    @Autowired
    public ExerciseStandardRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ExerciseRecordedModel getStandardForUser(Integer userId) {
        LOGGER.debug("getStandardForUser {}", userId);
        Record record = dslContext.select(
                EXERCISE_STANDARDS.EXERCISE_ID, 
                EXERCISE_STANDARDS.USER_ID, 
                EXERCISE_STANDARDS.WEIGHT, 
                EXERCISE_STANDARDS.REPS) 
            .from(EXERCISE_STANDARDS)
            .where(EXERCISE_STANDARDS.USER_ID.eq(userId))
            .fetchOne();
        
        if(record == null) {
            return null;
        }
        
        return record.map(new ExerciseStandardRecordMapper());
    }
    
    public ExerciseRecordedModel addStandard(ExerciseRecordedModel standard) {
        LOGGER.debug("addStandard: {}", standard);
        dslContext.insertInto(EXERCISE_STANDARDS)
                .columns(
                    EXERCISE_STANDARDS.EXERCISE_ID, 
                    EXERCISE_STANDARDS.USER_ID, 
                    EXERCISE_STANDARDS.WEIGHT, 
                    EXERCISE_STANDARDS.REPS) 
                .values(
                    standard.getExerciseId(), 
                    standard.getUserId(), 
                    standard.getWeight(), 
                    standard.getReps())
                .execute();
        
        return standard;
    }
    
    public ExerciseRecordedModel updateStandard(ExerciseRecordedModel standard) {
        LOGGER.debug("updateStandard: {}", standard);
        dslContext.update(EXERCISE_STANDARDS)
                .set(EXERCISE_STANDARDS.EXERCISE_ID, standard.getExerciseId())
                .set(EXERCISE_STANDARDS.REPS, standard.getReps())
                .set(EXERCISE_STANDARDS.WEIGHT, standard.getWeight())
                .where(EXERCISE_STANDARDS.USER_ID.eq(standard.getUserId()))
                .execute();
        
        return standard;
    }
    
    public class ExerciseStandardRecordMapper implements RecordMapper<Record, ExerciseRecordedModel> {
        @Override
        public ExerciseRecordedModel map(Record rec) {
            ExerciseRecordedModel model = new ExerciseRecordedModel();
            model.setUserId(rec.getValue(EXERCISE_STANDARDS.USER_ID));
            model.setExerciseId(rec.getValue(EXERCISE_STANDARDS.EXERCISE_ID));
            model.setReps(rec.getValue(EXERCISE_STANDARDS.REPS));
            model.setWeight(rec.getValue(EXERCISE_STANDARDS.WEIGHT));
            return model;
        }
    }
}
    