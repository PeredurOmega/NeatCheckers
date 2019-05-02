import graphics.Display;
import objects.Board;
import objects.Position;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();

        Display.displayGame(board);
    }
}
