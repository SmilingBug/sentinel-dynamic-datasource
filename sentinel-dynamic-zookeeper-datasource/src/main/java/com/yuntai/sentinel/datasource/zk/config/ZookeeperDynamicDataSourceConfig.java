package com.yuntai.sentinel.datasource.zk.config;

public class ZookeeperDynamicDataSourceConfig {

    /**
     * zk地址
     */
    private String remoteAddress;
    /**
     * 组id；引入groupId和dataId的概念，是为了方便和Nacos进行切换
     */
    private String groupId;
    /**
     * 应用名称
     */
    private String appName;

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "ZookeeperDynamicDataSourceConfig{" +
                "remoteAddress='" + remoteAddress + '\'' +
                ", groupId='" + groupId + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
