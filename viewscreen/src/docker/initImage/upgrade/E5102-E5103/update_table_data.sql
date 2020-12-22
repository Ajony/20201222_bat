-- 本文件主要存放数据库数据修改语句, 所有语句通过存储过程执行，保证其原子性
drop procedure if exists updateViewscreen;
delimiter //
create procedure updateViewscreen()
begin
  --update viewscreen.TBL_PARAMETER set `VALUE` ='Viewscreen@1234' where `TYPE` = 'sys_conf' AND `NAME` ='client.releasePwd' AND `VALUE` ='viewscreen.com';
end;
//
delimiter ;
call updateViewscreen();
drop procedure if exists updateViewscreen;
