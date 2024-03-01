SELECT sysdate FROM dual; -- 가상의 테이블에서 현재 시각을 가져옴. (현재 시각을 담고 있는 테이블은 없을테니..)
-- 실행 (ctrl+enter)
-- Sequence 생성 (동시에 같은 값을 갖지 못하도록)
CREATE SEQUENCE seq_board;

DROP TABLE TBL_BOARD;
-- Table 생성
CREATE TABLE tbl_board
(
	bno			number(10), -- 게시물 번호는 모든 데이터들이 달라야 함. 
	title		varchar2(200) NOT NULL,
	content		varchar2(2000) NOT NULL, -- varchar로 만들 수 있는 최대 크기 (2000)
	writer		varchar2(50) NOT NULL,
	category 	varchar2(200) NOT NULL,
	price 		number(20),
	deposit 	number(20)
);

-- ALTER TABLE TBL_BOARD ADD producturl VARCHAR2(500); -- 상품 사진

-- Primary Key 생성
ALTER TABLE tbl_board ADD CONSTRAINT PK_BOARD PRIMARY KEY(bno);

SELECT * FROM tbl_board;

INSERT INTO tbl_board(bno, title, content, writer, producturl)
values(seq_board.nextval, '테스트 제목', '테스트 내용', 'admin','shoes.jpg');
INSERT INTO tbl_board(bno, title, content, writer, producturl)
values(seq_board.nextval, '테스트 제목', '테스트 내용', 'admin','newbalance.jpg');


-- reply 테이블 생성
CREATE TABLE tbl_reply
(
	rno			number(10),
	bno			number(10) NOT NULL,
	reply		varchar2(1000) NOT NULL,
	replier		varchar2(50) NOT NULL,
	replydate	DATE DEFAULT sysdate,
	updatedate	DATE DEFAULT sysdate	
);

CREATE SEQUENCE SEQ_REPLY;
ALTER TABLE TBL_REPLY ADD CONSTRAINT PK_REPLY PRIMARY key(rno);

SELECT *
FROM dba_tables -- 모든 테이블 명의 이름을 가져옴.
WHERE table_name = 'TBL_REPLY';

SELECT *FROM tbl_reply WHERE BNO =64;

-- 첨부파일 테이블 생성
CREATE TABLE tbl_attach(
	uuid		VARCHAR2(100) NOT NULL,
	uploadpath	VARCHAR2(200) NOT NULL,
	filename	VARCHAR2(100) NOT NULL,
	image		VARCHAR2(1),  /*이미지이면 1, 아니면 0*/
	bno			number(10) 
);

ALTER TABLE tbl_attach ADD CONSTRAINT PK_ATTACH PRIMARY key(UUID);
SELECT *FROM tbl_attach;


drop table tbl_members1;

-- mysite member table
CREATE TABLE tbl_members1
(
	seq					number(10), -- 회원번호
	userID				VARCHAR(20) NOT NULL, -- 닉네임 
	passwd				VARCHAR(20) NOT NULL, -- 패스워드
	email	 			VARCHAR(50) NOT NULL, -- 이메일
	address				VARCHAR(100),-- 주소
	tel					VARCHAR(20), -- 연락처
	PRIMARY KEY(seq) 
);

INSERT INTO tbl_members1(seq, userID, passwd, email, address, tel)
values(5,'닉네임은없어', '1234','keunghee@naver.com','31111','010-1234-5678');

SELECT * FROM tbl_members1;


drop table tbl_product;
-- mysite member table
CREATE TABLE tbl_product
(	
	prod_ID				LONG(10), -- 상품 ID
	prod_title			VARCHAR2(200) NOT NULL, -- 판매 제목
	prod_keyword		VARCHAR(100), -- 키워드 설정
	prod_detail			VARCHAR2(200) NOT NULL, -- 상품 상세설명
	rental_days			LONG(20), -- 대여 일수
	prod_price			LONG(20), -- 가격
	regdate 			DATE DEFAULT sysdate, -- 상품 등록일
	prod_loc			VARCHAR(50) NOT NULL, -- 판매 지역 
	seller				VARCHAR(50) NOT NULL, -- 판매자 이름
	reservation	 		VARCHAR(50) NOT NULL, -- 예약자 이름
	PRIMARY KEY(prod_ID) 
);

INSERT INTO tbl_product VALUES(10,'용산구', '노경희','노희경','20221120','가벼움','신선하다','30',5,1000);

SELECT * FROM tbl_product;


INSERT INTO prodpost VALUES('제목임','내용임', '노경희','생활용품',10000, 3000);

