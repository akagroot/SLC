/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.RatioProfileModel;
import com.performancecarerx.model.RatioProfileValueModel;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jberroteran
 */
public interface RatioProfileService {
    public Map<Integer, RatioProfileModel> getMappedRatioProfiles(boolean withValues);
    public List<RatioProfileModel> getRatioProfiles(boolean withValues);
    public Boolean deleteRatioProfile(Integer id);
    public RatioProfileModel createRatioProfile(RatioProfileModel model);
    public RatioProfileModel updateRatioProfile(RatioProfileModel model);
    public RatioProfileModel getRatioProfile(Integer id);
    
    public RatioProfileValueModel createRatioProfileValue(RatioProfileValueModel model);
    public RatioProfileValueModel updateRatioProfileValue(RatioProfileValueModel model);
    public Boolean deleteRatioProfileValue(Integer id);
}
