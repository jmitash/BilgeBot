package com.knox.bilgebot;

/**
 * Created by Jacob on 7/12/2015.
 */
public class TickThread extends Thread
{
    private BilgeBot bilgeBot;
    private boolean shouldRun;

    public TickThread(BilgeBot bilgeBot)
    {
        super("Bot Tick Thread");
        this.bilgeBot = bilgeBot;
        shouldRun = true;
    }

    @Override
    public void run()
    {
        while (shouldRun)
        {
            try
            {
                sleep(100);
            } catch (InterruptedException e)
            {

            }
            long initTickTime = System.currentTimeMillis();
            bilgeBot.tick();
            long tickTime = System.currentTimeMillis() - initTickTime;
            if (tickTime > 200)
            {
                System.out.println("Tick time: " + (System.currentTimeMillis() - initTickTime));
            }
        }

        bilgeBot.getStatus().log("Tick thread shutting down");
    }

    public void shutdown()
    {
        shouldRun = false;
    }
}
