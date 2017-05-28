/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository;

import com.slc.model.RatioProfileValueModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface RatioProfileValueRepository {
    public RatioProfileValueModel create(RatioProfileValueModel model);
    public RatioProfileValueModel update(RatioProfileValueModel model);
    public List<RatioProfileValueModel> getAllForProfile(Integer profileId);
    public RatioProfileValueModel get(Integer id);
    public Boolean delete(Integer id);
}
