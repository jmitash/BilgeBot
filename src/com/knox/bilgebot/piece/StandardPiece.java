package com.knox.bilgebot.piece;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jacob on 7/12/2015.
 */
public abstract class StandardPiece extends Piece
{
    public static ArrayList<StandardPiece> pieces = populatePieces();

    public StandardPiece(Color centerColor, Color centerColorWater)
    {
        super(centerColor, centerColorWater);
    }

    protected static ArrayList<StandardPiece> populatePieces()
    {
        ArrayList<StandardPiece> pieces = new ArrayList<>();
        pieces.add(new BlueBrickPiece());
        pieces.add(new CyanMarblePiece());
        pieces.add(new ShellPiece());
        pieces.add(new TealMarblePiece());
        pieces.add(new WaveBrickPiece());
        pieces.add(new CyanBrickPiece());
        pieces.add(new PentagonPiece());
        return pieces;
    }
}
