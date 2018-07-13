package com.xcty.tools.dao.mapper;

import com.xcty.tools.dao.model.SlowSqlLog;
import com.xcty.tools.dao.model.SlowSqlLogWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "SlowSqlLogMapper")
public interface SlowSqlLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SlowSqlLogWithBLOBs record);

    int insertSelective(SlowSqlLogWithBLOBs record);

    int insertSlowSqlLogWithBLOBsBatch(List<SlowSqlLogWithBLOBs> recordList);

    SlowSqlLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SlowSqlLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SlowSqlLogWithBLOBs record);

    int updateByPrimaryKey(SlowSqlLog record);
}