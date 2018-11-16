package zelda;

import javafx.scene.shape.Rectangle;

public class GameBoard extends Rectangle {
    public enum CellValue {
        EMPTY, LINK, ENEMY, SCRAPHEAP, ARROW;
    }

    private CellValue[][] cells;

    private int score;
    private boolean gameOver;
    private boolean paused;
    private int rowCount;
    private int columnCount;

    /**
     * Creates gameboard model and sets the col and row counts and adds the player and enemy
     * @param rowCount
     * @param columnCount
     * @param link
     * @param enemy
     */
    public GameBoard(int rowCount, int columnCount, Player link, Enemy enemy) {
        this.cells = new CellValue[rowCount][columnCount];
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.initializeLevel(link, enemy);
    }

    /**
     * Creates cellValue grid with empty cells and then places the player and enemy
     * @param link
     * @param enemy
     */
    private void initializeLevel(Player link, Enemy enemy) {
        // Empty all the cells
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                this.cells[row][column] = CellValue.EMPTY;
            }
        }

        // Place the runner
        this.cells[link.getLinkRow()][link.getLinkCol()] = CellValue.LINK;
        this.cells[enemy.getRow()][enemy.getCol()] = CellValue.ENEMY;
    }

    /**
     * returns the cell value of the grid
     * @param row
     * @param col
     * @return
     */
    public CellValue getCellValue(int row, int col){
        return this.cells[row][col];
    }

    /**
     * returns number of rows of the grid
     * @return
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * return number of columns of the grid
     * @return
     */
    public int getColumnCount() {
        return this.columnCount;
    }

    /**
     * sets the number of row counts
     * @param rowCount
     */
    public void setrowCount(int rowCount){
        this.rowCount = rowCount;
    }

    /**
     * sets the number of column
     * @param columnCount
     */
    public void setColumnCount(int columnCount){
        this.columnCount = columnCount;
    }

    /**
     * returns the current score
     * @return
     */
    public int getScore(){
        return this.score;
    }

    /**
     * returns if the is paused
     * @return
     */
    public boolean isPaused(){
        return paused;
    }

    /**
     * sets the score
     * @param newScore
     */
    public void setScore(int newScore){
        this.score = newScore;
    }

    /**
     * adds and subtracts to the current score
     * @param add
     */
    public void addScore(int add){
        this.score += add;
    }

    /**
     * sets the game over status of the game
     * @param gameOver
     */
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    /**
     * returns if the game is over
     * @return
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * sets if the game is paused
     * @param setPause
     */
    public void setPaused(boolean setPause){
        this.paused = setPause;
    }

    /**
     * returns the cells data of the game board
     * @return
     */
    public CellValue[][] getCells(){
        return this.cells;
    }

    /**
     * changes cell values of the game board
     * @param row
     * @param col
     * @param newCell
     */
    public void changeCell(int row, int col, CellValue newCell){
        this.cells[row][col] = newCell;
    }

}
