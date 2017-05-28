/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository.impl;

import com.slc.model.RatioProfileValueModel;
import static com.performancecarerx.persistence.tables.RatioProfileValues.RATIO_PROFILE_VALUES;
import com.performancecarerx.persistence.tables.records.RatioProfileValuesRecord;
import com.slc.repository.RatioProfileValueRepository;
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
public class RatioProfileValueRepositoryImpl implements RatioProfileValueRepository {
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(RatioProfileValueRepositoryImpl.class);

    @Autowired
    public RatioProfileValueRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    public RatioProfileValueModel create(RatioProfileValueModel model) {
        LOGGER.debug("create {}", model);
        RatioProfileValuesRecord record = dslContext.insertInto(RATIO_PROFILE_VALUES)
                .columns(RATIO_PROFILE_VALUES.RATIO_PROFILE_ID, 
                        RATIO_PROFILE_VALUES.REPS, 
                        RATIO_PROFILE_VALUES.MULTIPLIER)
                .values(model.getRatioProfileId(), 
                        model.getReps(), 
                        model.getMultiplier())
                .returning(RATIO_PROFILE_VALUES.ID)
                .fetchOne();
        model.setId(record.getValue(RATIO_PROFILE_VALUES.ID));
        return model;
    }

    @Override
    public RatioProfileValueModel update(RatioProfileValueModel model) {
        LOGGER.debug("update {}", model);
        dslContext.update(RATIO_PROFILE_VALUES)
                .set(RATIO_PROFILE_VALUES.REPS, model.getReps())
                .set(RATIO_PROFILE_VALUES.MULTIPLIER, model.getMultiplier())
                .where(RATIO_PROFILE_VALUES.ID.eq(model.getId()))
                .execute();
        return model;
        
    }

    @Override
    public List<RatioProfileValueModel> getAllForProfile(Integer profileId) {
        LOGGER.debug("getAll");
        List<RatioProfileValueModel> models = dslContext.select(
                RATIO_PROFILE_VALUES.ID, 
                RATIO_PROFILE_VALUES.RATIO_PROFILE_ID, 
                RATIO_PROFILE_VALUES.REPS, 
                RATIO_PROFILE_VALUES.MULTIPLIER)
                .from(RATIO_PROFILE_VALUES)
                .where(RATIO_PROFILE_VALUES.RATIO_PROFILE_ID.eq(profileId))
                .orderBy(RATIO_PROFILE_VALUES.REPS)
                .fetch(new RatioProfileValueModelRecordMapper());
        return models;
    }

    @Override
    public RatioProfileValueModel get(Integer id) {
        LOGGER.debug("get {}", id);
        RatioProfileValueModel model = dslContext.select(
                RATIO_PROFILE_VALUES.ID, 
                RATIO_PROFILE_VALUES.RATIO_PROFILE_ID, 
                RATIO_PROFILE_VALUES.REPS,
                RATIO_PROFILE_VALUES.MULTIPLIER)
                .from(RATIO_PROFILE_VALUES)
                .where(RATIO_PROFILE_VALUES.ID.eq(id))
                .fetchOne(new RatioProfileValueModelRecordMapper());
        return model;
    }

    @Override
    public Boolean delete(Integer id) {
        LOGGER.debug("delete {}", id);
        return dslContext.deleteFrom(RATIO_PROFILE_VALUES)
                .where(RATIO_PROFILE_VALUES.ID.eq(id))
                .execute() == 1;
    }
    
    public class RatioProfileValueModelRecordMapper implements RecordMapper<Record, RatioProfileValueModel> {

	@Override
	public RatioProfileValueModel map(Record rec) {
            RatioProfileValueModel model = new RatioProfileValueModel();
            model.setId(rec.getValue(RATIO_PROFILE_VALUES.ID));
            model.setRatioProfileId(rec.getValue(RATIO_PROFILE_VALUES.RATIO_PROFILE_ID));
            model.setReps(rec.getValue(RATIO_PROFILE_VALUES.REPS));
            model.setMultiplier(rec.getValue(RATIO_PROFILE_VALUES.MULTIPLIER));
            return model;
	}
    }
}
