package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {

    private int enemyRow;
    private int enemyCol;
    private int health;
    private Image stance;
    private DIRECTION currentDir;

    // Image locations for the sprites
    private Image southStance = new Image("/res/south.png");
    private Image northStance = new Image("/res/north.png");
    private Image eastStance = new Image("/res/east.png");
    private Image westStance = new Image("/res/west.png");

    public enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;
    }

    /**
     * Creates the enemy model with the param constraints
     * @param row
     * @param col
     * @param health
     * @param direction
     */
    public Enemy(int row, int col, int health, DIRECTION direction){
        this.enemyRow = row;
        this.enemyCol = col;
        this.currentDir = direction;
        this.health = health;
        setNewImage();
    }

    /**
     * returns the current row of the enemy model
     * @return
     */
    public int getRow(){
        return this.enemyRow;
    }

    /**
     * return the current col of the enemy model
     * @return
     */
    public int getCol(){
        return this.enemyCol;
    }

    /**
     * sets the enemy row to the param
     * @param row
     */
    public void setEnemyRow(int row){
        this.enemyRow = row;
    }

    /**
     * set the enemy col to the param
     * @param col
     */
    public void setEnemyCol(int col){
        this.enemyCol = col;
    }

    /**
     * sets the enemy health to the param
     * @param newHealth
     */
    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    /**
     * adds the param to the current enemy health
     * @param add
     */
    public void addHealth(int add){
        this.health += add;
    }

    /**
     * returns the current health of the enemy model
     * @return
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * sets the image of enemy to the param image
     * @param img
     */
    public void setImg(Image img){
        this.stance = img;
    }

    /**
     * retuns the current image of the enemy model
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
     * changes the row and col of the enemy model on the gameboard by the param values
     * @param rowChange
     * @param columnChange
     * @param gameBoard
     */
    public void moveBy(int rowChange, int columnChange, GameBoard gameBoard) {
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

        int newRow = this.enemyRow + rowChange;
        if (newRow < 0) {
            newRow = 0;
        }
        if (newRow >= gameBoard.getRowCount()) {
            newRow = gameBoard.getRowCount() - 1;
        }

        int newColumn = this.enemyCol + columnChange;
        if (newColumn < 0) {
            newColumn = 0;
        }
        if (newColumn >= gameBoard.getColumnCount()) {
            newColumn = gameBoard.getColumnCount() - 1;
        }

        gameBoard.changeCell(this.getRow(), this.getCol(), zelda.GameBoard.CellValue.EMPTY);
        this.enemyRow = newRow;
        this.enemyCol = newColumn;
        gameBoard.changeCell(this.getRow(), this.getCol(), zelda.GameBoard.CellValue.ENEMY);
    }

}
