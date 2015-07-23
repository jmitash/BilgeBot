package com.knox.bilgebot.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 7/11/2015.
 */
public class ImageFrame extends JFrame
{
    private BufferedImage image;

    public ImageFrame(BufferedImage image)
    {
        super("Image Viewer");
        this.image = image;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(image.getWidth() + 5, image.getHeight() + 5);

        this.getContentPane().add(new ImagePanel());
    }

    public void setImage(BufferedImage bufferedImage)
    {
        image = bufferedImage;
        this.setSize(image.getWidth() + 5, image.getHeight() + 5);
        this.repaint();
    }

    private class ImagePanel extends JPanel
    {
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(image, 0, 0, this);
        }
    }


}
