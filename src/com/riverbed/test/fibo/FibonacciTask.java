package com.riverbed.test.fibo;

public class FibonacciTask extends AbstractTask
{
    private int n;
    private long result;
    private long elapsedTime;
    private long timeout;
    private long maxTime = Long.MAX_VALUE;

    public FibonacciTask(int n){
        this.n = n;
    }

    public FibonacciTask(int n, long t){
        this.n = n;
        this.timeout = t;
    }

    public void run()
    {
        long initialTime = System.currentTimeMillis();
        maxTime = initialTime + timeout;
        //System.out.println("Fib(" + n + ") started.  object: " + this.toString());
        try{
            result = fibonacci(n);
            elapsedTime = System.currentTimeMillis() - initialTime;
        } catch (Exception e) {
            result = 0;
            elapsedTime = 0;
            //System.out.println("Time expired!");
        } finally {
            //System.out.println("Fib(" + n + ")=" + result + "  t=" + System.currentTimeMillis() + " elapsedTime=" + elapsedTime + "ms.  object: " + this.toString());
        }
    }

    protected long fibonacci(int n) throws Exception {
        long now = System.currentTimeMillis();
        //if (maxTime!=Long.MAX_VALUE && now < maxTime) System.out.println("["+now+"] maxTime=" + maxTime + "\tn="+n);
        if (now > maxTime) {
            //System.out.println("Time expired!");
            throw(new Exception());
        }

        long ret = n;
        if (n > 1)
            ret = fibonacci(n-1) + fibonacci(n-2);
        //System.out.println("Fib(" + n + ") = " + ret);
        return ret;
    }

    public int getN(){
        return n;
    }

    public long getResult()
    {
        return result;
    }
    public long getElapsedTime()
    {
        return elapsedTime;
    }
}