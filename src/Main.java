import graphics.Display;
import objects.Board;
import objects.Position;

public class Main {

    static Position precPos = new Position(-1,-1);

    public static void main(String[] args) {
        Board board = new Board();

        Display.displayGame(board);

        Position a = waitForClick();
    }

    public static Position waitForClick(){

        while(Display.getCaseClicked().equals(precPos)){
            pause(500);
        }
        precPos = Display.getCaseClicked();
        return Display.getCaseClicked();

    }

    public static void pause(int ms) {
        try{
            Thread.sleep(ms);
        }catch(InterruptedException e){}
    }
}
