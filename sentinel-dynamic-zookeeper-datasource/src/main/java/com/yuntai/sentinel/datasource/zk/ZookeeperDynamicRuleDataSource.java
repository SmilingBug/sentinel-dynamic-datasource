package com.yuntai.sentinel.datasource.zk;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuntai.sentinel.datasource.zk.config.ZookeeperDynamicDataSourceConfig;
import com.yuntai.sentinel.datasource.zk.constants.ZookeeperConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ZookeeperDynamicRuleDataSource
 *
 * @author yangqk
 */
public class ZookeeperDynamicRuleDataSource implements InitFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperDynamicRuleDataSource.class);

    private ZookeeperDynamicDataSourceConfig zookeeperDynamicDataSourceConfig;


    @Override
    public void init() throws Exception {
        LOGGER.info("[sentinel-dynamic-zookeeper-datasource] init.zookeeperDynamicDataSourceConfig is: {}",zookeeperDynamicDataSourceConfig.toString());
        final String remoteAddress = zookeeperDynamicDataSourceConfig.getRemoteAddress();
        // 引入groupId和dataId的概念，是为了方便和Nacos进行切换
        final String groupId = StringUtil.isNotBlank(zookeeperDynamicDataSourceConfig.getGroupId()) ? zookeeperDynamicDataSourceConfig.getGroupId() : ZookeeperConfigConstant.GROUP_ID;
        final String flowDataId = zookeeperDynamicDataSourceConfig.getAppName() + ZookeeperConfigConstant.FLOW_PATH_SUFFIX;
        final String degradeDataId = zookeeperDynamicDataSourceConfig.getAppName() + ZookeeperConfigConstant.DEGRADE_PATH_SUFFIX;
        final String systemDataId = zookeeperDynamicDataSourceConfig.getAppName() + ZookeeperConfigConstant.SYSTEM_PATH_SUFFIX;


        // 规则会持久化到zk的/groupId/flowDataId节点
        // groupId和flowDataId可以用/开头也可以不用
        // 建议不用以/开头，目的是为了如果从Zookeeper切换到Nacos的话，只需要改数据源类名就可以
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress, groupId, flowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ZookeeperDataSource<>(remoteAddress, groupId, degradeDataId,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new ZookeeperDataSource<>(remoteAddress, groupId, systemDataId,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                }));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
    }


    public ZookeeperDynamicDataSourceConfig getZookeeperDynamicDataSourceConfig() {
        return zookeeperDynamicDataSourceConfig;
    }

    public void setZookeeperDynamicDataSourceConfig(ZookeeperDynamicDataSourceConfig zookeeperDynamicDataSourceConfig) {
        this.zookeeperDynamicDataSourceConfig = zookeeperDynamicDataSourceConfig;
    }

}
