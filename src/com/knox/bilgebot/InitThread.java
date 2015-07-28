package com.knox.bilgebot;

/**
 * Created by Jacob on 7/20/2015.
 */
public class InitThread extends Thread
{
    private final BilgeBot bilgeBot;
    private boolean overlay;
    private int depth;
    private boolean auto;

    public InitThread(BilgeBot bilgeBot, int depth, boolean auto, boolean overlay)
    {
        super("Bilge Bot init thread");
        this.setDaemon(true);
        this.bilgeBot = bilgeBot;
        this.depth = depth;
        this.auto = auto;
        this.overlay = overlay;
    }

    @Override
    public void run()
    {
        bilgeBot.init(depth, auto, overlay);
    }
}
