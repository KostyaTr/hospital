package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.TreatmentCourse;

public interface TreatmentCourseDao {
    TreatmentCourse getTreatmentCourseById(Long treatmentCourseId);

    Long addTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean removeTreatmentCourseById(Long treatmentCourseId);

    boolean updateTreatmentCourseById(TreatmentCourse treatmentCourse);
}
