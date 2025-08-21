CREATE TABLE flyway_schema_history
(
    installed_rank INT                     NOT NULL,
    version        VARCHAR(50)             NULL,
    `description`  VARCHAR(200)            NOT NULL,
    type           VARCHAR(20)             NOT NULL,
    script         VARCHAR(1000)           NOT NULL,
    checksum       INT                     NULL,
    installed_by   VARCHAR(100)            NOT NULL,
    installed_on   timestamp DEFAULT NOW() NOT NULL,
    execution_time INT                     NOT NULL,
    success        TINYINT(1)              NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (installed_rank)
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);