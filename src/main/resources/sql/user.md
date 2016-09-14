findByUserId
===
select * from sys_user where user_id=#_root#


findByUsername
===
select * from sys_user where user_name=#_root#

pageQuery
===
*分页查询
select #page()# from sys_user where  1=1
@if(!isEmpty(userName)){
and user_Name = #userName#
@}
@if(!isEmpty(realName)){
and real_Name like #'%'+realName+'%'#
@}

updateById
===
update sys_user set 
@trim(){ 
@if(!isEmpty(userName)){
	   user_name=#userName#,
	@}
	@if(!isEmpty(passwd)){
	   passwd=#passwd#,
	@}
	@if(!isEmpty(salt)){
	   salt=#salt#,
	@}
	@if(!isEmpty(realName)){
	   real_name=#realName#,
	@}
	@if(!isEmpty(email)){
	   email=#email#,
	@}
	@if(!isEmpty(phone)){
	   phone=#phone#,
	@}
	@if(!isEmpty(islock)){
	   islock=#islock#,
	@}
	@if(!isEmpty(isfreeze)){
	   isfreeze=#isfreeze#,
	@}
	@if(!isEmpty(createMan)){
	   create_man=#createMan#,
	@}
	@if(!isEmpty(createTime)){
	   create_time=#createTime#,
	@}
	@} 
	where user_id =#userId#
