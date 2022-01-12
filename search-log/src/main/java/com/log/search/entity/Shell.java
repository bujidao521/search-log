package com.log.search.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Shell {

    //远程主机的ip地址
    private String ip;
    //远程主机登录用户名
    private String username;
    //远程主机的登录密码
    private String password;
    //设置ssh连接的远程端口
    public int port = 22;
    //保存输出内容的容器
    private ArrayList<String> stdout = new ArrayList<>();


    /**
     * get stdout
     * @return
     */
    public ArrayList<String> getStandardOutput() {
        return stdout;
    }
}
