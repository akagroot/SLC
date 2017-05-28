/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository.impl;

import com.slc.constants.Constants;
import com.slc.model.UserProfileModel;
import static com.slc.persistence.tables.Users.USERS;
import com.slc.persistence.tables.records.UsersRecord;
import com.slc.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jberroteran
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final DSLContext dslContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public UserRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    @Transactional
    public UserProfileModel insertUser(UserProfileModel userModel) {
        LOGGER.debug("UserRepositoryImpl.insertUser: {}", userModel);
        
        UsersRecord result = dslContext.insertInto(USERS)
                .columns(USERS.EMAIL, 
                        USERS.FIRSTNAME, 
                        USERS.LASTNAME,
                        USERS.ROLE)
                .values(userModel.getEmail(), 
                        userModel.getFirstName(), 
                        userModel.getLastName(),
                        userModel.getRole())
                .returning(USERS.ID)
                .fetchOne();
        
        userModel.setId(result.getValue(USERS.ID));
        return userModel;
    }
    
    @Override
    public UserProfileModel updateUser(UserProfileModel userModel) {
        LOGGER.debug("UserRepositoryImpl.updateUser {}", userModel);
        
        dslContext.update(USERS)
                .set(USERS.FIRSTNAME, userModel.getFirstName())
                .set(USERS.LASTNAME, userModel.getLastName())
                .set(USERS.EMAIL, userModel.getEmail())
                .set(USERS.ROLE, userModel.getRole())
                .where(USERS.ID.eq(userModel.getId()))
                .execute();
        
        return userModel;
    }
    
    @Override
    public Boolean deleteUser(Integer userId) {
        LOGGER.debug("UserRepositoryImpl.deleteUser: {}", userId);
        return dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(userId))
                .execute() == 1;
    }
    
    @Override
    public Boolean setUserRole(Integer userId, String role) {
        LOGGER.debug("UserRepositoryImpl.setUserRole: {} {}", userId, role);
        dslContext.update(USERS)
                .set(USERS.ROLE, role)
                .where(USERS.ID.eq(userId))
                .execute();
        return true;
    }
    
    @Override
    public Integer getNumberOfAdmins() {
        LOGGER.debug("UserRepositoryImpl.getNumberOfAdmins()");
        return dslContext.selectCount()
                .from(USERS)
                .where(USERS.ROLE.eq(Constants.USER_ROLE_ADMIN))
                .fetchOne(0, Integer.class);
    }

    @Override
    public UserProfileModel getUserById(Integer userId) {
        LOGGER.debug("UserRepositoryImpl.getUserById: {}", userId);
        return dslContext.select(
                    USERS.ID, 
                    USERS.FIRSTNAME, 
                    USERS.LASTNAME,
                    USERS.EMAIL, 
                    USERS.ROLE)   
                .from(USERS) 
                .where(USERS.ID.eq(userId))
                .fetchOne(new UserProfileModelRecordMapper());
    }

    @Override
    public UserProfileModel getUserByEmail(String email) {
        LOGGER.debug("UserRepositoryImpl.getUserByEmail: {}", email);
        return dslContext.select(
                    USERS.ID, 
                    USERS.FIRSTNAME, 
                    USERS.LASTNAME,
                    USERS.EMAIL, 
                    USERS.ROLE)   
                .from(USERS) 
                .where(USERS.EMAIL.eq(email))
                .fetchOne(new UserProfileModelRecordMapper());
    }

    @Override
    public List<UserProfileModel> getAllUsers() {
        LOGGER.debug("UserRepositoryImpl.getAllUsers()");
        
        return dslContext.select( 
                    USERS.ID, 
                    USERS.FIRSTNAME, 
                    USERS.LASTNAME,
                    USERS.EMAIL,
                    USERS.ROLE)
                .from(USERS)
                .orderBy(USERS.LASTNAME, USERS.FIRSTNAME)
                .fetch(new UserProfileModelRecordMapper());
    }
    
    @Override
    public List<UserProfileModel> getAllAdmins() {
        LOGGER.debug("UserRepositoryImpl.getAllAdmins()");
        
        return dslContext.select( 
                    USERS.ID, 
                    USERS.FIRSTNAME, 
                    USERS.LASTNAME,
                    USERS.EMAIL,
                    USERS.ROLE)
                .from(USERS)
                .where(USERS.ROLE.eq(Constants.USER_ROLE_ADMIN))
                .orderBy(USERS.LASTNAME, USERS.FIRSTNAME)
                .fetch(new UserProfileModelRecordMapper());
    }
    
    public class UserProfileModelRecordMapper implements RecordMapper<Record, UserProfileModel> {

	@Override
	public UserProfileModel map(Record rec) {
            UserProfileModel model = new UserProfileModel();
            model.setId(rec.getValue(USERS.ID));
            model.setEmail(rec.getValue(USERS.EMAIL));
            model.setRole(rec.getValue(USERS.ROLE));
            model.setFirstName(rec.getValue(USERS.FIRSTNAME));
            model.setLastName(rec.getValue(USERS.LASTNAME));
            return model;
	}
    }
    
}
