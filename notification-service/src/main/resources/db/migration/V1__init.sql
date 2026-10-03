CREATE TABLE message
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    message_title   VARCHAR(255) NULL,
    message_content VARCHAR(255) NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE mock_user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_name     VARCHAR(255) NULL,
    mobile_number VARCHAR(255) NULL,
    email_id      VARCHAR(255) NULL,
    device_id     VARCHAR(255) NULL,
    CONSTRAINT pk_mockuser PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id                                 BIGINT AUTO_INCREMENT NOT NULL,
    user_id                            BIGINT NOT NULL,
    notification_channel               SMALLINT NULL,
    requesting_service_id              BIGINT NOT NULL,
    requesting_service_notification_id BIGINT NOT NULL,
    delivery_status                    SMALLINT NULL,
    message_id                         BIGINT NULL,
    instant                            BIGINT NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_MESSAGE FOREIGN KEY (message_id) REFERENCES message (id);