package com.tkx.zookeeper.test;

import com.tkx.zookeeper.curator.CuratorTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;

import java.util.*;

/**
 * @Author tkx
 * @Date 2024 12 14 20 02
 * 参考：https://www.cnblogs.com/crazymakercircle/p/10228385.html#autoid-h3-2-1-0
 **/
@Slf4j
public class TestDemo {

    public static String workerPath = "/zookeeper";
    public static String pathChildNode =  "/pathChildNode";
    public static String treeNodePath = "/treeNode";
    public static void main(String[] args) throws Exception {
        // testPathChildrenCache(pathChildNode);
        treeNodeWatch(treeNodePath);
    }

    /**
     * 测试监听子节点
     * @throws Exception
     */
    public static void testNodeCache() throws Exception {
        CuratorFramework client = CuratorTest.connect();
        // 为空就创建
        if(Objects.isNull(client.checkExists().forPath(workerPath))){
            client.create().forPath(workerPath);
        }
        try {
            NodeCache nodeCache  =new NodeCache(client,workerPath,false);
            NodeCacheListener listener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    log.info("ZNode节点状态改变, path={}", childData.getPath());
                    log.info("ZNode节点状态改变, data={}", new String(childData.getData(), "Utf-8"));
                    log.info("ZNode节点状态改变, stat={}", childData.getStat());
                }
            };

            nodeCache.getListenable().addListener(listener);
            // 开启监听
            nodeCache.start();

            // 第1次变更节点数据
            client.setData().forPath(workerPath, "第1次更改内容".getBytes());
            Thread.sleep(1000);

            // 第2次变更节点数据
            client.setData().forPath(workerPath, "第2次更改内容".getBytes());

            Thread.sleep(1000);

            // 第3次变更节点数据
            client.setData().forPath(workerPath, "第3次更改内容".getBytes());
            Thread.sleep(1000);

            // 第4次变更节点数据
//            client.delete().forPath(workerPath);
            Thread.sleep(Integer.MAX_VALUE);

        }catch (Exception e){
            log.error("创建NodeCache监听失败, path={}", workerPath);
        }finally {
            System.out.println("关闭zookeeper连接");
            client.close();
        }
    }

    /**
     * 直接子节点的监听
     * @param workerPaths
     */
    public static void testPathChildrenCache(String workerPaths) {
        CuratorFramework client = CuratorTest.connect();
        try {
            if(client.checkExists().forPath(workerPaths)==null){
                client.create().forPath(workerPaths,"hello,world".getBytes());
            }else{
                client.setData().forPath(workerPaths,"hello,world".getBytes());
            }
            PathChildrenCache cache = new PathChildrenCache(client, workerPaths, true);
            PathChildrenCacheListener l = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client,
                                       PathChildrenCacheEvent event) throws Exception {
                    try {
                        ChildData data = event.getData();
                        log.info("data->{},type->{}",event.getData(),event.getType());
                        switch (event.getType()) {
                            case CHILD_ADDED:
                                log.info("子节点增加,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                            case CHILD_UPDATED:
                                log.info("子节点更新,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                            case CHILD_REMOVED:
                                log.info("子节点删除,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                            default:
                                log.info("子节点删除,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            // 增加监听器
            cache.getListenable().addListener(l);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            Thread.sleep(1000);
            String subWorkerPath = new StringBuilder().append(workerPaths).append("/").toString();
            // 创建节点
            for (int i = 0; i < 3; i++) {
               client.create().forPath(subWorkerPath + i, String.valueOf(i).getBytes());
            }
            Thread.sleep(1000);
            // 删除节点
            for (int i = 0; i < 3; i++) {
                client.delete().forPath(subWorkerPath + i);
            }
        } catch (Exception e) {
            log.error("监听节点失败,{}",e.getMessage());
        } finally {
            client.close();
        }
    }


    public static void  treeNodeWatch(String path){
        CuratorFramework client = CuratorTest.connect();
        try {
            TreeCache treeCache = new TreeCache(client,path);
            TreeCacheListener l = new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    try {
                        ChildData data = event.getData();
                        log.info("data->{},type->{}",event.getData(),event.getType());
                        switch (event.getType()) {
                            case NODE_ADDED:
                                log.info("节点增加,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                            case NODE_UPDATED:
                                log.info("节点更新,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                            case NODE_REMOVED:
                                log.info("节点删除,path={},data={}", data.getPath(), new String(data.getData()), "UTF-8");
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            treeCache.getListenable().addListener(l);
            treeCache.start();

            // 添加本节点
            if(client.checkExists().forPath(path) == null){
                client.create().forPath(path,path.getBytes());
            }
            for (int i = 0; i < 3; i++) {
                // 创建子节点
                String childPath  = path+"/child/"+i;
                String value = String.valueOf(i);
                client.create().creatingParentsIfNeeded().forPath(childPath,value.getBytes());
                Thread.sleep(1000);
                for (int j = 0; j < 3; j++) {
                    // 创建子子节点
                    String ccPath = childPath+"/"+j;
                    String v = String.valueOf(j);
                    client.create().creatingParentsIfNeeded().forPath(ccPath,v.getBytes());
                    Thread.sleep(1000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            client.close();
        }
    }
}
