/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.service;

import com.performancecarerx.model.ExerciseStandard;

/**
 *
 * @author jberroteran
 */
public interface ExerciseStandardService {
    public ExerciseStandard getStandardForUser(Integer userId);
    public ExerciseStandard updateStandardForUser(ExerciseStandard standard);
}
