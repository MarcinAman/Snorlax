CREATE TABLE periodic_reservation (
    id BIGINT,
    count int,
    user_id BIGINT,
    pool_id varchar(255),
    period_from DATE,
    period_to DATE,
    time_from TIMESTAMP,
    time_to TIMESTAMP,


    PRIMARY KEY (id),
    FOREIGN KEY (pool_id) REFERENCES pool(pool_id),
    FOREIGN KEY (user_id) REFERENCES jhi_user(id)
);

CREATE TABLE reservation_leftout (
    id BIGINT,
    reservation_id BIGINT,
    day DATE,

    PRIMARY KEY (id),
    FOREIGN KEY (reservation_id) REFERENCES periodic_reservation(id),
);
