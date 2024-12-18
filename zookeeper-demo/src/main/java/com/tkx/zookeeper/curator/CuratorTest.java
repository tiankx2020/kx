package com.tkx.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.UUID;

/**
 * @Author tkx
 * @Date 2024 11 27 23 27
 **/
public class CuratorTest {

    static String url = "127.0.0.1:2181";
    static CuratorFramework client = null;

    public static void main(String[] args) throws Exception {
        connect();
        String path  ="/"+UUID.randomUUID().toString().substring(0,6);
        String val = "10086"+path;
        // 创建持久节点
        // create("/www","101",CreateMode.PERSISTENT);
        // 创建临时节点
        // create("/www2","102",CreateMode.EPHEMERAL);
        // 异步创建
        asyncCreate(path,val);
        Thread.sleep(10000);
        close();
    }

    public static CuratorFramework connect() {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(1000, 4);
        /// 第一种创建方式
        //  CuratorFramework client = CuratorFrameworkFactory.newClient(url,30*1000,15*1000,backoffRetry);
        client = CuratorFrameworkFactory.builder()
                .connectString(url).sessionTimeoutMs(30 * 1000).
                connectionTimeoutMs(15 * 10000).retryPolicy(backoffRetry).namespace("tkx").build();
        client.start();
        return client;
    }


    public static void close() {
        client.close();
    }

    /**
     * 创建节点
     *
     * @param path
     */
    public static void create(String path, String value,CreateMode createMode) throws Exception {
        client.create()
                .creatingParentsIfNeeded() // 递归创建
                //创建节点的模式
                .withMode(createMode)
                //权限列表为 world:anyone:cdrwa
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                //arg1 结点路径 arg2 结点数据 由于指定了命名空间 会创建/create/node1
                .forPath(path, value.getBytes());
    }

    public static void asyncCreate(String path, String val) throws Exception {
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

    // 普通的修改
    public static void update(String path,String val) throws Exception {
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
    public static void updateNoVersion(String path,String val) throws Exception {
        if(path.startsWith("/")){
            path = "/"+path;
        }
        client.setData().withVersion(-1).forPath(path,val.getBytes());
    }


    /**
     * 异步更新
     * @param path
     * @param val
     * @throws Exception
     */
    public static void asyncUpdate(String path,String val) throws Exception {
        if(path.startsWith("/")){
            path = "/"+path;
        }
        boolean notExist = client.checkExists().forPath(path)==null;
        if(notExist){
            return;
        }
        client.setData().withVersion(-1).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("节点路径->" + event.getPath());
                System.out.println("节点类型->" + event.getType());
            }
        }).forPath(path,val.getBytes());
    }


    /**
     * 删除
     * @param path
     * @throws Exception
     */
    public static void delete(String path) throws Exception {

        client.delete().deletingChildrenIfNeeded().forPath(path);
    }


    /***
     * 查找
     * @param path
     */
    public static String get(String path) throws Exception {
        path = path.startsWith("/")?path:"/"+path;
        if(client.checkExists().forPath(path)==null) {
            return "该路径下没有值";
        }
        byte[] bytes = client.getData().forPath(path);
        return new String(bytes);
    }

}
