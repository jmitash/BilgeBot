package com.knox.bilgebot;

import com.knox.bilgebot.solution.Solution;

/**
 * Created by Jacob on 7/13/2015.
 */
public class Swap
{
    private int xPos;
    private int yPos;
    private Solution solution;

    public Swap(int xPos, int yPos, Solution solution)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.solution = solution;
    }

    @Override
    public String toString()
    {
        return String.format("%d %s (%d, %d)", solution.getScore(), solution.toString(),  xPos, yPos);
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public int getScore()
    {
        return solution.getScore();
    }

    public Solution getSolution()
    {
        return solution;
    }
}
