package com.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;

public class zkClient {

    private ZooKeeper zkCli;
    private static final String CONNECT_STRING = "192.168.1.102:2181, 192.168.1.103:2181, 192.168.1.104:2181";

    private static final int SESSION_TIMEOUT = 2000;

    @Before
    public void before() throws IOException {
        zkCli = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, e -> {
            System.out.println("默认回调函数");
        });
    }

    @Test
    public void ls() throws KeeperException, InterruptedException {
        List<String> children = zkCli.getChildren("/", e -> {
            System.out.println("自定义的回调函数");
        });

        System.out.println("============================");
        for (String child : children) {
            System.out.println(child);
        }
        System.out.println("============================");

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String s = zkCli.create("/Idea", "Idea2020".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(s);

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void getPath() throws KeeperException, InterruptedException {
        byte[] data = zkCli.getData("/zookeeper", true, new Stat());

        String string = new String(data);

        System.out.println(string);
    }

    @Test
    public void set() throws KeeperException, InterruptedException {
        Stat stat = zkCli.setData("zookeeper2020", "IGARASHI".getBytes(), 0);

        System.out.println(stat.getDataLength());
    }

    @Test
    public void stat() throws KeeperException, InterruptedException {
        Stat exists = zkCli.exists("/IGARASHI", false);

        if (exists == null) {
            System.out.println("节点不存在");
        } else {
            System.out.println(exists.getDataLength());
        }
    }

    @Test
    public void delete() throws KeeperException, InterruptedException {
        Stat exists = zkCli.exists("/zookeeper2020", false);
        if(exists != null){
            zkCli.delete("/zookeeper2020", exists.getVersion());
        }
    }
}
