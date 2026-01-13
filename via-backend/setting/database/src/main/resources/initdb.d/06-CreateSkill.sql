# Set database
use career;

CREATE TABLE skill (
                       id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                       name                    VARCHAR(255)    NOT NULL,
                       image_path              VARCHAR(255),
                       activated               BOOLEAN         NOT NULL            DEFAULT TRUE,
                       field_id                BIGINT,
                       created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                       updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (field_id) REFERENCES field(id) ON DELETE SET NULL
);

CREATE INDEX idx_skill_name ON skill(name);
CREATE INDEX idx_skill_activated ON skill(activated);
CREATE INDEX idx_skill_field_id ON skill(field_id);

INSERT INTO skill (name, image_path, activated, field_id) VALUES
('서비스 기획', 'skill/image/service_planning.png', TRUE, 1),
('데이터 분석', 'skill/image/data_analysis.png', TRUE, 1),
('사용자 경험 설계', 'skill/image/ux_design.png', TRUE, 1),
('UI/UX 디자인', 'skill/image/ui_ux.png', TRUE, 2),
('그래픽 디자인', 'skill/image/graphic_design.png', TRUE, 2),
('브랜드 디자인', 'skill/image/brand_design.png', FALSE, 2),
('SpringBoot', 'skill/image/spring_boot.png', TRUE, 3),
('ReactNative', 'skill/image/react_native.png', TRUE, 3),
('LangChain', 'skill/image/lang_chain.png', FALSE, 3);