package com.tkx.zookeeper.util;

import com.tkx.zookeeper.config.Constants;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author tkx
 * @Date 2024 12 14 16 57
 **/
@Component
@Slf4j
public class CuratorUtil   {

    private  CuratorFramework client = null;



    @PostConstruct
    public void initialize() {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(1000, 4);
        client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZOOKEEPER_URL)
                .sessionTimeoutMs(Constants.SESSION_TIMEOUT_MS).
                connectionTimeoutMs(Constants.CONNECTION_TIMEOUT_MS)
                .retryPolicy(backoffRetry).build();
        client.start();
    }

    /**
     * 创建节点
     * @param path
     * @param value
     * @param createMode
     * @throws Exception
     */
    public  void create(String path, String value, CreateMode createMode) throws Exception {
        client.create()
                .creatingParentsIfNeeded() // 递归创建
                //创建节点的模式
                .withMode(createMode)
                //权限列表为 world:anyone:cdrwa
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                //arg1 结点路径 arg2 结点数据 由于指定了命名空间 会创建/create/node1
                .forPath(path, value.getBytes());
    }


    /**
     * 异步创建
     * @param path
     * @param val
     * @throws Exception
     */
    public  void asyncCreate(String path, String val) throws Exception {
        path = path.startsWith("/") ? path : "/" + path;
        // 节点不存在
        if (client.checkExists().forPath(path) != null) {
            System.out.println("节点存在");
            return;
        }
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println("节点路径->" + event.getPath());
                        System.out.println("节点类型->" + event.getType());
                    }
                })
                .forPath(path, val.getBytes());
    }


    /**
     *  普通的修改
     */
    public  void update(String path,String val) throws Exception {
        if(path.startsWith("/")){
            path = "/"+path;
        }
        client.setData().forPath(path,val.getBytes());
    }

    /**
     * 不修改版本号的更新
     * @param path
     * @param val
     * @throws Exception
     */
    public  void updateNoVersion(String path,String val) throws Exception {
        if(path.startsWith("/")){
            path = "/"+path;
        }
        client.setData().withVersion(-1).forPath(path,val.getBytes());
    }

    /**
     * 删除
     * @param path
     * @throws Exception
     */
    public  void delete(String path) throws Exception {

        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    /***
     * 查找
     * @param path
     */
    public  String get(String path) throws Exception {
        path = path.startsWith("/")?path:"/"+path;
        if(client.checkExists().forPath(path)==null) {
            return "该路径下没有值";
        }
        byte[] bytes = client.getData().forPath(path);
        return new String(bytes);
    }

    /**
     * 监控path
     * @param path
     */
    // public  void watchNode(String path) throws Exception {
    //     log.info("watchNode方法开始执行");
    //     PathChildrenCache cache = new PathChildrenCache(client, path, true);
    //
    //     // 添加监听器
    //     PathChildrenCacheListener listener = (client, event)->{
    //         switch (event.getType()) {
    //             case CHILD_ADDED:
    //                log.info("Child added: " + event.getData().getPath());
    //                 break;
    //             case CHILD_UPDATED:
    //                 log.info("Child updated: " + event.getData().getPath());
    //                 break;
    //             case CHILD_REMOVED:
    //                 log.info("Child removed: " + event.getData().getPath());
    //                 break;
    //             default:
    //                 break;
    //         }
    //     };
    //     cache.getListenable().addListener(listener);
    //
    //     // 启动 PathChildrenCache
    //     cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    //
    // }
}
