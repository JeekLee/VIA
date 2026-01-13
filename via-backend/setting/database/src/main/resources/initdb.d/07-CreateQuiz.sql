# Set database
use career;

CREATE TABLE quiz (
                      id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                      description             TEXT            NOT NULL,
                      question_count          INTEGER         NOT NULL,
                      estimated_time          INTEGER         NOT NULL,
                      activated               BOOLEAN         NOT NULL            DEFAULT TRUE,
                      skill_id                BIGINT          UNIQUE              NOT NULL,
                      created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                      updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

CREATE INDEX idx_quiz_activated ON quiz(activated);
CREATE INDEX idx_quiz_skill_id ON quiz(skill_id);

INSERT INTO quiz (description, question_count, estimated_time, activated, skill_id) VALUES
('서비스 기획에 필요한 기본 지식과 방법론을 확인하는 퀴즈입니다.', 20, 15, TRUE, 1),
('데이터 분석의 핵심 개념과 도구 사용법에 대한 퀴즈입니다.', 20, 18, TRUE, 2),
('UX 설계 원칙과 사용자 중심 디자인에 대한 퀴즈입니다.', 20, 20, TRUE, 3),
('UI/UX 디자인의 기본 원리와 실무 적용에 대한 퀴즈입니다.', 20, 16, TRUE, 4),
('그래픽 디자인 도구와 시각적 표현 기법에 대한 퀴즈입니다.', 15, 12, FALSE, 5),
('Spring Boot 프레임워크의 핵심 기능과 개발 패턴에 대한 퀴즈입니다.', 25, 22, FALSE, 7),
('React Native를 활용한 모바일 앱 개발에 대한 퀴즈입니다.', 18, 20, FALSE, 8);