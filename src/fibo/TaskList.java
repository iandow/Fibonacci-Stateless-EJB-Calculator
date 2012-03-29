package fibo;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> implements TaskListener
{
    public void taskCompleted(Task task)
    {
        add(task);
//        System.out.println("Added completed task.  this=" + this.toString());
        synchronized (this)
        {
//            System.out.println("Notifying All");
            notifyAll();
        }
    }
    public void waitForCompletion(int numTasks, long timeout)
    {
        long now = System.currentTimeMillis();
        long endTime = now + timeout;
//        System.out.println("All " + numTasks + " tasks in object " + this.toString() + " should be done by " + now + " + " + timeout + " = " + endTime);
        while ((numTasks > size()) && (System.currentTimeMillis() < endTime))
        {
            synchronized (this)
            {
                try
                {
//                    System.out.println("Waiting for task completion, or deadline at t=" + endTime + "ms. Now=" + System.currentTimeMillis());
                    wait(endTime - System.currentTimeMillis());
//                    System.out.println("Exited wait state at now=" + System.currentTimeMillis());
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
//        if (System.currentTimeMillis() >= endTime)
//            System.out.println("Time expired! Scheduled: " + numTasks+ " Completed: " + size() + " now="+  System.currentTimeMillis() + " deadline=" + endTime);
//        else
//            System.out.println("Time OK. Scheduled: " + numTasks + " Completed: " + size() + " now="+  System.currentTimeMillis() + " deadline=" + endTime);

    }
}
