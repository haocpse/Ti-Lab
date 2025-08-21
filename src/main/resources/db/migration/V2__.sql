CREATE TABLE customer
(
    id            VARCHAR(255) NOT NULL,
    first_name    VARCHAR(100) NULL,
    last_name     VARCHAR(100) NULL,
    point         DOUBLE       NULL,
    membership_id BIGINT       NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE customer_address
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    address             VARCHAR(100)          NULL,
    city                VARCHAR(30)           NULL,
    is_default_shipping BIT(1)                NOT NULL,
    customer_id         VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_customer_address PRIMARY KEY (id)
);

CREATE TABLE membership
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(50)           NOT NULL,
    `description` VARCHAR(200)          NOT NULL,
    min           DOUBLE                NOT NULL,
    max           DOUBLE                NOT NULL,
    created_at    datetime              NOT NULL,
    updated_at    datetime              NOT NULL,
    CONSTRAINT pk_membership PRIMARY KEY (id)
);

CREATE TABLE staff
(
    id             VARCHAR(255) NOT NULL,
    staff_code     VARCHAR(50)  NOT NULL,
    first_name     VARCHAR(100) NOT NULL,
    last_name      VARCHAR(100) NOT NULL,
    business_email VARCHAR(100) NULL,
    `role`         VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_staff PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         VARCHAR(255) NOT NULL,
    username   VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    phone      VARCHAR(15)  NULL,
    `role`     VARCHAR(10)  NOT NULL,
    reason_ban VARCHAR(200) NULL,
    active     BIT(1)       NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE customer_address
    ADD CONSTRAINT FK_CUSTOMER_ADDRESS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_ID FOREIGN KEY (id) REFERENCES user (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_MEMBERSHIP FOREIGN KEY (membership_id) REFERENCES membership (id);

ALTER TABLE staff
    ADD CONSTRAINT FK_STAFF_ON_ID FOREIGN KEY (id) REFERENCES user (id);