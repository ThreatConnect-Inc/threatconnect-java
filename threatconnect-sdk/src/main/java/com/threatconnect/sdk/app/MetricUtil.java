package com.threatconnect.sdk.app;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dtineo on 6/3/15.
 */
public class MetricUtil
{

    private static Map<String, Long> millisMap = new ConcurrentHashMap<>();
    private static Map<String, Long> countMap = new ConcurrentHashMap<>();
    private static Map<String, Long> timerMap = new ConcurrentHashMap<>();

    static
    {

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {

                    System.err.println("==============================================================================");
                    for (Map.Entry<String, Long> entry : countMap.entrySet())
                    {
                        long count = entry.getValue();
                        long millis = millisMap.get(entry.getKey());

                        System.err.printf("%s count=%d, millis=%d, rate=%.3f per second\n", entry.getKey(), count, millis, count / (millis / 1000d));

                        entry.setValue(0L);
                        millisMap.put(entry.getKey(), 0L);
                    }
                    System.err.println("==============================================================================");

                    try
                    {
                        Thread.sleep(60000);
                    } catch (InterruptedException e)
                    {
                    }
                }

            }
        }).start();
    }

    public static void add(String name)
    {
        millisMap.put(name, 0L);
        countMap.put(name, 0L);
    }

    public static void update(String name, Long millis)
    {
        millisMap.put(name, millisMap.get(name) + millis);
        countMap.put(name, countMap.get(name) + 1);
    }

    public static void timeVoid(String name, Callable task) throws Exception
    {
        time(name, task);
    }

    public static <T> T time(String name, Callable<T> task) throws Exception
    {
        tick(name);
        T value = task.call();
        tockUpdate(name);

        return value;
    }

    public static void tick(String name)
    {
        if (!millisMap.containsKey(name))
        {
            add(name);
        }

        timerMap.put(name, System.currentTimeMillis());
    }

    public static Long tock(String name)
    {
        Long value = timerMap.remove(name);
        return value == null ? 0 : System.currentTimeMillis() - value;
    }

    public static void tockUpdate(String name)
    {
        Long value = tock(name);
        update(name, value);
    }


}
