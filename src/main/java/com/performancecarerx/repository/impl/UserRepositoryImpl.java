/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository.impl;

import com.performancecarerx.constants.Constants;
import com.performancecarerx.model.UserProfileModel;
import static com.performancecarerx.persistence.tables.Users.USERS;
import com.performancecarerx.persistence.tables.records.UsersRecord;
import com.performancecarerx.repository.UserRepository;
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
                        USERS.ROLE, 
                        USERS.COACH_ID)
                .values(userModel.getEmail(), 
                        userModel.getFirstName(), 
                        userModel.getLastName(),
                        userModel.getRole(), 
                        userModel.getCoachId())
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
                .set(USERS.COACH_ID, userModel.getCoachId())
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
    public Boolean setCoach(Integer userId, Integer coachId) {
        LOGGER.debug("UserRepositoryImpl.setCoach: {} {}", userId, coachId);
        dslContext.update(USERS)
                .set(USERS.COACH_ID, coachId)
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
                    USERS.ROLE, 
                    USERS.COACH_ID)   
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
                    USERS.ROLE, 
                    USERS.COACH_ID)   
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
                    USERS.ROLE, 
                    USERS.COACH_ID)
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
                    USERS.ROLE, 
                    USERS.COACH_ID)
                .from(USERS)
                .where(USERS.ROLE.eq(Constants.USER_ROLE_ADMIN))
                .orderBy(USERS.LASTNAME, USERS.FIRSTNAME)
                .fetch(new UserProfileModelRecordMapper());
    }
    
    @Override
    public List<UserProfileModel> getAllUsersAthletes(Integer userId) {
        LOGGER.debug("UserRepositoryImpl.getAllAdmins()");
        
        return dslContext.select( 
                    USERS.ID, 
                    USERS.FIRSTNAME, 
                    USERS.LASTNAME,
                    USERS.EMAIL,
                    USERS.ROLE, 
                    USERS.COACH_ID)
                .from(USERS)
                .where(USERS.COACH_ID.eq(userId))
                .orderBy(USERS.LASTNAME, USERS.FIRSTNAME)
                .fetch(new UserProfileModelRecordMapper());
    }
    
    @Override
    public Integer removeCoachId(Integer coachId) {
        LOGGER.debug("UserRepositoryImpl.removeCoachId()");
        
        return dslContext.update(USERS) 
                .set(USERS.COACH_ID, (Integer)null) 
                .where(USERS.COACH_ID.eq(coachId))
                .execute();
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
            model.setCoachId(rec.getValue(USERS.COACH_ID));
            return model;
	}
    }
    
}
