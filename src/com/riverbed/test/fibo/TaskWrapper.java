package com.riverbed.test.fibo;

import java.io.Serializable;

public class TaskWrapper implements Serializable {
    private Task task;
    private TaskListener listener;
    public TaskWrapper(Task task, TaskListener listener) {
        this.task = task;
        this.listener = listener;
    }
    public void run(){
        try {
            task.setStatus(Task.Status.Started);
//            System.out.println("this obj = " + this.toString() + ", this.listener obj = " + this.listener.toString());
            task.run();
        }
        finally{
            task.setStatus(Task.Status.Completed);
            if (listener!=null)
                listener.taskCompleted(task);
        }
    }
}