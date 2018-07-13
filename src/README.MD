CREATE TABLE slow_sql_log
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    host_address VARCHAR(50) COMMENT '用户连接数据库的主机地址',
    db_name VARCHAR(20) COMMENT '数据库名称',
    db_engine INT DEFAULT 1 NOT NULL COMMENT '数据库类型',
    sql_text LONGTEXT COMMENT '查询语句',
    query_times LONG NOT NULL COMMENT '执行时长',
    lock_times LONG NOT NULL COMMENT '锁定时长',
    parse_row_counts LONG NOT NULL COMMENT '解析行数',
    return_row_rounts LONG NOT NULL COMMENT '返回行数',
    execution_start_time VARCHAR(50) COMMENT '执行开始时间',
    memo VARCHAR(255) COMMENT '备注',
    create_time VARCHAR(50) COMMENT '记录创建时间'
);
ALTER TABLE slow_sql_log COMMENT = '阿里云慢查询记录表';

ALTER TABLE slow_sql_log ADD INDEX idx_executionStartTime (execution_start_time);

ALTER TABLE slow_sql_log ADD INDEX idx_dbName (db_name);