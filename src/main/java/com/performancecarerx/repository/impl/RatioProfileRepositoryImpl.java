/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository.impl;

import com.performancecarerx.model.RatioProfileModel;
import static com.performancecarerx.persistence.tables.RatioProfiles.RATIO_PROFILES;
import com.performancecarerx.persistence.tables.records.RatioProfilesRecord;
import com.performancecarerx.repository.RatioProfileRepository;
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
public class RatioProfileRepositoryImpl implements RatioProfileRepository {
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(RatioProfileRepositoryImpl.class);

    @Autowired
    public RatioProfileRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    public RatioProfileModel create(RatioProfileModel model) {
        LOGGER.debug("create {}", model);
        RatioProfilesRecord record = dslContext.insertInto(RATIO_PROFILES)
                .columns(RATIO_PROFILES.NAME)
                .values(model.getName())
                .returning(RATIO_PROFILES.ID)
                .fetchOne();
        model.setId(record.getValue(RATIO_PROFILES.ID));
        return model;
    }

    @Override
    public RatioProfileModel update(RatioProfileModel model) {
        LOGGER.debug("update {}", model);
        dslContext.update(RATIO_PROFILES)
                .set(RATIO_PROFILES.NAME, model.getName())
                .where(RATIO_PROFILES.ID.eq(model.getId()))
                .execute();
        return model;
        
    }

    @Override
    public List<RatioProfileModel> getAll() {
        LOGGER.debug("getAll");
        List<RatioProfileModel> models = dslContext.select(
                RATIO_PROFILES.ID, 
                RATIO_PROFILES.NAME)
                .from(RATIO_PROFILES)
                .fetch(new RatioProfileModelRecordMapper());
        return models;
    }

    @Override
    public RatioProfileModel get(Integer id) {
        LOGGER.debug("get {}", id);
        RatioProfileModel model = dslContext.select(
                RATIO_PROFILES.ID, 
                RATIO_PROFILES.NAME)
                .from(RATIO_PROFILES)
                .where(RATIO_PROFILES.ID.eq(id))
                .fetchOne(new RatioProfileModelRecordMapper());
        return model;
    }

    @Override
    public Boolean delete(Integer id) {
        LOGGER.debug("delete {}", id);
        return dslContext.deleteFrom(RATIO_PROFILES)
                .where(RATIO_PROFILES.ID.eq(id))
                .execute() == 1;
    }
    
    public class RatioProfileModelRecordMapper implements RecordMapper<Record, RatioProfileModel> {

	@Override
	public RatioProfileModel map(Record rec) {
            RatioProfileModel model = new RatioProfileModel();
            model.setId(rec.getValue(RATIO_PROFILES.ID));
            model.setName(rec.getValue(RATIO_PROFILES.NAME));
            return model;
	}
    }
}
