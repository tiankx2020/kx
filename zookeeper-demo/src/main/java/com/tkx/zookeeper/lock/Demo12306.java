package com.tkx.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;

/**
 * @Author tkx
 * @Date 2024 12 02 21 37
 **/
public class Demo12306 implements Runnable {

    private InterProcessMutex mutex ;
    private int  count = 10;

    public Demo12306(){
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")//集群的ip地址和端口号
                .sessionTimeoutMs(5000)//超时时间
                .retryPolicy(new RetryOneTime(3000))//重连机制 超时3秒后重连一次
                .namespace("tkx")
                .build();//构建连接
        client.start();
        mutex = new InterProcessMutex(client,"/lock");

    }

    @Override
    public void run() {
        while (true){
            try {
                // mutex.acquire(3, TimeUnit.SECONDS);
                if(count > 0){
                    Thread.sleep(3);
                    System.out.println(Thread.currentThread()+":"+count--);
                }
            }catch (Exception e){

            }finally {
                try {
                    // mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
