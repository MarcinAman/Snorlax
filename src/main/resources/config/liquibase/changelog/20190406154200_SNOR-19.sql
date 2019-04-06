
CREATE TABLE pool (
    pool_id varchar(255),
    user_id BIGINT,
    display_name varchar(255),
    maximum_count int,
    enabled boolean,

    PRIMARY KEY (pool_id),
    FOREIGN KEY (user_id) REFERENCES jhi_user(id)
);

CREATE TABLE tools (
    id BIGINT,
    pool_id varchar(255),
    name varchar(255),
    version varchar(255),

    PRIMARY KEY (id),
    FOREIGN KEY (pool_id) REFERENCES pool(pool_id)
);

CREATE TABLE reservation (
    id BIGINT,
    count int,
    user_id BIGINT,
    pool_id varchar(255),

    PRIMARY KEY (id),
    FOREIGN KEY (pool_id) REFERENCES pool(pool_id),
    FOREIGN KEY (user_id) REFERENCES jhi_user(id)
);
