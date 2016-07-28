/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.model.dto;

import com.performancecarerx.model.ExerciseRecordedModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jberroteran
 */
public class ExercisesByDate {
    private Date date;
    private List<ExerciseRecordedModel> exercises;
    public ExercisesByDate(Date date, List<ExerciseRecordedModel> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    public Date getDate() {
        return date;
    }
    public List<ExerciseRecordedModel> getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExercisesByDate [")
                .append("date=").append(date)
                .append(", exercises=").append(exercises)
                .append("]");
        return builder.toString();
    }
}