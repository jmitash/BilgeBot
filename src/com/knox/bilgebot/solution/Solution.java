package com.knox.bilgebot.solution;

/**
 * Created by Jacob on 7/16/2015.
 */
public abstract class Solution implements Comparable
{
    private int score;

    public Solution(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public int compareTo(Object o)
    {
        Solution solution = (Solution) o;

        return this.getScore() - solution.getScore();
    }

    @Override
    public abstract String toString();
}
