CREATE TABLE bag
(
    id            VARCHAR(255) NOT NULL,
    name          VARCHAR(200) NOT NULL,
    price         DOUBLE       NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    author        VARCHAR(30)  NOT NULL,
    quantity      INT          NOT NULL,
    status        VARCHAR(255) NOT NULL,
    length        DOUBLE       NOT NULL,
    weight        DOUBLE       NOT NULL,
    type          VARCHAR(255) NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    CONSTRAINT pk_bag PRIMARY KEY (id)
);

CREATE TABLE bag_img
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    url        VARCHAR(255)          NOT NULL,
    main       BIT(1)                NOT NULL,
    bag_id     VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_bag_img PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id              VARCHAR(255) NOT NULL,
    number_of_bag   INT          NOT NULL,
    sub_total       DOUBLE       NOT NULL,
    fee_of_delivery INT          NOT NULL,
    total           DOUBLE       NOT NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_detail
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    bag_id      VARCHAR(255)          NOT NULL,
    quantity    INT                   NOT NULL,
    total_price DOUBLE                NOT NULL,
    cart_id     VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_cart_detail PRIMARY KEY (id)
);

CREATE TABLE coupon
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    code          VARCHAR(50)           NOT NULL,
    `description` VARCHAR(100)          NOT NULL,
    discount      DOUBLE                NOT NULL,
    created_at    datetime              NOT NULL,
    updated_at    datetime              NOT NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
);

CREATE TABLE coupon_usage
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    customer_id VARCHAR(255)          NOT NULL,
    coupon_id   BIGINT                NOT NULL,
    CONSTRAINT pk_coupon_usage PRIMARY KEY (id)
);

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

CREATE TABLE `order`
(
    id              VARCHAR(255) NOT NULL,
    number_of_bag   INT          NOT NULL,
    sub_total       DOUBLE       NOT NULL,
    fee_of_delivery INT          NOT NULL,
    total           DOUBLE       NOT NULL,
    method          VARCHAR(255) NOT NULL,
    status          VARCHAR(255) NOT NULL,
    coupon_id       BIGINT       NOT NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_detail
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    bag_id      VARCHAR(255)          NOT NULL,
    quantity    INT                   NOT NULL,
    total_price DOUBLE                NOT NULL,
    order_id    VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_order_detail PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id         VARCHAR(255) NOT NULL,
    order_id   VARCHAR(255) NOT NULL,
    total      DOUBLE       NOT NULL,
    status     VARCHAR(255) NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
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
    email      VARCHAR(100) NULL,
    password   VARCHAR(100) NULL,
    phone      VARCHAR(15)  NULL,
    `role`     VARCHAR(10)  NOT NULL,
    reason_ban VARCHAR(200) NULL,
    active     BIT(1)       NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE cart_detail
    ADD CONSTRAINT uc_cart_detail_bag UNIQUE (bag_id);

ALTER TABLE `order`
    ADD CONSTRAINT uc_order_coupon UNIQUE (coupon_id);

ALTER TABLE order_detail
    ADD CONSTRAINT uc_order_detail_bag UNIQUE (bag_id);

ALTER TABLE payment
    ADD CONSTRAINT uc_payment_order UNIQUE (order_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE bag_img
    ADD CONSTRAINT FK_BAG_IMG_ON_BAG FOREIGN KEY (bag_id) REFERENCES bag (id);

ALTER TABLE cart_detail
    ADD CONSTRAINT FK_CART_DETAIL_ON_BAG FOREIGN KEY (bag_id) REFERENCES bag (id);

ALTER TABLE cart_detail
    ADD CONSTRAINT FK_CART_DETAIL_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE coupon_usage
    ADD CONSTRAINT FK_COUPON_USAGE_ON_COUPON FOREIGN KEY (coupon_id) REFERENCES coupon (id);

ALTER TABLE coupon_usage
    ADD CONSTRAINT FK_COUPON_USAGE_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer_address
    ADD CONSTRAINT FK_CUSTOMER_ADDRESS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_ID FOREIGN KEY (id) REFERENCES user (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_MEMBERSHIP FOREIGN KEY (membership_id) REFERENCES membership (id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_BAG FOREIGN KEY (bag_id) REFERENCES bag (id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_COUPON FOREIGN KEY (coupon_id) REFERENCES coupon (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE staff
    ADD CONSTRAINT FK_STAFF_ON_ID FOREIGN KEY (id) REFERENCES user (id);