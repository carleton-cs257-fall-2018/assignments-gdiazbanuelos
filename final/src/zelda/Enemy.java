/**
 * Enemy.java
 * Gustavo Diaz, 2018
 *
 * The Enemy model for the "goblins"
 *
 */

package zelda;

import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {

    private int enemyRow;
    private int enemyCol;
    private DIRECTION currentDir;
    private boolean delete;


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
     * Returns if the enemy has been deleted as a boolean
     * @return
     */
    public boolean isDelete(){
        return this.delete;
    }

    /**
     * Sets delete function to param input
     * @param isDelete
     */
    public void setDelete(boolean isDelete){
        this.delete = isDelete;
    }

    /**
     * changes the row and col of the enemy model on the GameBoard by the param values
     * @param rowChange movement is only in increments of positive or negative one
     * @param columnChange movement is only in increments of positive or negative one
     * @param gameBoard GameBoard is passed along to store and changes in position of the
     *                  enemy model
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
