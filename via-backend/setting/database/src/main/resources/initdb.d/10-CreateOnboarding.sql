# Set database
use account;

# Create onboarding table
CREATE TABLE onboarding (
                            id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                            priority                INT             not NULL,
                            title                   VARCHAR(255)    not NULL,
                            content                 VARCHAR(255)    not NULL,
                            image_path              VARCHAR(255)    not NULL,
                            activated               BOOLEAN                             DEFAULT TRUE,

                            created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                            updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX idx_onboarding_priority ON onboarding(priority);
CREATE INDEX idx_onboarding_activated ON onboarding(activated);

# Insert sample data
INSERT INTO onboarding (priority, title, content, image_path, activated) VALUES
(1, '환영합니다!', '서비스에 오신 것을 환영합니다. 간단한 설정으로 시작해보세요.', 'onboarding/image/welcome.png', true),
(2, '프로필 설정', '개인 프로필을 설정하여 더 개인화된 서비스를 경험하세요.', 'onboarding/image/profile.png', true),
(3, '기본 기능 안내', '주요 기능들을 살펴보고 서비스를 최대한 활용해보세요.', 'onboarding/image/features.png', true),
(4, '알림 설정', '중요한 업데이트를 놓치지 않도록 알림을 설정하세요.', 'onboarding/image/notifications.png', true),
(5, '시작하기', '모든 준비가 완료되었습니다. 이제 서비스를 시작해보세요!', 'onboarding/image/start.png', true),
(6, '고급 기능 (비활성)', '고급 사용자를 위한 추가 기능들을 소개합니다.', 'onboarding/image/advanced.png', false);