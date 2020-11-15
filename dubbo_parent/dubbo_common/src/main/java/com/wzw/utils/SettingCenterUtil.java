package com.wzw.utils;



import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Properties;

/**
 * 配置中心
 * 1.首
 次启动通过此类加载数据源配置
 * 2.当zookeeper中config目录下节点下数据有改变的时候，也会通过此类加载最新的数据源配置 */
public class SettingCenterUtil extends PropertyPlaceholderConfigurer implements ApplicationContextAware {
    XmlWebApplicationContext xmlWebApplicationContext;
    /**
     * 加载zookeeper中config节点数据
     */
    public void loadZK(Properties properties){
        try {
            //创建重试策略
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,1);
            //创建客户端
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",retryPolicy);
            //开启客户端
            client.start();

            //在zookeeper中写入连接池的内容
            String jdbcDriver = new String(client.getData().forPath("/config/jdbc.driver"));
            String jdbcUrl = new String(client.getData().forPath("/config/jdbc.url"));
            String jdbcUser = new String(client.getData().forPath("/config/jdbc.user"));
            String jdbcPassword = new String(client.getData().forPath("/config/jdbc.password"));

            //将zk的数据设置Properties
            properties.setProperty("jdbc.driver",jdbcDriver);
            properties.setProperty("jdbc.url",jdbcUrl);
            properties.setProperty("jdbc.user",jdbcUser);
            properties.setProperty("jdbc.password",jdbcPassword);

            //关闭客户端
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加监听
     */
    public void addWatch(Properties properties){
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",retryPolicy);
            client.start();

            //添加监听
            TreeCache treeCache = new TreeCache(client,"/config");
            treeCache.start();

            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    if(event.getType() == TreeCacheEvent.Type.NODE_UPDATED){
                        ///调用spring容器刷新
                        xmlWebApplicationContext.refresh();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 父类加载配置方法
     * @param beanFactoryToProcess
     * @param props
     * @throws BeansException
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        //从zookeeper获取数据设置props对象中
        loadZK(props);
        //监听config
        addWatch(props);
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * 重新加载spring容器
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        xmlWebApplicationContext = (XmlWebApplicationContext)applicationContext;
    }
}
