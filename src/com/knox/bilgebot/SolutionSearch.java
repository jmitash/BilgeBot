package com.knox.bilgebot;

import com.knox.bilgebot.piece.FuturePiece;
import com.knox.bilgebot.piece.NullPiece;
import com.knox.bilgebot.piece.Piece;
import com.knox.bilgebot.piece.StandardPiece;
import com.knox.bilgebot.solution.FiveComboSolution;
import com.knox.bilgebot.solution.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 7/13/2015.
 */
public class SolutionSearch
{
    private Piece[][] board;
    private Piece[][] cleanBoard;
    private int depth;
    private int startIndex;
    private int endIndex; //exclusive

    public SolutionSearch(final Piece[][] board, int depth, int startIndex, int endIndex)
    {
        this.board = new Piece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) //Copy array
        {
            for (int j = 0; j < board[0].length; j++)
            {
                this.board[i][j] = board[i][j];
            }
        }
        cleanBoard = new Piece[board.length][board[0].length];
        this.depth = depth;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public List<Swap> searchDepth(int depth)
    {
        List<Swap> bestSwap = null;

        for (int i = startIndex; i < endIndex; i++)
        {
            int y = i / board[0].length;
            int x = i % board[0].length;

            if (x == 5)
            {
                continue;
            }

            if (board[y][x] != null && board[y][x] instanceof StandardPiece && board[y][x + 1] != null && board[y][x + 1] instanceof StandardPiece)
            {
                if(board[y][x].equals(board[y][x + 1]))
                {
                    continue;
                }
                swapAdjacent(x, y);

                ScoreSearch scoreSearch = new ScoreSearch(board);
                int score = 0;
                for(int k = 0; k < board.length; k++)
                {
                    System.arraycopy(board[k], 0, cleanBoard[k], 0, board[0].length);
                }
                Solution solution = scoreSearch.search();
                int initialScore = solution.getScore();
                Solution tempSolution;
                while ((tempSolution = scoreSearch.search()).getScore() > 0) //Keep summing score until board is clean
                {
                    score += Math.min(tempSolution.getScore(), new FiveComboSolution().getScore());
                    cleanBoard = ScoreSearch.searchAndRemove(cleanBoard);
                    cleanBoard = SolutionSearch.tickBoard(cleanBoard);
                    scoreSearch = new ScoreSearch(cleanBoard);
                }
                score /= 3;
                score += initialScore;

                if (bestSwap == null || score > sumSwapScores(bestSwap))
                {
                    bestSwap = new ArrayList<>();
                    bestSwap.add(new Swap(x, y, solution));
                }

                if (depth > 1)
                {
                    SolutionSearch solDepthSearch = new SolutionSearch(cleanBoard, depth - 1, 0, 72);
                    List<Swap> depthSwaps = solDepthSearch.searchDepth(depth - 1);
                    depthSwaps.add(0, new Swap(x, y, solution));
                    if (sumSwapScores(depthSwaps) > sumSwapScores(bestSwap))
                    {
                        bestSwap = depthSwaps;
                    }
                }

                swapAdjacent(x, y);
            }
        }

        return bestSwap;
    }

    public List<Swap> searchDepthThreads(int numThreads, int depth)
    {
        int segmentSize = (board.length * board[0].length) / numThreads;

        SolutionSearchThread[] threads = new SolutionSearchThread[numThreads];

        for (int i = 0; i < numThreads; i++)
        {
            SolutionSearch solutionSearch;
            if(i == numThreads - 1)
            {
                solutionSearch = new SolutionSearch(board, 0, i * segmentSize, board.length * board[0].length);
            }
            else
            {
                solutionSearch = new SolutionSearch(board, 0, i * segmentSize, (i + 1) * segmentSize);
            }
            threads[i] = new SolutionSearchThread(solutionSearch, depth);
            threads[i].start();
        }

        List<Swap> bestSwaps = null;
        for (int i = 0; i < numThreads; i++)
        {
            try
            {
                threads[i].join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            List<Swap> solSwaps = threads[i].getSwaps();
            if (bestSwaps == null || sumSwapScores(solSwaps) > sumSwapScores(bestSwaps))
            {
                bestSwaps = solSwaps;
            }
        }

        return bestSwaps;
    }

    private static int sumSwapScores(List<Swap> swaps)
    {
        int sum = 0;
        for (Swap swap : swaps)
        {
            sum += swap.getScore();
        }
        return sum;
    }

    private void swapAdjacent(int xPos, int yPos)
    {
        StandardPiece tempPiece = (StandardPiece) board[yPos][xPos]; //Swap
        board[yPos][xPos] = board[yPos][xPos + 1];
        board[yPos][xPos + 1] = tempPiece;
    }

    public static Piece[][] tickBoard(Piece[][] board)
    {
        int vOffset = 0;
        for (int x = 0; x < board[0].length; x++)
        {
            for (int y = 0; y < board.length; y++)
            {
                if (board[y][x] instanceof FuturePiece)
                {
                    vOffset++;
                }
                else
                {
                    board[y - vOffset][x] = board[y][x];
                }
                if(y >= board.length - vOffset) //TODO: verify correct?
                {
                    board[y][x] = new NullPiece();
                }
            }
            vOffset = 0;
        }


        return board;
    }

    public static void printBoard(Piece[][] board)
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (board[i][j] != null)
                {
                    System.out.printf("%d ", StandardPiece.pieces.indexOf(board[i][j]));
                } else
                {
                    System.out.printf("N ");
                }
            }
            System.out.println();
        }
        System.out.println("========");
    }
}
