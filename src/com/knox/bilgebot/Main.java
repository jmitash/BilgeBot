package com.knox.bilgebot;

import javax.swing.*;

public class Main
{

    /**
     * Program entry point. Creates a BilgeBot
     * @param args unused
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e)
        {
        } catch (InstantiationException e)
        {
        } catch (IllegalAccessException e)
        {
        } catch (UnsupportedLookAndFeelException e)
        {
        }

        BilgeBot bilgeBot = new BilgeBot();


/*        Piece[][] board = {
                {new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece()},
                {new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece()},
                {new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece()},
                {new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece()},
                {new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece()},
                {new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece()},
                {new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece()},
                {new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece()},
                {new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece(), new PentagonPiece()},
                {new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece(), new ShellPiece()},
                {new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece(), new TealMarblePiece()},
                {new TealMarblePiece(), new ShellPiece(), new PentagonPiece(), new CyanMarblePiece(), new CyanBrickPiece(), new BlueBrickPiece()}
        };

        SolutionSearchCL solutionSearchCL = new SolutionSearchCL(board, 4);
        solutionSearchCL.search();*/
    }

}


