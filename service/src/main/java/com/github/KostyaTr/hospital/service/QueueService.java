package com.github.KostyaTr.hospital.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface QueueService {
    int getCouponNum(Long doctorId, LocalDate visitDate);

    List<LocalDate> getAvailableDays(Long doctorId);

    LocalDateTime getVisitTime(Long doctorId, LocalDate day);
}
