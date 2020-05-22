package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.TreatmentCourse;

import java.util.List;

public interface TreatmentCourseDao {

    Long addTreatmentCourse(TreatmentCourse treatmentCourse);

    List<TreatmentCourse> getTreatmentCourses();

    boolean removeTreatmentCourseById(Long treatmentCourseId);

    TreatmentCourse getTreatmentCourseById(Long treatmentCourseId);
}
