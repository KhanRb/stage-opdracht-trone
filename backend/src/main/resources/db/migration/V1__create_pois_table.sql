CREATE TABLE IF NOT EXISTS pois
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    name              VARCHAR(255)     NOT NULL,
    latitude          DOUBLE PRECISION NOT NULL,
    longitude         DOUBLE PRECISION NOT NULL,
    construction_year INT CHECK (construction_year >= 1),
    create_time        TIMESTAMP        NOT NULL,
    update_time        TIMESTAMP        NOT NULL
);