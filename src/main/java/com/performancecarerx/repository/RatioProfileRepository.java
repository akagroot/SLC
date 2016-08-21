/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository;

import com.performancecarerx.model.RatioProfileModel;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public interface RatioProfileRepository {
    public RatioProfileModel create(RatioProfileModel model);
    public RatioProfileModel update(RatioProfileModel model);
    public List<RatioProfileModel> getAll();
    public RatioProfileModel get(Integer id);
    public Boolean delete(Integer id);
}
