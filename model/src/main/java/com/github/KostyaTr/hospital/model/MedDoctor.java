package com.github.KostyaTr.hospital.model;

public class MedDoctor {
    private Long doctorId;
    private Long userId;
    private Long deptNum;
    private boolean headOfDept;

    public MedDoctor(Long doctorId, Long userId, Long deptNum, boolean headOfDept) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.deptNum = deptNum;
        this.headOfDept = headOfDept;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getDeptNum() {
        return deptNum;
    }

    public boolean isHeadOfDept() {
        return headOfDept;
    }
}
