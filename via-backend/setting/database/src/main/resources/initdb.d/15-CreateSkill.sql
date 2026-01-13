# Set database
USE content;

# Create skill table
CREATE TABLE skill (
       id              BIGINT          PRIMARY KEY AUTO_INCREMENT,

       name            VARCHAR(255)    NOT NULL     unique,
       aliases         JSON,

       created_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
       updated_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;