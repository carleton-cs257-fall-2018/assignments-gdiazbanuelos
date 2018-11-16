package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import static zelda.Player.DIRECTION.SOUTH;

public class Player extends Rectangle {

    private int linkRow;
    private int linkCol;
    private int health;
    private Image stance;
    private DIRECTION currentDir;
    private Arrow arrow;

    // Image locations for the sprites
    private Image southStance = new Image("/res/south.png");
    private Image northStance = new Image("/res/north.png");
    private Image eastStance = new Image("/res/east.png");
    private Image westStance = new Image("/res/west.png");


    public enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;
    }

    /**
     * Creates the player model with the param constraints
     * @param row
     * @param col
     * @param health
     * @param direction
     */
    public Player(int row, int col, int health, DIRECTION direction){
        this.linkRow = row;
        this.linkCol = col;
        this.health = health;
        this.currentDir = direction;
        setNewImage();
    }

    /**
     * returns current row of player
     * @return
     */
    public int getLinkRow(){
        return this.linkRow;
    }

    /**
     * returns current col of player
     * @return
     */
    public int getLinkCol(){
        return this.linkCol;
    }

    /**
     * sets the player row to the param
     * @param row
     */
    public void setLinkRow(int row){
        this.linkRow = row;
    }

    /**
     * sets the player col to the param
     * @param col
     */
    public void setLinkCol(int col){
        this.linkCol = col;
    }

    /**
     * sets thje player health to the param
     * @param newHealth
     */
    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    /**
     * adds the param to the current player health
     * @param add
     */
    public void addHealth(int add){
        this.health += add;
    }

    /**
     * returns the current health of the player
     * @return
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * returns the current image of the player
     * @return
     */
    public Image getStance(){
        return this.stance;
    }

    /**
     * Returns the current direction the model is facing
     * @return
     */
    public DIRECTION getCurrentDir(){return this.currentDir;}

    /**
     * Sets the new image of the model depending on the direction it is facing
     */
    private void setNewImage(){
        if(this.getCurrentDir() == DIRECTION.SOUTH){
            this.stance = southStance;
        } else if(this.getCurrentDir() == DIRECTION.NORTH){
            this.stance = northStance;
        } else if(this.getCurrentDir() == DIRECTION. EAST){
            this.stance = eastStance;
        } else{this.stance = westStance;}
    }

    /**
     * changes the player row and col on the gameboard by the param values
     * @param rowChange
     * @param columnChange
     * @param gameBoard
     */
    public void moveLinkBy(int rowChange, int columnChange, GameBoard gameBoard) {
        if (gameBoard.isGameOver()) {
            return;
        }

        if(rowChange != 0){
            if (rowChange == 1){
                this.currentDir = DIRECTION.SOUTH;
            } else{this.currentDir = DIRECTION.NORTH;}

        } else if(columnChange != 0){
            if(columnChange == 1){
                this.currentDir = DIRECTION.EAST;
            } else{this.currentDir = DIRECTION.WEST;}
        }

        setNewImage();

        int newRow = this.linkRow + rowChange;
        if (newRow < 0) {
            newRow = 0;
        }
        if (newRow >= gameBoard.getRowCount()) {
            newRow = gameBoard.getRowCount() - 1;
        }

        int newColumn = this.linkCol + columnChange;
        if (newColumn < 0) {
            newColumn = 0;
        }
        if (newColumn >= gameBoard.getColumnCount()) {
            newColumn = gameBoard.getColumnCount() - 1;
        }

        gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.GameBoard.CellValue.EMPTY);
        this.linkRow = newRow;
        this.linkCol = newColumn;
        gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.GameBoard.CellValue.LINK);
    }

}