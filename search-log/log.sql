create schema if not exists log collate latin1_swedish_ci;

create table if not exists app_info
(
	id bigint auto_increment comment 'id'
		primary key,
	app_name varchar(255) not null comment '应用名称',
	log_path varchar(255) null comment '日志默认路径',
	create_user varchar(20) default 'sys' null comment '创建人',
	create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
	update_user varchar(20) default 'sys' null comment '更新人',
	update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
charset=utf8mb4;

create table if not exists app_instance
(
	id bigint auto_increment comment 'id'
		primary key,
	app_id bigint null comment '应用id',
	shell_host varchar(255) null comment '应用主机',
	shell_port int null comment 'shell端口',
	shell_user varchar(255) null comment 'shell用户',
	shell_pass varchar(255) null comment 'shell密码',
	create_user varchar(20) default 'sys' null,
	create_time datetime default CURRENT_TIMESTAMP null,
	update_user varchar(100) default 'sys' null,
	update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	log_path varchar(255) null comment '日志路径，空则使用app_info的路径'
)
charset=utf8mb4;

