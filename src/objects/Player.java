package objects;

import java.util.ArrayList;

public class Player {
    private boolean began;

    public Player(boolean vBegan){
        this.began = vBegan;

    }

    public boolean hasBegun() {
        return this.began;
    }

}
