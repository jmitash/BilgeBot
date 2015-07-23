package com.knox.bilgebot;

import com.knox.bilgebot.gui.StatusFrame;

/**
 * Created by Jacob on 7/11/2015.
 */
public class Status
{
    public static Status I;

    public enum Severity { DEBUG, INFO, WARNING, ERROR };

    private Severity minimumSeverity = Severity.INFO;
    private final static Severity MINIMUM_SEVERITY_TAG = Severity.WARNING;

    private StatusFrame statusFrame;

    public Status(StatusFrame statusFrame)
    {
        this.statusFrame = statusFrame;
        I = this;
    }


    public void log(String message, Severity severity)
    {
        String formattedMessage = ""; //TODO: possibly add indentation?
        if(severity.compareTo( minimumSeverity) >= 0)
        {
            if(severity.compareTo(MINIMUM_SEVERITY_TAG) >= 0)
            {
                formattedMessage += severity.toString();
                formattedMessage += " ";
            }
        }
        else
        {
            //Message shouldn't be shown
            return;
        }

        formattedMessage += "> ";
        formattedMessage += message;
        formattedMessage += "\n";

        statusFrame.addMessage(formattedMessage);
    }

    public void setStatus(String status)
    {
        statusFrame.setStatus(status);
    }

    public void log(String message)
    {
        log(message, Severity.INFO);
    }
}
