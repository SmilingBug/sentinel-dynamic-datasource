package com.yuntai.sentinel.datasource.zk.constants;

/**
 * zookeeper动态规则实现所需要的一些配置
 */
public class ZookeeperConfigConstant {

    /**
     * 默认组id
     */
    public static final String GROUP_ID = "DEFAULT_SENTINEL_GROUP";
    /**
     * 限流
     */
    public static final String FLOW_PATH_SUFFIX = "-flow-rules";
    /**
     * 降级
     */
    public static final String DEGRADE_PATH_SUFFIX = "-degrade-rules";
    /**
     * 系统防护
     */
    public static final String SYSTEM_PATH_SUFFIX = "-system-rules";

}
