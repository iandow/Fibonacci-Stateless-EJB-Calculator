package fibo;
import java.util.Collection;
import javax.ejb.*;

@Local
public interface ConcurrencyManager
{
    public void execute(Collection<Task> tasks);
    //public void executeAndWait(Collection<Task> tasks);
    public Collection<Task> executeAndWait(Collection<Task> tasks, long timeout);
}
