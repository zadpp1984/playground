package org.cay.play.demoactiviti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.springframework.stereotype.Service;

@Service
public class UserTaskService implements ActivityBehavior {


    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("hahaha!!");

    }
}
