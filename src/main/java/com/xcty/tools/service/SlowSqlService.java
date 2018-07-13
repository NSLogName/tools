package com.xcty.tools.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.rds.model.v20140815.DescribeSlowLogRecordsRequest;
import com.aliyuncs.rds.model.v20140815.DescribeSlowLogRecordsResponse;
import com.xcty.tools.dao.mapper.SlowSqlLogMapper;
import com.xcty.tools.dao.model.SlowSqlLogWithBLOBs;
import com.xcty.tools.utils.DatetimeUtil;
import com.xcty.tools.utils.LoggerUtils;
import com.xcty.tools.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/********************************
 * @Title SlowSQLService
 * @package com.xcty.tools.aliyun.sql
 * @Description:TODO
 *
 * @author XCTY
 * @date 2018/7/2 13:13
 * @version
 *********************************/
@Service
public class SlowSqlService {
    @Value("${rds.regionId}")
    private String regionId;

    @Value("${rds.accessKeyId}")
    private String accessKeyId;

    @Value("${rds.accessKeySecret}")
    private String accessKeySecret;

    @Value("${rds.actionName}")
    private String actionName;

    @Value("${rds.dbInstanceId}")
    private String dbInstanceId;

    @Value("${rds.pageSize}")
    private Integer pageSize;


    private static final int FLAG = 1000;

    private final SlowSqlLogMapper slowSqlLogMapper;

    @Autowired
    public SlowSqlService(SlowSqlLogMapper slowSqlLogMapper) {
        this.slowSqlLogMapper = slowSqlLogMapper;
    }


    @Scheduled(cron = "0 00 04 * * ?")
    public void execute() {
        LoggerUtils.getLogger(getClass()).info("======================== 慢SQl查询入库操作执行结束！ ========================");
        String date = StringUtils.split(DatetimeUtil.getNextDay(DatetimeUtil.getNow(), -1), " ")[0];
        String searchStartTime = TimeUtil.local2Utc( date + " 00:00:00");
        String searchEndTime = TimeUtil.local2Utc(date + " 23:59:59");

        // 发起请求并处理应答或异常
        DescribeSlowLogRecordsResponse response;
        try {
            Integer pageNumber = getPageNumber(searchStartTime, searchEndTime);
            List<SlowSqlLogWithBLOBs> slowSqlLogWithBLOBsList = new ArrayList<SlowSqlLogWithBLOBs>();
            for (int i = 1; i <= pageNumber; i++) {
                response = getAcsResponse(createRequest(searchStartTime, searchEndTime, i));
                List<DescribeSlowLogRecordsResponse.SQLSlowRecord> list = response.getItems();
                for (DescribeSlowLogRecordsResponse.SQLSlowRecord sqlSlowLog : list) {
                    if (slowSqlLogWithBLOBsList.size() == FLAG) {
                        slowSqlLogMapper.insertSlowSqlLogWithBLOBsBatch(slowSqlLogWithBLOBsList);
                        slowSqlLogWithBLOBsList.clear();
                    }
                    SlowSqlLogWithBLOBs slowSqlLogWithBLOBs = createSlowSqlLogWithBLOBs(sqlSlowLog);
                    slowSqlLogWithBLOBsList.add(slowSqlLogWithBLOBs);
                }
            }

            // 补充写入
            if (slowSqlLogWithBLOBsList.size() > 0) {
                slowSqlLogMapper.insertSlowSqlLogWithBLOBsBatch(slowSqlLogWithBLOBsList);
            }
        } catch (ClientException e) {
            LoggerUtils.getLogger(getClass()).error("写入慢SQL出错！" + e.getMessage(), e);
        }

        LoggerUtils.getLogger(getClass()).info("======================== 慢SQl查询入库操作执行结束！ ========================");
    }

    /**
     * 生成数据库存储对象
     *
     * @param sqlSlowLog 返回实体
     * @return 数据库存储对象
     */
    private SlowSqlLogWithBLOBs createSlowSqlLogWithBLOBs(DescribeSlowLogRecordsResponse.SQLSlowRecord sqlSlowLog) {
        SlowSqlLogWithBLOBs slowSqlLogWithBLOBs = new SlowSqlLogWithBLOBs();
        slowSqlLogWithBLOBs.setHostAddress(sqlSlowLog.getHostAddress());
        slowSqlLogWithBLOBs.setDbName(sqlSlowLog.getDBName());
        slowSqlLogWithBLOBs.setDbEngine(1);
        slowSqlLogWithBLOBs.setExecutionStartTime(TimeUtil.utc2Local(sqlSlowLog.getExecutionStartTime()));
        slowSqlLogWithBLOBs.setLockTimes(sqlSlowLog.getLockTimes() + "");
        slowSqlLogWithBLOBs.setQueryTimes(sqlSlowLog.getQueryTimes() + "");
        slowSqlLogWithBLOBs.setParseRowCounts(sqlSlowLog.getParseRowCounts() + "");
        slowSqlLogWithBLOBs.setSqlText(StringUtils.replace(sqlSlowLog.getSQLText(), "\n", ""));
        slowSqlLogWithBLOBs.setReturnRowRounts(sqlSlowLog.getReturnRowCounts() + "");
        slowSqlLogWithBLOBs.setCreateTime(DatetimeUtil.getNowStr());

        return slowSqlLogWithBLOBs;
    }

    /**
     * 创建API请求并设置参数
     *
     * @return API请求
     */
    private DescribeSlowLogRecordsRequest createRequest(String searchStartTime, String searchEndTime, int pageNumber) {
        // 创建API请求并设置参数
        DescribeSlowLogRecordsRequest request = new DescribeSlowLogRecordsRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setActionName(actionName);
        request.setDBInstanceId(dbInstanceId);
        request.setStartTime(searchStartTime);
        request.setEndTime(searchEndTime);
        request.setPageSize(pageSize);
        request.setPageNumber(pageNumber);

        return request;
    }

    /**
     * 获取页数
     *
     * @param searchStartTime 开始搜索时间
     * @param searchEndTime   结束搜索时间
     * @return 页数
     * @throws ClientException
     */
    private int getPageNumber(String searchStartTime, String searchEndTime) throws ClientException {
        DescribeSlowLogRecordsRequest request = createRequest(searchStartTime, searchEndTime, 1);
        DescribeSlowLogRecordsResponse response = getAcsResponse(request);
        Integer num = response.getTotalRecordCount();
        Integer residueNum = num % pageSize;
        Integer pageNumber = num / pageSize;
        return residueNum > 0 ? pageNumber + 1 : pageNumber;
    }

    /**
     * 获取响应实体
     *
     * @param request 请求实体
     * @return 响应实体
     * @throws ClientException
     */
    private DescribeSlowLogRecordsResponse getAcsResponse(DescribeSlowLogRecordsRequest request) throws ClientException {
        // 创建DefaultAcsClient实例并初始化(地域ID, RAM账号的AccessKey ID,  RAM账号Access Key Secret)
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client.getAcsResponse(request);
    }
}
