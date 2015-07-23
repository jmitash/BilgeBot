package com.knox.bilgebot.gui;

import com.knox.bilgebot.ExternalWindowManager;

import java.awt.*;

/**
 * Created by Jacob on 7/14/2015.
 */
public class CursorTrackingThread extends Thread
{
    private OverlayFrame overlayFrame;
    private ExternalWindowManager externalWindowManager;
    private int xOffset;
    private int yOffset;
    private boolean shouldRun;

    public CursorTrackingThread(int x, int y, ExternalWindowManager externalWindowManager, OverlayFrame overlayFrame)
    {
        super("Cursor Tracking Thread");
        xOffset = x;
        yOffset = y;
        this.externalWindowManager = externalWindowManager;
        this.overlayFrame = overlayFrame;
    }

    @Override
    public void run()
    {
        while(shouldRun)
        {
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;

            Rectangle bounds = externalWindowManager.getWindowBounds();
            overlayFrame.setCursorPos(x - bounds.x - xOffset, y - bounds.y - yOffset);

            try
            {
                sleep(16);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setShouldRun(boolean shouldRun)
    {
        this.shouldRun = shouldRun;
    }
}
