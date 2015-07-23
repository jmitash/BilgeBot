package com.knox.bilgebot;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import java.awt.*;

/**
 * Created by Jacob on 7/11/2015.
 */
public class ExternalWindowManager
{
    private HWND hwnd;

    public ExternalWindowManager()
    {
        isWindowAvailable();
    }

    public boolean isWindowAvailable()
    {
        hwnd = null;
        User32.INSTANCE.EnumWindows((hwnd, pointer) -> {
            byte[] titleString = new byte[256];
            User32.INSTANCE.GetWindowTextA(hwnd, titleString, titleString.length);
            String title = Native.toString(titleString);
            if(title.equals("Puzzle Pirates") || (title.startsWith("Puzzle Pirates") && title.endsWith("ocean")))
            {
                ExternalWindowManager.this.hwnd = hwnd;
                return false;
            }
            return true;
        }, null);

        return hwnd != null;
    }


    public boolean isWindowActive()
    {
        throw new RuntimeException("Not implemented");
        /*
        //TODO: make functional
        HWND focus = User32.INSTANCE.GetFocus();
        System.out.println(focus);
        return focus.equals(hwnd); //The equals method isn't implemented for HWND
        //Perhaps a title text comparison would do?
        */
    }

    public void restoreWindow()
    {
        boolean result = User32.INSTANCE.SetForegroundWindow(hwnd);
        if (!result)
        {
            Status.I.log("Failed to pull up window", Status.Severity.ERROR);
        }
    }

    public Rectangle getWindowBounds()
    {
        int[] rect = {0, 0, 0, 0};

        User32.INSTANCE.GetWindowRect(hwnd, rect);
        //Note GetWindowRect gives x1, y1, x2, y2
        //not x, y, width, height
        if(rect[2] == 0 || rect[3] == 0)
        {
            Status.I.log("Received invalid window bounds", Status.Severity.ERROR);
        }

        return new Rectangle(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
    }

    public interface User32 extends StdCallLibrary
    {

        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SetForegroundWindow(HWND hWnd);
        boolean GetWindowRect(HWND hWnd, int[] rect);
        HWND GetFocus();

        boolean EnumWindows(EnumWindowsProc lpEnumFunction, Pointer pointer);

        interface EnumWindowsProc extends StdCallCallback
        {
            boolean callback(HWND hwnd, Pointer pointer);
        }

        int GetWindowTextA(HWND hWnd, byte[] lpString, int size);
    }

}
