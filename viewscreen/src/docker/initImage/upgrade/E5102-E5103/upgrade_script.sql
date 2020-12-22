drop procedure if exists updateViewscreen;
delimiter //
create procedure updateViewscreen()
begin

-- 增加table示例
CREATE TABLE IF NOT EXISTS viewscreen.TBL_UPGRADE_TEST2 (
 ID BIGINT NOT NULL AUTO_INCREMENT,
 VALUE VARCHAR(512),
  PRIMARY KEY (ID)
 );

-- 修改table示例
-- if not exists (select COLUMN_NAME from information_schema.COLUMNS where TABLE_SCHEMA='viewscreen' and TABLE_NAME='TBL_UPGRADE_TEST' and COLUMN_NAME='ADD_COL')
-- then  ALTER TABLE TBL_UPGRADE_TEST ADD ADD_COL varchar(64) COMMENT '新增字段';
-- end if;
-- 请在这里仿照以上语句编写SQL升级语句


end;
//
delimiter ;
call updateViewscreen();
drop procedure if exists updateViewscreen;
