package com.riverbed.test.fibo;
import javax.ejb.*;

@Remote
public interface FiboRemote {
    public long bean_entry_point(int s);
    public long getElapsedTime();
    public Long getResult();
}