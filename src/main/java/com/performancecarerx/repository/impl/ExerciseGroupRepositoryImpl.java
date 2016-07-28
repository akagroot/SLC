/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository.impl;

import com.performancecarerx.model.ExerciseGroupModel;
import static com.performancecarerx.persistence.tables.ExerciseGroups.EXERCISE_GROUPS;
import com.performancecarerx.repository.ExerciseGroupRepository;
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
public class ExerciseGroupRepositoryImpl implements ExerciseGroupRepository {
    
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseGroupRepositoryImpl.class);

    @Autowired
    public ExerciseGroupRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    public ExerciseGroupModel getExerciseGroup(String keyname) {
        LOGGER.debug("ExerciseGroupRepositoryImpl.getExerciseGroup() {}", keyname);
        return dslContext.select(
                EXERCISE_GROUPS.KEY_NAME, 
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISE_GROUPS)
                .where(EXERCISE_GROUPS.KEY_NAME.eq(keyname))
                .fetchOne(new ExerciseGroupModelRecordMapper());
    }

    @Override
    public List<ExerciseGroupModel> getExerciseGroups() {
        LOGGER.debug("ExerciseGroupRepositoryImpl.getExerciseGroups()");
        return dslContext.select(
                EXERCISE_GROUPS.KEY_NAME, 
                EXERCISE_GROUPS.DISPLAY_NAME)
                .from(EXERCISE_GROUPS)
                .orderBy(EXERCISE_GROUPS.DISPLAY_NAME)
                .fetch(new ExerciseGroupModelRecordMapper());
    }
    
    @Override
    public Boolean isKeyNameUnique(String keyName) {
        LOGGER.debug("ExerciseGroupRepositoryImpl.isKeyNameUnique {}", keyName);
        return (dslContext.selectCount()
                .from(EXERCISE_GROUPS)
                .where(EXERCISE_GROUPS.KEY_NAME.eq(keyName)) 
                .fetchOne(0, int.class) == 0);
    }

    @Override
    public ExerciseGroupModel addExerciseGroup(ExerciseGroupModel model) {
        LOGGER.debug("ExerciseGroupRepositoryImpl.addExerciseGroup {}", model);
        dslContext.insertInto(EXERCISE_GROUPS)
                .columns(EXERCISE_GROUPS.KEY_NAME, 
                        EXERCISE_GROUPS.DISPLAY_NAME)
                .values(model.getKeyName(), 
                        model.getDisplayName())
                .execute();
        return model;
    }
    
    @Override
    public ExerciseGroupModel updateExerciseGroup(ExerciseGroupModel model) {
        LOGGER.debug("ExerciseGroupRepositoryImpl.updateExerciseGroup {}", model);
        dslContext.update(EXERCISE_GROUPS)
                .set(EXERCISE_GROUPS.DISPLAY_NAME, model.getDisplayName())
                .where(EXERCISE_GROUPS.KEY_NAME.eq(model.getKeyName()))
                .execute();
        
        return model;
    }

    @Override
    public Boolean deleteExerciseGroup(String exerciseGroupKey) {
        LOGGER.debug("ExerciseGroupRepositoryImpl.deleteExerciseGroup {}", exerciseGroupKey);
        return (dslContext.deleteFrom(EXERCISE_GROUPS)
                .where(EXERCISE_GROUPS.KEY_NAME.eq(exerciseGroupKey))
                .execute() == 1);
    }
    
    public class ExerciseGroupModelRecordMapper implements RecordMapper<Record, ExerciseGroupModel> {
        @Override
        public ExerciseGroupModel map(Record rec) {
            ExerciseGroupModel model = new ExerciseGroupModel();
            model.setKeyName(rec.getValue(EXERCISE_GROUPS.KEY_NAME));
            model.setDisplayName(rec.getValue(EXERCISE_GROUPS.DISPLAY_NAME));
            return model;
        }
    }
}
