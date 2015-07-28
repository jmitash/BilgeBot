package com.knox.bilgebot.gui;

import com.knox.bilgebot.ExternalWindowManager;
import com.knox.bilgebot.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 7/12/2015.
 */
public class OverlayFrame extends JFrame
{
    private BufferedImage image;

    private int selectorX = 0;
    private int selectorY = 0;

    private int cursorX = 0;
    private int cursorY = 0;

    private Point solutionPoint;

    private final static int SELECTOR_WIDTH = 90;
    private final static int SELECTOR_HEIGHT = 45;

    private Piece[][] puzzlePieces;

    private CursorTrackingThread cursorTrackingThread;

    public OverlayFrame(int x, int y, ExternalWindowManager exWinMan)
    {
        cursorTrackingThread = new CursorTrackingThread(x, y, exWinMan, this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 590);
        this.setContentPane(new ImagePanel());
    }

    public void setImage(BufferedImage bufferedImage)
    {
        this.image = bufferedImage;
        this.repaint();
    }

    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        if(visible)
        {
            cursorTrackingThread.setShouldRun(true);
            cursorTrackingThread.start();
        }
        else
        {
            cursorTrackingThread.setShouldRun(false);
        }
    }

    private class ImagePanel extends JPanel
    {
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);
            if(image != null)
            {
                g.drawImage(image, 0, 0, this);
            }

            if(selectorX > 0)
            {
                g.setColor(new Color(255, 255, 0, 120));
                g.fillRect(selectorX, selectorY, SELECTOR_WIDTH, SELECTOR_HEIGHT);
            }

            if(puzzlePieces != null)
            {
                for(int y = 0; y < PIECES_PER_COL; y++)
                {
                    for(int x = 0; x < PIECES_PER_ROW; x++)
                    {
                        if(puzzlePieces[y][x] != null)
                        {
                            int xPos = BORDER_WIDTH + x * PIECE_LENGTH;
                            int yPos = BORDER_WIDTH + y * PIECE_LENGTH;

                            Color origColor = puzzlePieces[y][x].getCenterColor();
                            drawOutlineRect(g, origColor, xPos, yPos, PIECE_LENGTH, PIECE_LENGTH);
                        }
                    }
                }
            }

            if(solutionPoint != null)
            {
                int xPos = BORDER_WIDTH + solutionPoint.x * PIECE_LENGTH;
                int yPos = BORDER_WIDTH + solutionPoint.y * PIECE_LENGTH;

                drawOutlineRect(g, new Color(255, 0, 0), xPos, yPos, PIECE_LENGTH * 2, PIECE_LENGTH);
            }

            if(cursorX > 0 && cursorY > 0)
            {
                g.setColor(Color.GREEN);
                g.fillOval(cursorX, cursorY, 3, 3);
            }
        }
    }

    public void setSelectorPosition(Point point)
    {
        if(point != null)
        {
            selectorX = point.x;
            selectorY = point.y;
        }
        else
        {
            selectorX = 0;
            selectorY = 0;
        }
        this.repaint();
    }

    public void setSolution(Point point)
    {
        this.solutionPoint = point;
        this.repaint();
    }

    public void setPuzzlePieces(Piece[][] puzzlePieces)
    {
        this.puzzlePieces = puzzlePieces;
    }

    public synchronized void setCursorPos(int x, int y)
    {
        this.cursorX = x;
        this.cursorY = y;
        this.repaint();
    }

    private static void drawOutlineRect(Graphics g, Color color, int x, int y, int width, int height)
    {
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
        g.fillRect(x, y, width, height);

        g.setColor(color);
        g.fillRect(x, y, width, OUTLINE_WIDTH);
        g.fillRect(x + width - OUTLINE_WIDTH, y, OUTLINE_WIDTH, height);
        g.fillRect(x, y + height - OUTLINE_WIDTH, width, OUTLINE_WIDTH);
        g.fillRect(x, y, OUTLINE_WIDTH, height);
    }

    private final static int OUTLINE_WIDTH = 2;

    private final static int BORDER_WIDTH = 7;
    private final static int PIECES_PER_ROW = 6;
    private final static int PIECES_PER_COL = 12;
    private final static int PIECE_LENGTH = 45;
}
