package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.TreatmentCourse;

public interface TreatmentCourseDao {

    Long addTreatmentCourse(TreatmentCourse treatmentCourse);

    boolean removeTreatmentCourseById(Long treatmentCourseId);

}
