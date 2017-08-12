package com.cyxhm;

import java.sql.Timestamp;
import java.util.Date;

public class Stopwatch
{
    public long getStartTimestamp()
    {
        return startTimestamp;
    }

    public long getEndTimestamp()
    {
        return endTimestamp;
    }

    public long getExecutedTime()
    {
        return executedTime;
    }


    public void start()
    {
        startTimestamp = System.currentTimeMillis();
    }

    public void stop()
    {
        endTimestamp = System.currentTimeMillis();
        executedTime = endTimestamp - startTimestamp;
    }

    public long stopAndGetExecutedTime()
    {
        stop();
        return executedTime;
    }

    public Date stopAndGetDate()
    {
        stop();
        return new Date(new Timestamp(System.currentTimeMillis()).getTime());
    }

    private long startTimestamp;
    private long endTimestamp;
    private long executedTime;

}
