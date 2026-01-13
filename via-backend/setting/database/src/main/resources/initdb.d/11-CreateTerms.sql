# Set database
use account;

# Create terms_category table
CREATE TABLE terms_category (
                                id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                                required                BOOLEAN         NOT NULL            COMMENT '필수 동의 여부',
                                name                    VARCHAR(100)    NOT NULL            COMMENT '약관 카테고리명',
                                priority                INT             NOT NULL            COMMENT '우선순위',

                                created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                                updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX idx_terms_category_priority ON terms_category(priority);
CREATE INDEX idx_terms_category_required ON terms_category(required);

# Create terms table
CREATE TABLE terms (
                       id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                       major_version           INT             NOT NULL            COMMENT '주 버전',
                       minor_version           INT             NOT NULL            COMMENT '부 버전',
                       title                   VARCHAR(255)    NOT NULL            COMMENT '약관 제목',
                       content                 TEXT            NOT NULL            COMMENT '약관 내용',
                       effective_at            DATETIME        NOT NULL            COMMENT '시행일시',
                       terms_category_id       BIGINT          NOT NULL            COMMENT '약관 카테고리 ID',

                       created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                       updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                       FOREIGN KEY (terms_category_id) REFERENCES terms_category(id)
) DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX idx_terms_category_id ON terms(terms_category_id);
CREATE INDEX idx_terms_effective_at ON terms(effective_at);
CREATE INDEX idx_terms_version ON terms(major_version, minor_version);

# Create terms_consent table
CREATE TABLE terms_consent (
    id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,

    terms_id                BIGINT          NOT NULL            ,
    member_id               BIGINT          NOT NULL            ,
    withdrawn               boolean         NOT NULL            DEFAULT false,

    created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
    updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (terms_id) REFERENCES terms(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
) DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX idx_terms_consent_member_id ON terms_consent(member_id);

# Insert sample data for terms_category
INSERT INTO terms_category (required, name, priority) VALUES
(true, '서비스 이용약관', 1),
(true, '개인정보 수집 및 이용 동의', 2),
(false, '마케팅 정보 수신 동의', 3),
(false, '위치정보 이용 동의', 4),
(true, '만 14세 이상 확인', 5);

# Insert sample data for terms
INSERT INTO terms (major_version, minor_version, title, content, effective_at, terms_category_id) VALUES
-- 서비스 이용약관
(1, 0, '서비스 이용약관',
'제1조 (목적)
본 약관은 회사가 제공하는 서비스의 이용조건 및 절차, 회사와 회원 간의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.

제2조 (정의)
1. "서비스"란 회사가 제공하는 모든 서비스를 의미합니다.
2. "회원"이란 본 약관에 따라 회사와 이용계약을 체결하고 회사가 제공하는 서비스를 이용하는 자를 의미합니다.

제3조 (약관의 효력 및 변경)
1. 본 약관은 서비스 화면에 게시하거나 기타의 방법으로 회원에게 공지함으로써 효력을 발생합니다.
2. 회사는 필요 시 본 약관을 변경할 수 있으며, 변경된 약관은 공지 후 효력을 발생합니다.',
'2024-01-01 00:00:00', 1),

-- 개인정보 수집 및 이용 동의
(1, 0, '개인정보 수집 및 이용 동의서',
'1. 개인정보 수집 및 이용 목적
- 회원가입 및 관리
- 서비스 제공 및 개선
- 고객 상담 및 불만 처리

2. 수집하는 개인정보의 항목
- 필수정보: 이름, 이메일, 휴대폰번호
- 선택정보: 생년월일, 성별

3. 개인정보의 보유 및 이용기간
회원 탈퇴 시까지

4. 개인정보 수집 및 이용 동의 거부권
귀하는 개인정보 수집 및 이용에 대한 동의를 거부할 권리가 있습니다.
다만, 필수정보 수집에 대한 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.',
'2024-01-01 00:00:00', 2),

-- 마케팅 정보 수신 동의
(1, 0, '마케팅 정보 수신 동의',
'회사는 서비스 관련 정보 및 혜택 안내를 위해 다음과 같은 방법으로 마케팅 정보를 발송할 수 있습니다.

1. 발송 방법
- 이메일
- SMS/MMS
- 앱 푸시 알림
- 우편물

2. 발송 내용
- 신규 서비스 및 상품 안내
- 이벤트 및 프로모션 정보
- 할인 혜택 및 쿠폰 제공
- 설문조사 및 이벤트 당첨 안내

3. 수신 거부
언제든지 고객센터 또는 앱 내 설정을 통해 수신을 거부할 수 있습니다.
수신 거부 후에도 거래 관련 정보는 발송될 수 있습니다.',
'2024-01-01 00:00:00', 3),

-- 위치정보 이용 동의
(1, 0, '위치정보 이용 동의서',
'1. 위치정보 수집 목적
- 주변 매장 및 서비스 정보 제공
- 배달 및 픽업 서비스 제공
- 위치 기반 맞춤 서비스 제공
- 길찾기 및 네비게이션 서비스

2. 수집 방법
- GPS를 통한 정확한 위치 수집
- WiFi 접속 정보를 통한 위치 추정
- 기지국 정보를 통한 대략적 위치 파악

3. 이용 및 보유기간
서비스 이용 기간 동안 수집 및 이용하며, 목적 달성 후 즉시 파기합니다.

4. 위치정보 제공 거부권
위치정보 제공에 대한 동의를 거부할 수 있으나, 일부 서비스 이용이 제한될 수 있습니다.',
'2024-01-01 00:00:00', 4),

-- 만 14세 이상 확인
(1, 0, '만 14세 이상 이용자 확인',
'본 서비스는 만 14세 이상 이용자를 대상으로 합니다.

1. 연령 제한 사유
- 정보통신망 이용촉진 및 정보보호 등에 관한 법률에 따라 만 14세 미만 아동의 개인정보 수집 시 법정대리인의 동의가 필요합니다.
- 서비스의 특성상 만 14세 이상의 이용자를 대상으로 설계되었습니다.

2. 만 14세 미만 이용자
만 14세 미만의 경우 법정대리인의 동의가 필요하며, 관련 법령에 따라 개인정보 수집 및 이용에 제한이 있을 수 있습니다.

3. 확인
본인은 만 14세 이상임을 확인하며, 허위 정보 제공 시 서비스 이용이 제한될 수 있음을 동의합니다.',
'2024-01-01 00:00:00', 5),

-- 약관 버전 업데이트 예시 (개인정보 처리방침 개정)
(1, 1, '개인정보 수집 및 이용 동의서 (개정)',
'1. 개인정보 수집 및 이용 목적
- 회원가입 및 관리
- 서비스 제공 및 개선
- 고객 상담 및 불만 처리
- 서비스 이용 통계 분석 (신규 추가)
- 맞춤형 서비스 제공 (신규 추가)

2. 수집하는 개인정보의 항목
- 필수정보: 이름, 이메일, 휴대폰번호
- 선택정보: 생년월일, 성별, 관심분야, 직업 (관심분야, 직업 신규 추가)
- 자동 수집: 접속 로그, 쿠키, 접속 IP 정보 (신규 추가)

3. 개인정보의 보유 및 이용기간
회원 탈퇴 후 1년간 보관 (기존: 즉시 삭제)
단, 관련 법령에 따라 일정 기간 보관이 필요한 경우 해당 기간 동안 보관

4. 개인정보 수집 및 이용 동의 거부권
귀하는 개인정보 수집 및 이용에 대한 동의를 거부할 권리가 있습니다.
다만, 필수정보 수집에 대한 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.',
'2024-07-01 00:00:00', 2);