package objects;

import enums.Type;

import java.util.ArrayList;

public class King extends Piece {

    public King(int vX, int vY, boolean vIsFromWhiteTeam) {
        super(vX, vY,  Type.KING,vIsFromWhiteTeam);
    }


    @Override
    public ArrayList<Position> getAvailableMovements(Board currentBoard) {
        //TODO Use recursivity to get all movements with several components
        ArrayList<Position> positions = new ArrayList<Position>();

        if(getEatingMovementsToBottomRight(currentBoard).size() != 0 || getEatingMovementsToBottomLeft(currentBoard).size() != 0 || getEatingMovementsToTopRight(currentBoard).size() != 0 || getEatingMovementsToTopLeft(currentBoard).size() != 0){
            positions.addAll(getEatingMovementsToBottomRight(currentBoard));
            positions.addAll(getEatingMovementsToBottomLeft(currentBoard));
            positions.addAll(getEatingMovementsToTopRight(currentBoard));
            positions.addAll(getEatingMovementsToTopLeft(currentBoard));
        }
        else{
            positions.addAll(getMovementsToBottomLeft(currentBoard));
            positions.addAll(getMovementsToBottomRight(currentBoard));
            positions.addAll(getMovementsToTopLeft(currentBoard));
            positions.addAll(getMovementsToTopRight(currentBoard));
        }



        return positions;
    }

    public ArrayList<Position> getMovementsToBottomRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean eatingMode = false;

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;

        ArrayList<Position> movements = new ArrayList<Position>();

        while((++x < 10 && ++y < 10) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(!eatingMode){
                if (!currentCellOccupied) {
                    movements.add(new Position(x, y));
                    System.out.println("Done 2");
                }
            }

        }

        /*
        while ((x < 10 && y < 10) && !(previousCellOccupied && currentCellOccupied)) {
            x++;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }

        while (x > this.getX() && y > this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x--;
            y--;
        }
        */
        return movements;
    }

    public ArrayList<Position> getEatingMovementsToBottomRight(Board currentBoard) {
        int x = this.getX(), y = this.getY();

        boolean eatingMode = false;

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;

        ArrayList<Position> eatingMovements = new ArrayList<Position>();

        while((++x < 10 && ++y < 10) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(eatingMode){
                if(!currentCellOccupied) {
                    eatingMovements.add(new Position(x, y));
                    System.out.println("Done 1");
                }
            }

        }

        return eatingMovements;
    }

    public ArrayList<Position> getMovementsToBottomLeft(Board currentBoard) {
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean eatingMode = false;

        ArrayList<Position> movements = new ArrayList<Position>();

        while((++x < 10 && --y >= 0) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(!eatingMode){
                if (!currentCellOccupied) {
                    movements.add(new Position(x, y));
                }
            }

        }
        /*
        while ((x < 10 && y >= 0) && !(previousCellOccupied && currentCellOccupied)) {
            x++;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x > this.getX() && y < this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x--;
            y++;
        }
        */
        return movements;
    }

    public ArrayList<Position> getEatingMovementsToBottomLeft(Board currentBoard) {
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;

        boolean eatingMode = false;

        ArrayList<Position> eatingMovements = new ArrayList<Position>();

        while((++x < 10 && --y > 0) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(eatingMode){
                if(!currentCellOccupied) {
                    eatingMovements.add(new Position(x, y));
                }
            }

        }

        return eatingMovements;

    }

    public ArrayList<Position> getMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean eatingMode = false;

        ArrayList<Position> movements = new ArrayList<Position>();

        while((--x >= 0 && --y >= 0) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(!eatingMode){
                if (!currentCellOccupied) {
                    movements.add(new Position(x, y));
                    System.out.println("Done 2");
                }
            }

        }

        /*
        while ((x >=0 && y >= 0) && !(previousCellOccupied && currentCellOccupied)) {
            x--;
            y--;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x < this.getX() && y < this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x++;
            y++;
        }
        */
        return movements;
    }

    public ArrayList<Position> getEatingMovementsToTopLeft(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean eatingMode = false;

        ArrayList<Position> eatingMovements = new ArrayList<Position>();

        while((--x > 0 && --y > 0) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(eatingMode){
                if(!currentCellOccupied) {
                    eatingMovements.add(new Position(x, y));
                }
            }

        }

        return eatingMovements;

    }

    public ArrayList<Position> getMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean eatingMode = false;

        ArrayList<Position> movements = new ArrayList<Position>();

        while((--x >= 0 && ++y < 10) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(!eatingMode){
                if (!currentCellOccupied) {
                    movements.add(new Position(x, y));
                }
            }

        }
        /*
        while ((x >=0 && y < 10) && !(previousCellOccupied && currentCellOccupied)) {
            x--;
            y++;
            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);

        }
        ArrayList<Position> movements = new ArrayList<Position>();
        while (x < this.getX() && y > this.getY()){
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);
            if (!currentCellOccupied) {
                movements.add(new Position(x, y));
            }
            x++;
            y--;
        }
        */
        return movements;
    }

    public ArrayList<Position> getEatingMovementsToTopRight(Board currentBoard){
        int x = this.getX(), y = this.getY();

        boolean previousCellOccupied = false;
        boolean currentCellOccupied = false;
        boolean eatingMode = false;

        ArrayList<Position> eatingMovements = new ArrayList<Position>();


        while((--x >= 0 && ++y < 10) && !(previousCellOccupied && currentCellOccupied)){

            previousCellOccupied = currentCellOccupied;
            currentCellOccupied = (currentBoard.getSpecificPiece(new Position(x, y)).getType() != Type.EMPTY);


            if(currentCellOccupied) {
                eatingMode = true;
            }

            if(eatingMode){
                if(!currentCellOccupied) {
                    eatingMovements.add(new Position(x, y));
                }
            }

        }

        return eatingMovements;
    }
}
