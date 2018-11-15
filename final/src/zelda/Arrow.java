package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Arrow extends Rectangle {

    private int arrowRow;
    private int arrowCol;
    private Image img;

    public enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;
    }

    public Arrow(int row, int col, DIRECTION direction){
        this.arrowRow = row;
        this.arrowCol = col;
        //moveArrow();
    }

    public int getArrowRow() {
        return this.arrowRow;
    }

    public int getArrowCol(){
        return this.arrowCol;
    }

    public void moveArrow(int row, int col, GameBoard gameBoard){
        gameBoard.changeCell(this.arrowRow, this.arrowCol, zelda.GameBoard.CellValue.EMPTY);
        gameBoard.changeCell(this.arrowRow + row, this.arrowCol + col, zelda.GameBoard.CellValue.ARROW);
    }
}
