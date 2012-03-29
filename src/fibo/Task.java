package fibo;

import java.io.Serializable;

public interface Task extends Runnable, Serializable
{
    enum Status { Inactive, Started, Completed };
    public void setStatus(Status status);
    public Status getStatus();
}