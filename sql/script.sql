create table department
(
    id                bigint auto_increment
        primary key,
    department_name   varchar(255) not null,
    chamber_amount    int          not null,
    vipChamber_amount int          not null,
    constraint department1_department_name_uindex
        unique (department_name)
);

create table chamber
(
    id               bigint auto_increment
        primary key,
    chamber_id       int           not null,
    dept_id          bigint        not null,
    vip              tinyint(1)    not null,
    chamber_capacity int           not null,
    chamber_load     int default 0 not null,
    price_a_day      double        not null,
    constraint chamber_pk
        unique (chamber_id, dept_id),
    constraint chamber_department_id_fk
        foreign key (dept_id) references department (id)
            on update cascade on delete cascade
)
    comment 'палата';

create table discharged_patient
(
    id               bigint auto_increment
        primary key,
    patient_name     varchar(255) not null,
    doctor_name      varchar(255) not null,
    diagnose         varchar(255) not null,
    card_history     text         not null,
    treatment_course varchar(255) not null,
    operation_name   varchar(255) null,
    patient_status   varchar(255) not null,
    enrollment_date  date         not null,
    discharge_date   date         not null
);

create table equipment
(
    id             bigint auto_increment
        primary key,
    equipment_name varchar(255) not null,
    description    text         null,
    constraint equipment_equipment_name_uindex
        unique (equipment_name)
);

create table medicine
(
    id             bigint auto_increment
        primary key,
    medicine_name  varchar(255) not null,
    normal_dose    double       not null,
    critical_dose  double       not null,
    package_amount int(64)      not null,
    price          double       not null,
    constraint medicine_medicine_name_uindex
        unique (medicine_name)
);

create table speciality
(
    id              bigint auto_increment
        primary key,
    speciality_name varchar(255) null,
    constraint speciality_speciality_name_uindex
        unique (speciality_name)
);

create table medical_service
(
    id                   bigint auto_increment
        primary key,
    service_name         varchar(255) not null,
    needed_speciality_id bigint       not null,
    needed_equipment_id  bigint       not null,
    service_cost         double       not null,
    constraint medical_service_service_name_uindex
        unique (service_name),
    constraint medical_service_equipment_id_fk
        foreign key (needed_equipment_id) references equipment (id)
            on update cascade on delete cascade,
    constraint medical_service_speciality_id_fk
        foreign key (needed_speciality_id) references speciality (id)
            on update cascade on delete cascade
);

create table treatment_course
(
    id                    bigint auto_increment
        primary key,
    medicine_id           bigint   not null,
    drug_dose             double   not null,
    reception_description tinytext not null,
    times_a_day           int      not null,
    duration_in_days      int      not null,
    constraint treatment_course_medicine_id_fk
        foreign key (medicine_id) references medicine (id)
            on update cascade on delete cascade
);

create table user
(
    id           bigint auto_increment
        primary key,
    first_name   varchar(50) not null,
    last_name    varchar(50) not null,
    phone_number varchar(50) not null,
    email        varchar(50) not null,
    constraint user_email_uindex
        unique (email),
    constraint user_phone_number_uindex
        unique (phone_number)
);

create table auth_user
(
    id       bigint auto_increment
        primary key,
    login    varchar(255) not null,
    password varchar(255) not null,
    role     varchar(50)  not null,
    user_id  bigint       not null,
    constraint auth_user_login_uindex
        unique (login),
    constraint auth_user_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table card
(
    id            bigint auto_increment
        primary key,
    user_id       bigint       not null,
    history       text         not null,
    address       varchar(255) not null,
    date_of_birth date         not null,
    insurance     tinyint(1)   not null,
    constraint card_user_id_uindex
        unique (user_id),
    constraint card_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table doctor
(
    id           bigint auto_increment
        primary key,
    user_id      bigint     not null,
    dept_id      bigint     not null,
    head_of_dept tinyint(1) not null,
    constraint doctor_user_id_uindex
        unique (user_id),
    constraint doctor_department_id_fk
        foreign key (dept_id) references department (id)
            on update cascade on delete cascade,
    constraint doctor_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table doctor_speciality
(
    id            bigint auto_increment
        primary key,
    doctor_id     bigint not null,
    speciality_id bigint not null,
    constraint doctor_speciality_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade on delete cascade,
    constraint doctor_speciality_speciality_id_fk
        foreign key (speciality_id) references speciality (id)
            on update cascade on delete cascade
);

create table guest_patient
(
    id                 bigint auto_increment
        primary key,
    first_name         varchar(255) null,
    last_name          varchar(255) null,
    phone_number       varchar(255) null,
    email              varchar(255) null,
    doctor_id          bigint       not null,
    coupon_num         int          not null,
    medical_service_id bigint       not null,
    visit_date         timestamp    not null,
    constraint guest_patient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade on delete cascade,
    constraint guest_patient_medical_service_id_fk
        foreign key (medical_service_id) references medical_service (id)
            on update cascade on delete cascade
);

create table operation_service
(
    id             bigint auto_increment
        primary key,
    operation_name varchar(255) not null,
    doctor_id      bigint       not null,
    operation_cost int          null,
    constraint operation_service_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade on delete cascade
);

create table inpatient
(
    id                   bigint auto_increment
        primary key,
    user_id              bigint       not null,
    doctor_id            bigint       not null,
    dept_chamber_id      bigint       not null,
    diagnose             varchar(255) null,
    treatment_course_id  bigint       null,
    operation_service_id bigint       null,
    patient_status       varchar(255) not null,
    enrollment_date      date         not null,
    constraint inpatient_chamber_id_fk
        foreign key (dept_chamber_id) references chamber (id)
            on update cascade on delete cascade,
    constraint inpatient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade on delete cascade,
    constraint inpatient_operation_service_id_fk
        foreign key (operation_service_id) references operation_service (id)
            on update cascade on delete cascade,
    constraint inpatient_treatment_course_id_fk
        foreign key (treatment_course_id) references treatment_course (id)
            on update cascade on delete cascade,
    constraint inpatient_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
)
    comment 'стационарный больной';

create table patient
(
    id                 bigint auto_increment
        primary key,
    user_id            bigint    null,
    doctor_id          bigint    not null,
    coupon_num         int       not null,
    medical_service_id bigint    not null,
    visit_date         timestamp not null,
    constraint patient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade on delete cascade,
    constraint patient_medical_service_id_fk
        foreign key (medical_service_id) references medical_service (id)
            on update cascade on delete cascade,
    constraint patient_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table receipt
(
    user_id            bigint not null
        primary key,
    price_for_chamber  double not null,
    price_for_medicine double not null,
    constraint receipt_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);