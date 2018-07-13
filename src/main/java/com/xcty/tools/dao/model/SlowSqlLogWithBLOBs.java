package com.xcty.tools.dao.model;

public class SlowSqlLogWithBLOBs extends SlowSqlLog {
    private String sqlText;

    private String queryTimes;

    private String lockTimes;

    private String parseRowCounts;

    private String returnRowRounts;

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText == null ? null : sqlText.trim();
    }

    public String getQueryTimes() {
        return queryTimes;
    }

    public void setQueryTimes(String queryTimes) {
        this.queryTimes = queryTimes == null ? null : queryTimes.trim();
    }

    public String getLockTimes() {
        return lockTimes;
    }

    public void setLockTimes(String lockTimes) {
        this.lockTimes = lockTimes == null ? null : lockTimes.trim();
    }

    public String getParseRowCounts() {
        return parseRowCounts;
    }

    public void setParseRowCounts(String parseRowCounts) {
        this.parseRowCounts = parseRowCounts == null ? null : parseRowCounts.trim();
    }

    public String getReturnRowRounts() {
        return returnRowRounts;
    }

    public void setReturnRowRounts(String returnRowRounts) {
        this.returnRowRounts = returnRowRounts == null ? null : returnRowRounts.trim();
    }
}