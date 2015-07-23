package com.knox.bilgebot;

import com.knox.bilgebot.piece.CrabPiece;
import com.knox.bilgebot.piece.Piece;
import com.knox.bilgebot.piece.StandardPiece;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 7/12/2015.
 */
public class PieceSearch
{
    private BufferedImage screenCapture;



    public PieceSearch(BufferedImage image)
    {
        screenCapture = image;
    }

    public Piece[][] searchPieces()
    {
        Piece[][] pieces = new Piece[PIECES_PER_COL][PIECES_PER_ROW];

        for(int y = 0; y < PIECES_PER_COL; y++)
        {
            for (int x = 0; x < PIECES_PER_ROW; x++)
            {
                int xPos = BORDER_WIDTH + PIECE_LENGTH / 2 + PIECE_LENGTH * x + PIECE_OFFSET;
                int yPos = BORDER_WIDTH + PIECE_LENGTH / 2 + PIECE_LENGTH * y + PIECE_OFFSET;
                Color color = new Color(screenCapture.getRGB(xPos, yPos));

                for (int i = 0; i < StandardPiece.pieces.size(); i++)
                {
                    if(StandardPiece.pieces.get(i).isColorPiece(color))
                    {
                        pieces[y][x] = StandardPiece.pieces.get(i);
                    }
                }
                if(pieces[y][x] == null)
                {
                    for(int i = 0; i < Piece.pieces.size(); i++)
                    {
                        if(Piece.pieces.get(i).isColorPiece(color))
                        {
                            pieces[y][x] = Piece.pieces.get(i);
                        }
                    }
                    if(pieces[y][x] == null)
                    {
                        color = new Color(screenCapture.getRGB(xPos, yPos + 10));
                        if(new CrabPiece().isColorPiece(color))
                        {
                            pieces[y][x] = new CrabPiece();
                        }
                    }
                }
            }
        }

        return pieces;
    }

    public void retrieveColors()
    {
        System.out.println("==============================================================");
        for(int y = 0; y < PIECES_PER_COL; y++)
        {
            for (int x = 0; x < PIECES_PER_ROW; x++)
            {
                int xPos = BORDER_WIDTH + PIECE_LENGTH / 2 + PIECE_LENGTH * x + PIECE_OFFSET;
                int yPos = BORDER_WIDTH + PIECE_LENGTH / 2 + PIECE_LENGTH * y + PIECE_OFFSET + 10;
                Color color = new Color(screenCapture.getRGB(xPos, yPos));
                //OverlayFrame.INSTANCE.pixelPoints.add(new Point(xPos, yPos));
                System.out.println("(" + x + ", " + y + "): " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            }
        }
    }

    private final static int BORDER_WIDTH = 7;
    private final static int PIECES_PER_ROW = 6;
    private final static int PIECES_PER_COL = 12;
    private final static int PIECE_LENGTH = 45;
    private final static int PIECE_OFFSET = -3;
}
