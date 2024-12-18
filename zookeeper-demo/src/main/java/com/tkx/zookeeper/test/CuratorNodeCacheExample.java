package com.tkx.zookeeper.test;

import com.tkx.zookeeper.curator.CuratorTest;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * @Author tkx
 * @Date 2024 12 14 19 23
 **/
public class CuratorNodeCacheExample {

    private static final String ADDR = "127.0.0.1:2181";
    private static final String PATH = "/watch";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorTest.connect();
        //监听一个节点
        NodeCache nodeCache = new NodeCache(curatorFramework,"/zookeeper");
        //注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变化了！！！！");
            }
        });
        //开启监听 参数 如果设置为true 则开启监听时加载缓存数据
        nodeCache.start(true);
        while (true){

        }


    }

}
