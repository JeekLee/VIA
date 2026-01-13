# Set database
use career;

CREATE TABLE field (
           id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
           name                    VARCHAR(255)    NOT NULL,
           image_path              VARCHAR(255),
           activated               BOOLEAN         NOT NULL            DEFAULT TRUE,
           created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
           updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_field_name ON field(name);
CREATE INDEX idx_field_activated ON field(activated);

INSERT INTO field (name, image_path, activated) VALUES
('기획자', 'field/image/backend.png', TRUE),
('디자이너', 'field/image/backend.png', TRUE),
('개발자', 'field/image/backend.png', FALSE);