create table department
(
    id                 int auto_increment
        primary key,
    department_name    varchar(255) not null,
    chambers_number    int          not null,
    vipChambers_number int          not null,
    constraint department1_department_name_uindex
        unique (department_name)
);

create table chamber
(
    id               int auto_increment
        primary key,
    chamber_id       int           not null,
    dept_id          int           not null,
    vip              tinyint(1)    not null,
    chamber_capacity int           not null,
    chamber_load     int default 0 not null,
    price_a_day      int           not null,
    constraint chamber_department_id_fk
        foreign key (dept_id) references department (id)
            on update cascade
)
    comment 'палата';

create table discharged_patient
(
    id               int(64) auto_increment
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
    id             int auto_increment
        primary key,
    equipment_name varchar(255) not null,
    description    text         null,
    constraint equipment_equipment_name_uindex
        unique (equipment_name)
);

create table medicine
(
    id             int(64) auto_increment
        primary key,
    medicine_name  varchar(255) not null,
    normal_dose    int(64)      not null,
    critical_dose  int(64)      not null,
    package_amount int(64)      not null,
    price          int(64)      not null,
    constraint medicine_medicine_name_uindex
        unique (medicine_name)
);

create table operation_room
(
    id           int auto_increment
        primary key,
    equipment_id int not null,
    constraint operation_room_equipment_id_fk
        foreign key (equipment_id) references equipment (id)
);

create table speciality
(
    id              int(64) auto_increment
        primary key,
    speciality_name varchar(255) null,
    constraint speciality_speciality_name_uindex
        unique (speciality_name)
);

create table medical_service
(
    id                   int(64) auto_increment
        primary key,
    service_name         varchar(255) not null,
    needed_speciality_id int(64)      not null,
    needed_equipment_id  int(64)      null,
    service_cost         double       not null,
    constraint medical_service_service_name_uindex
        unique (service_name),
    constraint medical_service_equipment_id_fk
        foreign key (needed_equipment_id) references equipment (id)
            on update cascade,
    constraint medical_service_speciality_id_fk
        foreign key (needed_speciality_id) references speciality (id)
            on update cascade
);

create table treatment_course
(
    id                    int(64) auto_increment
        primary key,
    medicine_id           int(64)  not null,
    drug_dose             int      not null,
    reception_description tinytext not null,
    times_a_day           int      not null,
    duration_in_days      int      not null,
    constraint treatment_course_medicine_id_fk
        foreign key (medicine_id) references medicine (id)
);

create table user
(
    id           int(64) auto_increment
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
    id       int(64) auto_increment
        primary key,
    login    varchar(255) not null,
    password varchar(255) not null,
    role     varchar(50)  not null,
    user_id  int(64)      not null,
    constraint auth_user_login_uindex
        unique (login),
    constraint auth_user_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table card
(
    id            int(64) auto_increment
        primary key,
    user_id       int(64)      not null,
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
    id           int(64) auto_increment
        primary key,
    user_id      int(64)    not null,
    dept_id      int(64)    not null,
    head_of_dept tinyint(1) not null,
    constraint doctor_user_id_uindex
        unique (user_id),
    constraint doctor_department_id_fk
        foreign key (dept_id) references department (id)
            on update cascade,
    constraint doctor_user_id_fk
        foreign key (user_id) references user (id)
);

create table doctor_speciality
(
    id            int(64) auto_increment
        primary key,
    doctor_id     int(64) not null,
    speciality_id int(64) not null,
    constraint doctor_speciality_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade,
    constraint doctor_speciality_speciality_id_fk
        foreign key (speciality_id) references speciality (id)
            on update cascade
);

create table guest_patient
(
    id                 int(64) auto_increment
        primary key,
    first_name         varchar(255) null,
    last_name          varchar(255) null,
    phone_number       varchar(255) null,
    email              varchar(255) null,
    doctor_id          int(64)      not null,
    coupon_num         int(64)      null,
    medical_service_id int(64)      not null,
    visit_date         timestamp    not null,
    constraint guest_patient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade,
    constraint guest_patient_medical_service_id_fk
        foreign key (medical_service_id) references medical_service (id)
            on update cascade
);

create table operation_service
(
    id             int auto_increment
        primary key,
    operation_name varchar(255) not null,
    doctor_id      int          not null,
    operation_cost int          null,
    constraint operation_service_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade
);

create table inpatient
(
    id                   int(64) auto_increment
        primary key,
    user_id              int(64)      not null,
    doctor_id            int(64)      not null,
    dept_chamber_id      int(64)      not null,
    diagnose             varchar(255) null,
    treatment_course_id  int(64)      null,
    operation_service_id int(64)      null,
    patient_status       varchar(255) not null,
    enrollment_date      date         not null,
    constraint inpatient_chamber_id_fk
        foreign key (dept_chamber_id) references chamber (id)
            on update cascade,
    constraint inpatient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade,
    constraint inpatient_operation_id_fk
        foreign key (operation_service_id) references operation_service (id)
            on update cascade,
    constraint inpatient_treatment_course_id_fk
        foreign key (treatment_course_id) references treatment_course (id)
            on update cascade,
    constraint inpatient_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
)
    comment 'стационарный больной';

create table patient
(
    id                 int(64) auto_increment
        primary key,
    user_id            int(64)   null,
    doctor_id          int(64)   not null,
    coupon_num         int(64)   not null,
    medical_service_id int(64)   not null,
    visit_date         timestamp not null,
    constraint patient_doctor_id_fk
        foreign key (doctor_id) references doctor (id)
            on update cascade,
    constraint patient_medical_service_id_fk
        foreign key (medical_service_id) references medical_service (id)
            on update cascade,
    constraint patient_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table receipt
(
    user_id            int(64) not null
        primary key,
    price_for_chamber  double  not null,
    price_for_medicine double  not null,
    constraint receipt_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade
);

create table scheduled_operation
(
    id               int auto_increment
        primary key,
    operation_id     int          not null,
    patient_id       int          not null,
    operation_room   int          not null,
    operation_time   datetime     not null,
    operation_reason varchar(255) not null,
    constraint operation_inpatient_id_fk
        foreign key (patient_id) references inpatient (id)
            on update cascade on delete cascade,
    constraint operation_operation_room_id_fk
        foreign key (operation_room) references operation_room (id),
    constraint scheduled_operation_operation_service_id_fk
        foreign key (operation_id) references operation_service (id)
);

create table performed_operation
(
    id                     int auto_increment
        primary key,
    scheduled_operation_id int          not null,
    operation_end          datetime     not null,
    patient_status         varchar(255) not null,
    operation_status       varchar(255) not null,
    constraint performed_operation_scheduled_operation_id_fk
        foreign key (scheduled_operation_id) references scheduled_operation (id)
);

