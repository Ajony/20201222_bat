drop procedure if exists updateViewscreen;
delimiter //
create procedure updateViewscreen()
begin

    -- 增加table示例
    CREATE TABLE IF NOT EXISTS viewscreen.TBL_UPGRADE_TEST3
    (
        ID    BIGINT NOT NULL AUTO_INCREMENT,
        VALUE VARCHAR(512),
        PRIMARY KEY (ID)
    );

    -- 修改table示例
-- if not exists (select COLUMN_NAME from information_schema.COLUMNS where TABLE_SCHEMA='viewscreen' and TABLE_NAME='TBL_UPGRADE_TEST' and COLUMN_NAME='ADD_COL')
-- then  ALTER TABLE TBL_UPGRADE_TEST ADD ADD_COL varchar(64) COMMENT '新增字段';
-- end if;
-- 请在这里仿照以上语句编写SQL升级语句

    if not exists(select COLUMN_NAME
                  from information_schema.COLUMNS
                  where TABLE_SCHEMA = 'viewscreen'
                    and TABLE_NAME = 'tbl_share_area'
                    and COLUMN_NAME = 'location')
    then ALTER TABLE `viewscreen`.`tbl_share_area`
        ADD COLUMN `location` varchar(255) NULL DEFAULT NULL COMMENT '经纬度' AFTER `status`;
    end if;


    ALTER TABLE `viewscreen`.`tbl_china_area`
        MODIFY COLUMN `id` bigint(20) NULL DEFAULT NULL FIRST,
        MODIFY COLUMN `address_parent_id` bigint(20) NULL DEFAULT NULL AFTER `id`,
        MODIFY COLUMN `class_sum` bigint(20) NULL DEFAULT NULL AFTER `ext_name`,
        MODIFY COLUMN `desktop_sum` bigint(20) NULL DEFAULT NULL AFTER `class_sum`,
        MODIFY COLUMN `school_sum` bigint(20) NULL DEFAULT NULL AFTER `desktop_sum`,
        MODIFY COLUMN `course_sum` bigint(20) NULL DEFAULT NULL AFTER `school_sum`,
        MODIFY COLUMN `host_sum` bigint(20) NULL DEFAULT NULL AFTER `course_sum`,
        MODIFY COLUMN `terminal_sum` bigint(20) NULL DEFAULT NULL AFTER `host_sum`,
        MODIFY COLUMN `user_sum` bigint(20) NULL DEFAULT NULL AFTER `terminal_sum`;


    ALTER TABLE `viewscreen`.`tbl_college_dynamic`
        MODIFY COLUMN `host_number` bigint(20) NULL DEFAULT NULL COMMENT '主机数' AFTER `id`,
        MODIFY COLUMN `classroom_number` bigint(20) NULL DEFAULT NULL COMMENT '教室数' AFTER `host_number`,
        MODIFY COLUMN `terminal_number` bigint(20) NULL DEFAULT NULL COMMENT '终端数' AFTER `classroom_number`;


end;
//
delimiter ;
call updateViewscreen();
drop procedure if exists updateViewscreen;
