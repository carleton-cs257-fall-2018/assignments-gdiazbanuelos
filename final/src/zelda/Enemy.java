package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {

    private int enemyRow;
    private int enemyCol;
    private int health;
    private Image stance;
    private DIRECTION currentDir;
    private boolean delete;

    // Image locations for the sprites
    private Image southStance = new Image("/res/southEnemy.png");
    private Image northStance = new Image("/res/northEnemy.png");
    private Image eastStance = new Image("/res/eastEnemy.png");
    private Image westStance = new Image("/res/westEnemy.png");

    public enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;
    }

    /**
     * Creates the enemy model with the param constraints
     * @param row
     * @param col
     * @param direction
     */
    public Enemy(int row, int col, DIRECTION direction){
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

    public boolean isDelete(){
        return this.delete;
    }

    public void setDelete(boolean isDelete){
        this.delete = isDelete;
    }

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
    public void moveBy(int rowChange, int columnChange, GameBoard gameBoard, Player link) {
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

        if(gameBoard.getCellValue(this.getRow(), this.getCol()) == GameBoard.CellValue.LINK){
            gameBoard.changeCell(this.getRow(), this.getCol(), zelda.GameBoard.CellValue.SCRAPHEAP);
            link.setGameOver();
        }
        gameBoard.changeCell(this.getRow(), this.getCol(), zelda.GameBoard.CellValue.ENEMY);
    }

}
