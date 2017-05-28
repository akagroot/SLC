/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.repository;

import com.slc.model.ExerciseRecordedModel;

/**
 *
 * @author jberroteran
 */
public interface ExerciseStandardRepository {
    public ExerciseRecordedModel getStandardForUser(Integer userId);
    public ExerciseRecordedModel addStandard(ExerciseRecordedModel standard);
    public ExerciseRecordedModel updateStandard(ExerciseRecordedModel standard);
}
