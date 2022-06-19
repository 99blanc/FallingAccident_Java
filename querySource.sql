-- 
-- 한이음 Database / MSSQL 기반 구축
-- s201810246
-- 유저(노약자)관련
--

-- 사용자 테이블 지정
use ksh -- 자기 이름의 테이블 설정

-- 테이블 userDB 생성(기본 제약조건 추가)
create table userDB
(
	name varchar(20) not null, -- 이름
	gender varchar(1) check(gender in('M', 'W')), -- 성별
	age int check((age < 120 and age > 64) and age > 0), -- 나이
	temp numeric(3, 1) check(temp < 43.0 and temp > 24.0), -- 온도
	bpm int check(bpm < 151 and bpm > 19), -- 심박수
	ID varchar(30), -- 아이디
	PW varchar(30), -- 비밀번호
	admin bit,
	primary key(ID)
)
go
sp_help userDB
-- drop table userDB

-- 테이블 BPMDB 생성(기본 제약조건 추가)
create table infoDB
(
	name varchar(20) not null, -- 이름
	temp numeric(3, 1) check(temp < 43.0 and temp > 24.0), -- 온도
	bpm int check(bpm < 151 and bpm > 19), -- 심박수
	ID varchar(30), -- 아이디
	time datetime default getdate(), -- 입력 시간
	primary key(ID, time),
)
go
sp_help infoDB
-- drop table infoDB

-- userDB 테이블에 대한 데이터 제약조건 생성
-- alter table userDB add constraint df_nameC check(name is not null)
-- alter table userDB add constraint df_genderC check(gender in('M', 'W'))
alter table userDB add constraint df_genderD default 'M' for gender
-- alter table userDB add constraint df_ageC check((age < 120 and age > 64) and age > 0)
-- alter table userDB add constraint df_tempC check(temp < 43.0 and temp > 24.0)
alter table userDB add constraint df_tempD default 36.5 for temp
-- alter table df_bpmC add constraint bpm check(bpm < 151 and bpm > 19)
alter table userDB add constraint df_bpmD default 70 for bpm
-- alter table df_bpmC add constraint bpm check(bpm in(0, 1))
alter table userDB add constraint df_adminD default 0 for admin
-- alter table userDB drop primary key
-- alter table userDB modify ID varchar(30) not null primary key

-- userDB 데이터 넌클러스터드 인덱스 생성(주 키 ID을 토대로 함.)
create nonclustered index uidn on userDB(ID)
go
sp_helpindex userDB
-- drop index uidn

-- userDB 데이터 넌클러스터드 인덱스 생성(주 키 ID을 토대로 함.)
create nonclustered index iidn on infoDB(ID)
go
sp_helpindex infoDB
-- drop index iidn

-- 데이터베이스 삽입 트리거 설정
create trigger tri_insert on userDB
	instead of insert
	as
	if not exists(select name from inserted where ID = null)
		begin
			insert userDB select name, gender, age, temp, bpm, ID, PW, admin from inserted
			insert infoDB(name, temp, bpm, ID) select inserted.name, inserted.temp, inserted.bpm, inserted.ID from inserted
		end
	else if not exists(select name from inserted where temp = null or bpm = null)
		begin
			insert userDB select name, gender, age, '36.5', '70', ID, PW, admin from inserted
			insert infoDB(name, temp, bpm, ID) select inserted.name, '36.5', '70', inserted.ID from inserted
		end
go
-- drop trigger tri_insert

-- 데이터베이스 수정 트리거 설정
create trigger tri_update on userDB
	for update
	as
	if not exists(select name from inserted where name = null or temp = null or bpm = null or ID = null)
		begin
			insert infoDB(name, temp, bpm, ID) select inserted.name, inserted.temp, inserted.bpm, inserted.ID from inserted
		end
go
-- drop trigger tri_update

-- 데이터 삽입
insert into userDB values('노윤호', 'M', 65, 35.5, 78, 'roh', '123456', 0)
insert into userDB values('김세현', 'M', 65, 36.5, 75, 'ksh', '123456', 1)
insert into userDB values('조성빈', 'M', 65, 36.7, 80, 'csb', '123456', 0)
insert into userDB values('하재협', 'M', 65, 36.7, 80, 'hjh', '123456', 0)

-- 데이터 수정
update userDB set temp = 36.0, bpm = 70 where ID = 'roh'
update userDB set temp = 36.0, bpm = 70 where ID = 'ksh'
update userDB set temp = 36.0, bpm = 70 where ID = 'csb'
update userDB set temp = 36.0, bpm = 70 where ID = 'hjh'

-- 데이터 삭제
delete from userDB
delete from infoDB

-- 데이터 확인
select * from userDB
select * from infoDB