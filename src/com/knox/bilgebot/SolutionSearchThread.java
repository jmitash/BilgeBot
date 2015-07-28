package com.knox.bilgebot;

import java.util.List;

/**
 * Created by Jacob on 7/15/2015.
 */
public class SolutionSearchThread extends Thread
{
    private SolutionSearch solutionSearch;
    private int depth;
    private List<Swap> swaps;

    public SolutionSearchThread(SolutionSearch solutionSearch, int depth)
    {
        super("Solution Search Thread");
        this.setDaemon(true);
        this.solutionSearch = solutionSearch;
        this.depth = depth;
    }

    @Override
    public void run()
    {
        swaps = solutionSearch.searchDepth(depth);
    }

    public List<Swap> getSwaps()
    {
        return swaps;
    }
}
