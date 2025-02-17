CREATE SCHEMA schedules;
USE schedules;

CREATE TABLE todos
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    user_id BIGINT NOT NULL COMMENT '유저 식별자',
    todo VARCHAR(200) NOT NULL COMMENT '할일',
    password VARCHAR(50) NOT NULL COMMENT '비밀번호',
    created_at DATETIME NOT NULL COMMENT '작성일',
    updated_at DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 식별자',
    name VARCHAR(50) NOT NULL COMMENT '이름',
    email VARCHAR(50) NOT NULL UNIQUE COMMENT '이메일',
    created_at DATETIME NOT NULL COMMENT '작성일',
    updated_at DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);