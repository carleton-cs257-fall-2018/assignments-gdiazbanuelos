package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class enemy extends Rectangle {

    private int enemyRow;
    private int enemyCol;
    private int health;
    private Image stance;

    /**
     * Creates the enemy model with the param constraints
     * @param row
     * @param col
     * @param health
     * @param initStance
     */
    public enemy(int row, int col, int health, Image initStance){
        this.enemyRow = row;
        this.enemyCol = col;
        this.stance = initStance;
        this.health = health;
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
     * changes the row and col of the enemy model on the gameboard by the param values
     * @param rowChange
     * @param columnChange
     * @param gameBoard
     */
    public void moveBy(int rowChange, int columnChange, gameBoard gameBoard) {
        if (gameBoard.isGameOver()) {
            return;
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


        gameBoard.changeCell(this.getRow(), this.getCol(), zelda.gameBoard.CellValue.EMPTY);
        this.enemyRow = newRow;
        this.enemyCol = newColumn;
        gameBoard.changeCell(this.getRow(), this.getCol(), zelda.gameBoard.CellValue.ENEMY);
        //this.moveDaleksToFollowRunner();
    }

}
