# Set database
USE career;

# Create career table
CREATE TABLE career (
                        member_id       BIGINT          PRIMARY KEY,

                        name            VARCHAR(100)    NOT NULL,
                        birth_date      DATE            NOT NULL,
                        gender          VARCHAR(20)     NOT NULL,

                        phone           VARCHAR(20)     NOT NULL,
                        email           VARCHAR(255)    NOT NULL,

                        zip_code        VARCHAR(10)     NOT NULL,
                        road            VARCHAR(255)    NOT NULL,
                        detail          VARCHAR(255),

                        image_path      VARCHAR(500)    NOT NULL,

                        created_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                        updated_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        FOREIGN KEY (member_id) REFERENCES account.member(id)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;