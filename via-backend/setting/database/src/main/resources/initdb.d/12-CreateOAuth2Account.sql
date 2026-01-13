# Set database
use account;

# Create position table
CREATE TABLE oauth2_account (
                        id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                        member_id               BIGINT          NOT NULL,
                        oauth2_provider         VARCHAR(50)     NOT NULL,
                        email                   VARCHAR(255)    NOT NULL,
                        initial_account         BOOLEAN         NOT NULL,

                        created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                        updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
                        CONSTRAINT uk_oauth2_account UNIQUE (email, oauth2_provider)
) DEFAULT CHARACTER SET utf8mb4;