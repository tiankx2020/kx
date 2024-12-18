package com.tkx.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author tkx
 * @Date 2024 11 27 00 59
 **/
@Slf4j
public class ZkHelper {
    private static final String connectUri = "127.0.0.1:2181";
    private static final int sessionTimeout = 20000; //超时时间
    private static ZooKeeper zkClient = null;//zk的客户端，相当于zkCli.sh
    private static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public ZkHelper() {
        try {
            zkClient = connect();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    public ZkHelper(Watcher watcher){
        try {
            zkClient = new ZooKeeper(connectUri,sessionTimeout,watcher);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * 建立一个{@link ZooKeeper}连接
     *
     * @return {@link ZooKeeper}连接
     * @throws IOException          IO异常
     * @throws InterruptedException 中断异常
     */
    private ZooKeeper connect() throws IOException, InterruptedException {
        zkClient = new ZooKeeper(connectUri, sessionTimeout, event -> {
            log.debug("ZooKeeper客户端初始化");
            //收到事件通知后的回调函数（用户的业务逻辑）
            log.debug("事件信息：事件类型{}--事件发生的结点的路径{}--服务器状态{}", event.getType(), event.getPath(), event.getState());
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) { //只有回调的状态值
                log.debug("客户端建立与服务器的连接");
                connectedSemaphore.countDown();//只有连接建立了才释放锁，让主线程继续运行
            }
        });
        connectedSemaphore.await(); //在主线程中堵塞，等待连接建立好
        log.debug("客户端主线程运行完");
        return zkClient;
    }

    /**
     * 关闭连接
     */
    public void close() throws InterruptedException {
        zkClient.close();
    }
    /**
     * 拿到连接uri
     */
    public String getConnectUri() {
        return connectUri;
    }
    /**
     * 拿到zookeeper连接
     *
     * @return zkClient
     */
    public ZooKeeper getZookeeper() {
        return zkClient;
    }
}
