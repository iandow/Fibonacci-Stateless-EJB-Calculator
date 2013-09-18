package com.riverbed.test.fibo;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Collection;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;

@Stateless (name="EJBConcurrencyManager")
@Local (ConcurrencyManager.class)
@TransactionManagement(value=TransactionManagementType.BEAN )
@TransactionAttribute(value=NOT_SUPPORTED)
public class EJBConcurrencyManager implements ConcurrencyManager
{
    //private @Resource TimerService timerService;
    @Resource
    TimerService timerService;
    private Timer timer;

    public void execute(Collection<Task> tasks)
    {
        if ((tasks == null) || (tasks.size() == 0))
            return;
        TaskList completedTasks = new TaskList();
        for (Task task : tasks)
        {
//            TaskWrapper tw = new TaskWrapper(task);
            TaskWrapper tw = new TaskWrapper(task, completedTasks);
            timerService.createTimer(0,tw);
        }
    }

    public Collection<Task> executeAndWait(Collection<Task> tasks, long timeout)
    {
        if ((tasks == null) || (tasks.size() == 0))
            return null;
        if (timeout < 0)
            new IllegalArgumentException("timeout cannot be negative");

        //timer = timerService.createTimer(0,tw);

        TaskList completedTasks = scheduleTasks(tasks);
        completedTasks.waitForCompletion(tasks.size(), timeout);
//        System.out.println("Done waiting for completion. t=" + System.currentTimeMillis());
        return completedTasks;
    }

    private TaskList scheduleTasks(Collection<Task> tasks)
    {
        TaskList completedTasks = new TaskList();
//        System.out.println("completedTasks obj = " + completedTasks.toString());
        for (Task task : tasks)
        {
            TaskWrapper tw = new TaskWrapper(task, completedTasks);
            timerService.createTimer(0,tw);
        }
        return completedTasks;
    }

    @Timeout
    public void timeoutHandler(Timer t)
    {
        TaskWrapper tw = (TaskWrapper) t.getInfo();
        tw.run();
    }


}
