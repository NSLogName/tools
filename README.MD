#sql
-- auto-generated definition
create table slow_sql_log
(
  id                   int auto_increment
    primary key,
  host_address         varchar(50)     null
  comment '用户连接数据库的主机地址',
  db_name              varchar(20)     null
  comment '数据库名称',
  db_engine            int default '1' not null
  comment '数据库类型',
  sql_text             longtext        null
  comment '查询语句',
  query_times          mediumtext      not null
  comment '执行时长',
  lock_times           mediumtext      not null
  comment '锁定时长',
  parse_row_counts     mediumtext      not null
  comment '解析行数',
  return_row_rounts    mediumtext      not null
  comment '返回行数',
  execution_start_time varchar(50)     null
  comment '执行开始时间',
  memo                 varchar(255)    null
  comment '备注',
  create_time          varchar(50)     null
  comment '记录创建时间'
)
  comment '阿里云慢查询记录表';

create index idx_dbName
  on slow_sql_log (db_name);

create index idx_executionStartTime
  on slow_sql_log (execution_start_time);
  
 #备注
 运行需要配置application.properties里面的相关配置