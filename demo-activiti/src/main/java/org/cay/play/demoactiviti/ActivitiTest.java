package org.cay.play.demoactiviti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiTest {

    @Autowired
    ProcessEngine processEngine;

    /**
     * 启动流程
     * <p>
     * RuntimeService
     */
    public void startProcess(String key) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //可根据id、key、message启动流程
        runtimeService.startProcessInstanceByKey(key);
    }
}
