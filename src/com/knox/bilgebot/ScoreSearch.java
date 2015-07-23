package com.knox.bilgebot;

import com.knox.bilgebot.piece.FuturePiece;
import com.knox.bilgebot.piece.Piece;
import com.knox.bilgebot.piece.StandardPiece;
import com.knox.bilgebot.solution.*;

/**
 * Created by Jacob on 7/13/2015.
 */
public class ScoreSearch
{
    private Piece[][] board;



    public ScoreSearch(Piece[][] board)
    {
        this.board = board;
    }

    public Solution search()
    {
        Class<StandardPiece> prevPiece = null;
        int prevPieces = 0;
        int horizontalMaxPieces = 0;
        int horizontalMinPieces = Integer.MAX_VALUE;
        int horizontalCombos = 0;

        for (int y = 0; y < board.length; y++)
        {
            for (int x = 0; x < board[0].length; x++)
            {
                if (board[y][x] == null)
                {
                    if (prevPieces >= 3)
                    {
                        horizontalMaxPieces = Math.max(horizontalMaxPieces, prevPieces);
                        horizontalMinPieces = Math.min(horizontalMinPieces, prevPieces);
                        horizontalCombos++;
                    }
                    prevPiece = null;
                    prevPieces = 0;
                    continue;
                }
                if (!(board[y][x] instanceof StandardPiece))
                {
                    if (prevPieces >= 3)
                    {
                        horizontalMaxPieces = Math.max(horizontalMaxPieces, prevPieces);
                        horizontalMinPieces = Math.min(horizontalMinPieces, prevPieces);
                        horizontalCombos++;
                    }
                    prevPieces = 0;
                    prevPiece = null;
                } else //Standard Piece
                {
                    if (prevPiece == null || !prevPiece.equals(board[y][x].getClass())) //Different piece than previous
                    {
                        if (prevPieces >= 3)
                        {
                            horizontalMaxPieces = Math.max(horizontalMaxPieces, prevPieces);
                            horizontalMinPieces = Math.min(horizontalMinPieces, prevPieces);
                            horizontalCombos++;
                        }
                        prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                        prevPieces = 1;
                    } else
                    {
                        prevPieces++;
                    }
                }
            }
            if (prevPieces >= 3)
            {
                horizontalMaxPieces = Math.max(horizontalMaxPieces, prevPieces);
                horizontalMinPieces = Math.min(horizontalMinPieces, prevPieces);
                horizontalCombos++;
            }
            prevPiece = null;
            prevPieces = 0;
        }

        prevPiece = null;
        prevPieces = 0;

        int verticalMaxPieces = 0;
        int verticalMinPieces = Integer.MAX_VALUE;
        int verticalCombos = 0;

        for (int x = 0; x < board[0].length; x++)
        {
            for (int y = 0; y < board.length; y++)
            {
                if (board[y][x] == null)
                {
                    if (prevPieces >= 3)
                    {
                        verticalMaxPieces = Math.max(verticalMaxPieces, prevPieces);
                        verticalMinPieces = Math.min(verticalMinPieces, prevPieces);
                        verticalCombos++;
                    }
                    prevPiece = null;
                    prevPieces = 0;
                    continue;
                }
                if (!(board[y][x] instanceof StandardPiece))
                {
                    if (prevPieces >= 3)
                    {
                        verticalMaxPieces = Math.max(verticalMaxPieces, prevPieces);
                        verticalMinPieces = Math.min(verticalMinPieces, prevPieces);
                        verticalCombos++;
                    }
                    prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                    prevPieces = 0;
                } else //Standard Piece
                {
                    if (prevPiece == null || !prevPiece.equals(board[y][x].getClass()))
                    {
                        if (prevPieces >= 3)
                        {
                            verticalMaxPieces = Math.max(verticalMaxPieces, prevPieces);
                            verticalMinPieces = Math.min(verticalMinPieces, prevPieces);
                            verticalCombos++;
                        }
                        prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                        prevPieces = 1;
                    } else
                    {
                        prevPieces++;
                    }
                }
            }
            if (prevPieces >= 3)
            {
                verticalMaxPieces = Math.max(verticalMaxPieces, prevPieces);
                verticalMinPieces = Math.min(verticalMinPieces, prevPieces);
                verticalCombos++;
            }
            prevPiece = null;
            prevPieces = 0;
        }

        int totalCombos = verticalCombos + horizontalCombos;
        if(totalCombos == 0)
        {
            return new NoSolution();
        }
        else if(totalCombos == 1)
        {
            int totalMaxPieces = Math.max(horizontalMaxPieces, verticalMaxPieces);
            if(totalMaxPieces == 3)
            {
                return new ThreeComboSolution();
            }
            else if(totalMaxPieces == 4)
            {
                return new FourComboSolution();
            }
            else if(totalMaxPieces >= 5)
            {
                return new FiveComboSolution();
            }
            else
            {
                System.out.println("Scoring: 1 combo counted, but combo had less than 3 pieces?");
                throw new RuntimeException("This should be unreachable.");
            }
        }
        else if(totalCombos == 2)
        {
            int totalMaxPieces = Math.max(horizontalMaxPieces, verticalMaxPieces);
            int totalMinPieces = Math.min(horizontalMinPieces, verticalMinPieces);

            if(totalMaxPieces == 3 && totalMinPieces == 3)
            {
                return new ThreeByThreeSolution();
            }
            else if(totalMaxPieces == 4 && totalMinPieces == 3)
            {
                return new ThreeByFourSolution();
            }
            else if(totalMaxPieces == 4 && totalMinPieces == 4)
            {
                return new FourByFourSolution();
            }
            else if(totalMaxPieces >= 5 && totalMinPieces == 3)
            {
                return new ThreeByFiveSolution();
            }
            else if(totalMaxPieces >= 5 && totalMinPieces == 4)
            {
                return new FourByFourSolution();
            }
            else if(totalMaxPieces >= 5 && totalMinPieces >= 5)
            {
                return new FiveByFiveSolution();
            }
            else
            {
                System.out.println("Scoring: 2 combos counted, but combos had less than 3 pieces?");
                throw new RuntimeException("This should be unreachable.");
            }
        }
        else if(totalCombos == 3)
        {
            return new BingoSolution();
        }
        else if(totalCombos == 4)
        {
            return new SeaDonkeySolution();
        }
        else
        {
            return new VegasSolution();
        }
    }

