CREATE TABLE email_queue
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    template_id   BIGINT                NOT NULL,
    recipient     VARCHAR(255)          NOT NULL,
    subject       VARCHAR(255)          NOT NULL,
    body          TEXT                  NOT NULL,
    status        VARCHAR(20)           NOT NULL,
    retry_count   INT                   NOT NULL,
    error_message TEXT                  NULL,
    sent_at       datetime              NULL,
    CONSTRAINT pk_email_queue PRIMARY KEY (id)
);

CREATE TABLE email_template
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    code       VARCHAR(50)           NOT NULL,
    subject    VARCHAR(255)          NOT NULL,
    body       TEXT                  NOT NULL,
    variables  JSON                  NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_email_template PRIMARY KEY (id)
);

ALTER TABLE bag
    ADD design TEXT NULL;

ALTER TABLE bag
    ADD material TEXT NULL;

ALTER TABLE bag
    ADD story TEXT NULL;

ALTER TABLE customer
    ADD dob datetime NULL;

ALTER TABLE email_template
    ADD CONSTRAINT uc_email_template_code UNIQUE (code);

ALTER TABLE email_queue
    ADD CONSTRAINT FK_EMAIL_QUEUE_ON_TEMPLATE FOREIGN KEY (template_id) REFERENCES email_template (id);