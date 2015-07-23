package com.knox.bilgebot.piece;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jacob on 7/13/2015.
 */
public abstract class Piece
{
    private Color centerColor;
    private Color centerColorWater;

    public static ArrayList<Piece> pieces = populatePieces();

    private static ArrayList<Piece> populatePieces()
    {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(0, new NullPiece());
        pieces.add(new BlowfishPiece());
        pieces.add(new JellyfishPiece());
        pieces.add(new CrabPiece());
        pieces.addAll(StandardPiece.populatePieces());
        return pieces;
    }

    public Piece(Color centerColor, Color centerColorWater)
    {
        this.centerColor = centerColor;
        this.centerColorWater = centerColorWater;
    }

    public static byte getPieceIndex(Piece piece)
    {
        if(piece == null)
        {
            return 0;
        }

        return (byte) pieces.indexOf(piece);
    }

    public boolean isColorPiece(Color color)
    {
        return color.equals(centerColor) || color.equals(centerColorWater);
    }

    public Color getCenterColor()
    {
        return centerColor;
    }

    @Override
    public boolean equals(Object o)
    {
        return o.getClass().equals(this.getClass());
    }
}
