/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service.impl;

import com.performancecarerx.model.RatioProfileModel;
import com.performancecarerx.model.RatioProfileValueModel;
import com.performancecarerx.repository.RatioProfileRepository;
import com.performancecarerx.repository.RatioProfileValueRepository;
import com.performancecarerx.service.RatioProfileService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jberroteran
 */
@Component("ratioProfileService")
public class RatioProfileServiceImpl implements RatioProfileService {
    
    @Autowired
    private RatioProfileRepository ratioProfileRepository;
    
    @Autowired
    private RatioProfileValueRepository ratioValueRepository;
    
    @Override
    public Map<Integer, RatioProfileModel> getMappedRatioProfiles(boolean withValues) {
        List<RatioProfileModel> profiles = getRatioProfiles(withValues);
        
        Map<Integer, RatioProfileModel> mapped = new HashMap();
        
        profiles.stream().forEach((RatioProfileModel t) -> {
            mapped.put(t.getId(), t);
        });
        
        return mapped;
    }

    @Override
    public List<RatioProfileModel> getRatioProfiles(boolean withValues) {
        if(!withValues) {
            return ratioProfileRepository.getAll();
        }
        
        List<RatioProfileModel> profiles = ratioProfileRepository.getAll();
        List<RatioProfileValueModel> values;
        for(RatioProfileModel m : profiles) {
            values = ratioValueRepository.getAllForProfile(m.getId());
            m.setValues(values);
        }
        return profiles;
    }

    @Override
    public Boolean deleteRatioProfile(Integer id) {
        return ratioProfileRepository.delete(id);
    }

    @Override
    public RatioProfileModel createRatioProfile(RatioProfileModel model) {
        return ratioProfileRepository.create(model);
    }

    @Override
    public RatioProfileModel updateRatioProfile(RatioProfileModel model) {
        return ratioProfileRepository.update(model);
    }

    @Override
    public RatioProfileModel getRatioProfile(Integer id) {
        RatioProfileModel profile = ratioProfileRepository.get(id);
        List<RatioProfileValueModel> values = ratioValueRepository.getAllForProfile(id);
        profile.setValues(values);
        return profile;
    }

    @Override
    public RatioProfileValueModel createRatioProfileValue(RatioProfileValueModel model) {
        return ratioValueRepository.create(model);
    }

    @Override
    public RatioProfileValueModel updateRatioProfileValue(RatioProfileValueModel model) {
        return ratioValueRepository.update(model);
    }

    @Override
    public Boolean deleteRatioProfileValue(Integer id) {
        return ratioValueRepository.delete(id);
    }
    
}
