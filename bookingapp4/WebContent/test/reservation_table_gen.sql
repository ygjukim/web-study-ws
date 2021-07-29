create table SEAT (
    seat_number NUMBER(5),
    seat_type VARCHAR2(10),
    rsv_seq NUMBER(10),
    constraint pk_seat PRIMARY KEY(seat_number)
);

create table RESERVATIOM (
    rsv_seq NUMBER(10),
    name VARCHAR2(20),
    passwd VARCHAR2(20),
    phone VARCHAR2(20),
    rsv_date DATE,
    seat_number NUMBER(5),
    constraint pk_reservation PRIMARY KEY(rsv_seq)
);

create sequence rsv_seq_gen
start with 1
increment by 1
minvalue 1
nomaxvalue
nocycle
nocache;

select rsv_seq_gen.nextval from dual;

select rsv_seq_gen.currval from dual;

select * from user_sequences;