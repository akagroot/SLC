/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository.impl;

import static com.performancecarerx.persistence.tables.Parameters.PARAMETERS;
import com.slc.repository.ParameterRepository;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jberroteran
 */
@Repository
public class ParameterRepositoryImpl implements ParameterRepository {
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterRepositoryImpl.class);
    
    @Autowired
    public ParameterRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public String getParameterValue(String name) {
        return dslContext.select(PARAMETERS.VALUE) 
                .from(PARAMETERS)
                .where(PARAMETERS.NAME.eq(name))
                .fetchOne(PARAMETERS.VALUE);
    }

    @Override
    public void setParameterValue(String name, String value) {
        dslContext.update(PARAMETERS)
                .set(PARAMETERS.VALUE, value)
                .where(PARAMETERS.NAME.eq(name))
                .execute();
    }
    
    @Override
    public void insertParameterValue(String name, String value) {
        dslContext.insertInto(PARAMETERS)
                .columns(PARAMETERS.NAME, PARAMETERS.VALUE) 
                .values(name, value);
    }
    
}
