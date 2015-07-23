package com.knox.bilgebot;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 7/11/2015.
 */
public class ImageSearch
{
    private BufferedImage source;
    private BufferedImage search;


    public ImageSearch(BufferedImage source, BufferedImage search)
    {
        this.source = source;
        this.search = search;
    }


    public Point search()
    {
        int posX = 0;
        int posY = 0;

        int initX;
        int initY;

        int initialColor = search.getRGB(0, 0);


        while (posY < source.getHeight() - search.getHeight())
        {
            while (posX < source.getWidth() - search.getWidth())
            {
                int color = source.getRGB(posX, posY);
                if (initialColor == color)
                {
                    initX = posX;
                    initY = posY;
                    boolean tempMatch = true;
                    for (int y = initY; y < initY + search.getHeight() && tempMatch; y++)
                    {
                        for (int x = initX; x < initX + search.getWidth() && tempMatch; x++)
                        {
                            int srcColor = source.getRGB(x, y);
                            int schColor = search.getRGB(x - initX, y - initY);

                            if (srcColor != schColor)
                            {
                                tempMatch = false;
                            }
                        }
                    }

                    if (tempMatch)
                    {
                        return new Point(initX, initY);
                    }
                }
                posX++;
            }
            posY++;
            posX = 0;
        }

        return null;
    }

}
