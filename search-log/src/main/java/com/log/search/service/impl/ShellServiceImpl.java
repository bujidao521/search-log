package com.log.search.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.log.search.entity.Shell;
import com.log.search.service.IShellService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShellServiceImpl implements IShellService {

    @Override
    public Session getSession(Shell shell){
        JSch jsch = new JSch();
        try {
            //创建session并且打开连接，因为创建session之后要主动打开连接
            Session session = jsch.getSession(shell.getUsername(), shell.getIp(), shell.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            if(StrUtil.isBlank(shell.getPassword())){
                jsch.setKnownHosts("~/.ssh/known_hosts");
                jsch.addIdentity("~/.ssh/id_rsa");
            }else {
                session.setPassword(shell.getPassword());
            }
            session.connect();
            return session;
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<String> exec(Shell shell,String command){
        Session session = null;
        ChannelExec channelExec = null;
        BufferedReader input = null;
        InputStreamReader inputStreamReader = null;
        List<String> outList = new ArrayList<>();
        try {
            //打开通道，设置通道类型，和执行的命令
            session = getSession(shell);
            if(null==session){
                return outList;
            }
            channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setInputStream(null);
            inputStreamReader = new InputStreamReader(channelExec.getInputStream(), StandardCharsets.UTF_8);
            input = new BufferedReader(inputStreamReader);

            channelExec.connect();
            //接收远程服务器执行命令的结果
            String line;
            while ((line = input.readLine()) != null) {
                outList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null!=input){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=channelExec){
                channelExec.disconnect();
                if (channelExec.isClosed()) {
                    return outList;
                }
            }
            if(null!=inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=session){
                session.disconnect();
            }
        }
        return outList;
    }

}
