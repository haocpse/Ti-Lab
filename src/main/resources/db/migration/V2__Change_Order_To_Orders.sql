ALTER TABLE payment
    drop method;

ALTER TABLE payment
    ADD method VARCHAR(255) NULL;

ALTER TABLE payment
    MODIFY method VARCHAR(255) NOT NULL;