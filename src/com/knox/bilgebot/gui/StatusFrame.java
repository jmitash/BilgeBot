package com.knox.bilgebot.gui;

import com.knox.bilgebot.BilgeBot;
import com.knox.bilgebot.InitThread;
import com.knox.bilgebot.UpdateManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jacob on 7/20/2015.
 */
public class StatusFrame extends JFrame
{
    private JPanel contentPanel;
    private JTextArea statusTextArea;
    private JScrollPane statusScrollPane;
    private JSpinner depthSpinner;
    private JButton quitButton;
    private JLabel statusLabel;
    private JCheckBox autoCheckBox;
    private JCheckBox overlayCheckBox;
    private JButton startStopButton;
    private BilgeBot bilgeBot;
    private InitThread initThread;

    public StatusFrame(BilgeBot bilgeBot)
    {
        super("Knox's Bilge Bot - " + UpdateManager.getVersionString());
        this.bilgeBot = bilgeBot;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.quitButton.addActionListener(e -> dispose());

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(3, 1, 4, 1);
        this.depthSpinner.setModel(spinnerNumberModel);

        this.autoCheckBox.addActionListener(e -> {
            if (autoCheckBox.isSelected())
            {
                overlayCheckBox.setEnabled(true);
            } else
            {
                overlayCheckBox.setSelected(true);
                overlayCheckBox.setEnabled(false);
            }
        });

        this.startStopButton.addActionListener(e -> {
            if(bilgeBot.isRunning())
            {
                if(initThread.isAlive())
                {
                    bilgeBot.getStatus().log("Killing init thread");
                    initThread.stop(); //this is bad... needs to be fixed... someday
                }
                bilgeBot.stop();
                bilgeBot.getStatus().log("Stopped");
                startStopButton.setText("Start");
            }
            else
            {
                startStopButton.setText("Stop");
                initThread = new InitThread(bilgeBot, (Integer) depthSpinner.getValue(), autoCheckBox.isSelected(), overlayCheckBox.isSelected());
                initThread.start();
            }
        });

        this.statusTextArea.setFont(new JLabel().getFont());

        this.setContentPane(contentPanel);
        this.pack();
    }

    public void addMessage(String formattedMessage)
    {
        if(statusScrollPane.getVerticalScrollBar().getValue() == statusScrollPane.getVerticalScrollBar().getMaximum() || true) //TODO: fix auto scroll
        {
            //Scroll bar at bottom
            statusTextArea.append(formattedMessage);
            statusTextArea.select(statusTextArea.getText().length(), statusTextArea.getText().length());
        }
        else
        {
            //User has moved scroll bar up; don't readjust height
            statusTextArea.append(formattedMessage);
        }
    }

    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);

        if(visible)
        {
            Thread updateThread = new Thread() {
                @Override
                public void run()
                {
                    UpdateManager updateManager = new UpdateManager();
                    if(updateManager.isUpdateAvailable())
                    {
                        updateManager.requestUpdate();
                    }
                }};
            updateThread.start();
        }
    }

    public void setStatus(String status)
    {
        this.statusLabel.setText("Status: " + status);
    }
}
