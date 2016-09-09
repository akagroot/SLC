/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.repository;

import com.performancecarerx.model.ExerciseStandard;

/**
 *
 * @author jberroteran
 */
public interface ExerciseStandardRepository {
    public ExerciseStandard getStandardForUser(Integer userId);
    public ExerciseStandard addStandard(ExerciseStandard standard);
    public ExerciseStandard updateStandard(ExerciseStandard standard);
}
