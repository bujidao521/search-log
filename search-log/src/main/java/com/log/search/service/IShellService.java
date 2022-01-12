package com.log.search.service;

import com.jcraft.jsch.Session;
import com.log.search.entity.Shell;

import java.util.List;

public interface IShellService {
    public Session getSession(Shell shell);
    public List<String> exec(Shell shell, String command);
}
