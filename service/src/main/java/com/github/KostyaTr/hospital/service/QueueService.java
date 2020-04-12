package com.github.KostyaTr.hospital.service;

import java.util.Date;

public interface QueueService {
    Long getCouponNum(Long doctorId, Date visitDate);
}
