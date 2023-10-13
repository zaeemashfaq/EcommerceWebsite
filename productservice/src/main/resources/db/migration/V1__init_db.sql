CREATE TABLE category
(
    id   BINARY(16) NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE category_id
(
    category_id BINARY(16) NULL,
    id BINARY(16) NOT NULL,
    CONSTRAINT pk_category_id PRIMARY KEY (id)
);

CREATE TABLE price
(
    id       BINARY(16) NOT NULL,
    currency VARCHAR(255) NULL,
    price    DOUBLE NOT NULL,
    CONSTRAINT pk_price PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BINARY(16) NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    image         VARCHAR(255) NULL,
    price         DOUBLE NOT NULL,
    currency      VARCHAR(255) NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE category_id
    ADD CONSTRAINT fk_catid_on_category FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE category_id
    ADD CONSTRAINT fk_catid_on_product FOREIGN KEY (id) REFERENCES product (id);