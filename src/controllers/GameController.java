package controllers;

import graphics.Display;
import interfaces.GameListener;
import objects.Board;
import objects.Position;

public class GameController implements GameListener {
    Display displayer;
    @Override
    public void onClick(Position position) {
        System.out.println("Clicked : " + position);
    }

    public void startGame(){
        Board board = new Board();
        displayer = new Display(board, GameController.this);
    }
}
