create table vm_user_usage_statistics (
    login varchar(255),
    times_used int,

    PRIMARY KEY (login)
);

create table vm_usage_statistics (
    pool_id varchar(255),
    DISPLAY_NAME varchar(255),
    times_used int,

    PRIMARY KEY (pool_id)
);