    public static Piece[][] searchAndRemove(Piece[][] board)
    {

        Class<StandardPiece> prevPiece = null;
        int prevPieces = 0;

        for (int y = 0; y < board.length; y++)
        {
            for (int x = 0; x < board[0].length; x++)
            {
                if (board[y][x] == null)
                {
                    if (prevPieces >= 3)
                    {
                        for (int i = x - prevPieces; i < x; i++)
                        {
                            board[y][i] = new FuturePiece();
                        }
                    }
                    prevPiece = null;
                    prevPieces = 0;
                    continue;
                }
                if (!(board[y][x] instanceof StandardPiece))
                {
                    if (prevPieces >= 3)
                    {
                        for (int i = x - prevPieces; i < x; i++)
                        {
                            board[y][i] = new FuturePiece();
                        }
                    }
                    prevPieces = 0;
                    prevPiece = null;
                } else //Standard Piece
                {
                    if (prevPiece == null || !prevPiece.equals(board[y][x].getClass())) //Different piece than previous
                    {
                        if (prevPieces >= 3)
                        {
                            for (int i = x - prevPieces; i < x; i++)
                            {
                                board[y][i] = new FuturePiece();
                            }
                        }
                        prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                        prevPieces = 1;
                    } else
                    {
                        prevPieces++;
                    }
                }
            }
            if (prevPieces >= 3)
            {
                for (int i = board[0].length - prevPieces; i < board[0].length; i++) //TODO: verify
                {
                    board[y][i] = new FuturePiece();
                }
            }
            prevPiece = null;
            prevPieces = 0;
        }

        prevPiece = null;
        prevPieces = 0;

        for (int x = 0; x < board[0].length; x++)
        {
            for (int y = 0; y < board.length; y++)
            {
                if (board[y][x] == null)
                {
                    for (int i = y - prevPieces; i < y; i++)
                    {
                        board[i][x] = new FuturePiece();
                    }
                    prevPiece = null;
                    prevPieces = 0;
                    continue;
                }
                if (!(board[y][x] instanceof StandardPiece))
                {
                    if (prevPieces >= 3)
                    {
                        for (int i = y - prevPieces; i < y; i++)
                        {
                            board[i][x] = new FuturePiece();
                        }
                    }
                    prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                    prevPieces = 0;
                } else //Standard Piece
                {
                    if (prevPiece == null || !prevPiece.equals(board[y][x].getClass()))
                    {
                        if (prevPieces >= 3)
                        {
                            for (int i = y - prevPieces; i < y; i++)
                            {
                                board[i][x] = new FuturePiece();
                            }
                        }
                        prevPiece = (Class<StandardPiece>) board[y][x].getClass();
                        prevPieces = 1;
                    } else
                    {
                        prevPieces++;
                    }
                }
            }
            if (prevPieces >= 3)
            {
                for (int i = board.length - prevPieces; i < board.length; i++)
                {
                    board[i][x] = new FuturePiece();
                }
            }
            prevPiece = null;
            prevPieces = 0;
        }

        return board;
    }
}
