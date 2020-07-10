package com.github.KostyaTr.hospital.web.rq;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GuestInfoRq {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
