package com.knox.bilgebot;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 7/24/2015.
 */
public class UpdateManager
{
    private final static int VERSION_MAJOR = 1;
    private final static int VERSION_MINOR = 0;
    private final static String UPDATE_URL = "https://dl.dropboxusercontent.com/u/48365491/bilgebot/update.dat";


    private int updateMajorVersion = 0;
    private int updateMinorVersion = 0;
    private List<String> updateInfo = new ArrayList<>();
    private String updateUrlLine;

    public void requestUpdate()
    {
        if(updateUrlLine == null)
        {
            throw new RuntimeException("requestUpdate() called, but no update URL is available");
        }

        String message = "A new version is available. You are currently running version: " + VERSION_MAJOR + "." + VERSION_MINOR + ".";
        message += "\n\n";
        message += (updateMajorVersion + "." + updateMinorVersion);
        for(String line : updateInfo)
        {
            message += ("\n" + line);
        }
        int action = JOptionPane.showConfirmDialog(null, message, "Bilge Bot Update", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(action == JOptionPane.YES_OPTION)
        {
            try
            {
                Desktop.getDesktop().browse(new URI(updateUrlLine));
            } catch (IOException | URISyntaxException e)
            {
                Status.I.log("Error opening update link: " + e.getMessage(), Status.Severity.ERROR);
                e.printStackTrace();
            }
        }
    }

    public boolean isUpdateAvailable()
    {
        boolean hasUpdate = false;

        URL url;
        try
        {
            url = new URL(UPDATE_URL);
        } catch (MalformedURLException e)
        {
            Status.I.log("Update URL is invalid: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }

        URLConnection urlConnection;
        try
        {
            urlConnection = url.openConnection();
        } catch (IOException e)
        {
            Status.I.log("Failed to connect to update URL: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }

        BufferedReader bufferedReader;
        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e)
        {
            Status.I.log("Failed to connect to update URL: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }

        try
        {
            String versionLine = bufferedReader.readLine();
            updateUrlLine = bufferedReader.readLine();
            String currentLine;
            updateInfo.clear();
            while ((currentLine = bufferedReader.readLine()) != null)
            {
                updateInfo.add(currentLine);
            }

            String[] versionPieces = versionLine.split("\\.");
            updateMajorVersion = Integer.parseInt(versionPieces[0]);
            updateMinorVersion = Integer.parseInt(versionPieces[1]);
            if(updateMajorVersion > VERSION_MAJOR)
            {
                hasUpdate = true;
            }
            else if(updateMajorVersion == VERSION_MAJOR && updateMinorVersion > VERSION_MINOR)
            {
                hasUpdate = true;
            }
        } catch (IOException e)
        {
            Status.I.log("Failed to load update information: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }
        catch (NullPointerException e)
        {
            Status.I.log("Unexpected end while loading update information", Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }
        catch (NumberFormatException e)
        {
            Status.I.log("Unexpected data while reading update version number: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Status.I.log("Unexpected data while reading update version number: " + e.getMessage(), Status.Severity.WARNING);
            e.printStackTrace();
            return false;
        }

        try
        {
            bufferedReader.close();
        } catch (IOException e)
        {
            Status.I.log("Could not close BufferedReader for updater: " + e.getMessage());
            e.printStackTrace();
        }

        return hasUpdate;
    }

    public static String getVersionString()
    {
        return VERSION_MAJOR + "." + VERSION_MINOR;
    }
}
