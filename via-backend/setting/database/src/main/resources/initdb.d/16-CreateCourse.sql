# Set database
USE content;

# Create course table
CREATE TABLE course (
        id              BIGINT          PRIMARY KEY AUTO_INCREMENT,

        url             VARCHAR(500)    NOT NULL     UNIQUE,
        platform        VARCHAR(50)     NOT NULL,
        title           VARCHAR(255)    NOT NULL,
        instructor      VARCHAR(255)    NOT NULL,
        description     TEXT            NOT NULL,
        difficulty      VARCHAR(50)     NOT NULL,
        rating          FLOAT           NOT NULL,

        created_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
        updated_at      DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Create course_skill mapping table
CREATE TABLE course_skill (
        course_id       BIGINT          NOT NULL,
        skill_id        BIGINT          NOT NULL,

        PRIMARY KEY (course_id, skill_id),
        FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
        FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;