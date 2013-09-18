package com.riverbed.test.fibo;
import javax.ejb.*;

@Stateless
public class FiboBean implements FiboRemote {
    private Long elapsedTime;
    private Long result;
    public long bean_entry_point(int s) {
        FibonacciTask mytask = new FibonacciTask(s);

                long initialTime = System.currentTimeMillis();
                long result = fibonacci(s);
                long elapsedTime = System.currentTimeMillis() - initialTime;
                System.out.println("\tFib("+s+")=" + result + " computed after " + elapsedTime/1000F + "s");

        /*long initialTime = System.currentTimeMillis();

        mytask.run();

        elapsedTime = System.currentTimeMillis() - initialTime;
        result = mytask.getResult();*/

        return elapsedTime;
    }
    public long getElapsedTime() {
        return elapsedTime;
    }
    public Long getResult() {
        return result;
    }

    private long fibonacci(int n) {
        long ret = n;
        if (n > 1)
            ret = fibonacci(n-1) + fibonacci(n-2);
        return ret;
    }
}