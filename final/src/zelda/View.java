package zelda;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class View extends Group {
    public final static double CELL_WIDTH = 20.0;

    @FXML private int rowCount = 21;
    @FXML private int columnCount = 40;
    private Rectangle[][] cellViews;

    public View() {
    }

    /**
     * returns row count of the gameboard
     * @return
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * sets the row count to the param
     * @param rowCount
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    /**
     * returns the col count
     * @return
     */
    public int getColumnCount() {
        return this.columnCount;
    }

    /**
     * sets the col count to the param
     * @param columnCount
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }

    /**
     * Creates a rectangle grid based on the properties of the row and col count
     * Each section of the grid is its own rectangle
     */
    private void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new Rectangle[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX((double)column * CELL_WIDTH);
                    rectangle.setY((double)row * CELL_WIDTH);
                    rectangle.setWidth(CELL_WIDTH);
                    rectangle.setHeight(CELL_WIDTH);
                    this.cellViews[row][column] = rectangle;
                    this.getChildren().add(rectangle);
                }
            }
        }
    }

    /**
     * Compares the cells of the gameboard and displays those cells on the View grid
     * The view grid puts the player and enemy where they correspond on the x-y grid of
     * the view based on the gameboard param
     * @param gameBoard
     * @param link
     * @param enemy
     */
    public void update(gameBoard gameBoard, player link, enemy enemy) {
        assert gameBoard.getRowCount() == this.rowCount && gameBoard.getColumnCount() == this.columnCount;
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                gameBoard.CellValue cellValue = gameBoard.getCellValue(row, column);
                if (cellValue == zelda.gameBoard.CellValue.ENEMY) {
                    this.cellViews[row][column].setFill(new ImagePattern(enemy.getStance()));
                    //this.cellViews[row][column].setFill(Color.RED);
                } else if (cellValue == zelda.gameBoard.CellValue.SCRAPHEAP) {
                    this.cellViews[row][column].setFill(new ImagePattern(enemy.getStance()));
                    //this.cellViews[row][column].setFill(Color.BLUE);
                } else if (cellValue == zelda.gameBoard.CellValue.LINK) {
                    this.cellViews[row][column].setFill(new ImagePattern(link.getStance()));
                } else {
                    this.cellViews[row][column].setFill(Color.WHITE);
                }
            }
        }
    }


}
