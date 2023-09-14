CREATE TABLE user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE profile
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    user_id             BIGINT       NOT NULL,
    first_name          VARCHAR(255) NOT NULL,
    last_name           VARCHAR(255) NOT NULL,
    profile_picture_url VARCHAR(255),
    about               TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE

);

CREATE TABLE post_preferences
(
    id                      BIGINT       NOT NULL AUTO_INCREMENT,
    interaction_environment VARCHAR(255) NOT NULL,
    post_category           VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE post
(
    id                  BIGINT    NOT NULL AUTO_INCREMENT,
    user_id             BIGINT    NOT NULL,
    post_preferences_id BIGINT    NOT NULL,
    content_text        TEXT      NOT NULL,
    content_picture_url VARCHAR(255)       DEFAULT NULL,
    creation_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
        FOREIGN KEY (post_preferences_id) REFERENCES post_preferences (id)
        ON DELETE CASCADE

);

CREATE TABLE comment
(
    id               BIGINT    NOT NULL AUTO_INCREMENT,
    user_id          BIGINT    NOT NULL,
    post_id          BIGINT    NOT NULL,
    prior_comment_id BIGINT             DEFAULT NULL,
    content_text     TEXT      NOT NULL,
    creation_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id)
        ON DELETE CASCADE
        FOREIGN KEY (prior_id) REFERENCES comment (id)
        ON DELETE CASCADE
);

CREATE TABLE direct_message
(
    id           BIGINT    NOT NULL AUTO_INCREMENT,
    sender_id    BIGINT    NOT NULL,
    receiver_id  BIGINT    NOT NULL,
    content_text TEXT      NOT NULL,
    sent_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES user (id)
        ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user (id)
        ON DELETE CASCADE
);

CREATE TABLE search_history
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    user_id     BIGINT    NOT NULL,
    search_term TEXT      NOT NULL,
    search_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
);

CREATE TABLE user_interaction
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    user_id          BIGINT       NOT NULL,
    interaction_type VARCHAR(255) NOT NULL,
    reference_type   VARCHAR(255) NOT NULL,
    reference_id     BIGINT       NOT NULL,
    interaction_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE

);





