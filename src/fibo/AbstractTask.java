package fibo;

abstract public class AbstractTask implements Task
{
    private Status status=Status.Inactive;
    public void setStatus(Status status)
    {
        this.status = status;
    }
    public Status getStatus(){
        return status;
    }
}