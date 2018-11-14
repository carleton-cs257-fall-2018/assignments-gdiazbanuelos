package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class player extends Rectangle {

    private int linkRow;
    private int linkCol;
    private int health;
    private Image stance;

    /**
     * Creates the player model with the param constraints
     * @param row
     * @param col
     * @param health
     * @param initStance
     */
    public player(int row, int col, int health, Image initStance){
        this.linkRow = row;
        this.linkCol = col;
        this.stance = initStance;
        this.health = health;
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
     * sets the player image to the param image
     * @param img
     */
    public void setImg(Image img){
        this.stance = img;
    }

    /**
     * returns the current image of the player
     * @return
     */
    public Image getStance(){
        return this.stance;
    }

    /**
     * changes the player row and col on the gameboard by the param values
     * @param rowChange
     * @param columnChange
     * @param gameBoard
     */
    public void moveLinkBy(int rowChange, int columnChange, gameBoard gameBoard) {
        if (gameBoard.isGameOver()) {
            return;
        }

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


        gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.gameBoard.CellValue.EMPTY);
        this.linkRow = newRow;
        this.linkCol = newColumn;
        gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.gameBoard.CellValue.LINK);
        //this.moveDaleksToFollowRunner();
    }

}
