import controllers.GameController;

class Main  {

    /**
     * Method to start the game (non-static).
     * @param args Params needed for main static method.
     */
    public static void main(String[] args) {
        new GameController().startGame();
    }
}
