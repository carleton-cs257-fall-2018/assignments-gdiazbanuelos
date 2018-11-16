package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Arrow extends Rectangle {

    private int arrowRow;
    private int arrowCol;
    private Image stance;
    private Player.DIRECTION currentDir;
    private int vel_x;
    private int vel_y;
    private boolean delete;

    // Image locations for the sprites
    private Image southStance = new Image("/res/arrowSouth.png");
    private Image northStance = new Image("/res/arrowNorth.png");
    private Image eastStance = new Image("/res/arrowEast.png");
    private Image westStance = new Image("/res/arrowWest.png");

    public Arrow(Player link){
        this.delete = false;
        this.currentDir = link.getCurrentDir();
        if(this.currentDir == Player.DIRECTION.NORTH){
            this.vel_y = -1;
            this.vel_x = 0;
        } else if(this.currentDir == Player.DIRECTION.SOUTH){
            this.vel_y = 1;
            this.vel_x = 0;
        } else if(this.currentDir == Player.DIRECTION.EAST){
            this.vel_y = 0;
            this.vel_x = 1;
        } else {
            this.vel_y = 0;
            this.vel_x = -1;
        }
        this.arrowCol = link.getLinkCol() + this.vel_x;
        this.arrowRow = link.getLinkRow() + this.vel_y;
        setNewImage();
    }

    public Player.DIRECTION getCurrentDir(){return this.currentDir;}

    public Image getStance(){
        return this.stance;
    }

    public int getArrowRow(){
        return this.arrowRow;
    }

    public int getArrowCol(){
        return this.arrowCol;
    }

    public boolean isDelete(){
        return this.delete;
    }

    public void stopMovement(){
        this.vel_y = 0;
        this.vel_x = 0;
    }

    private void setNewImage(){
        if(this.getCurrentDir() == Player.DIRECTION.SOUTH){
            this.stance = this.southStance;
        } else if(this.getCurrentDir() == Player.DIRECTION.NORTH){
            this.stance = this.northStance;
        } else if(this.getCurrentDir() == Player.DIRECTION. EAST){
            this.stance = this.eastStance;
        } else{this.stance = this.westStance;}
    }

    public void moveBy(GameBoard gameBoard){
        if(this.arrowRow >= 19 || this.arrowRow <= 1){
            this.delete = true;
        } else if(this.arrowCol <= 1 || this.arrowCol >= 32 ){
            this.delete = true;
        }
        gameBoard.changeCell(this.getArrowRow(), this.getArrowCol(),  GameBoard.CellValue.EMPTY);
        this.arrowRow = this.getArrowRow() + this.vel_y;
        this.arrowCol = this.getArrowCol() + this.vel_x;
        gameBoard.changeCell(this.getArrowRow(),this.getArrowCol(), zelda.GameBoard.CellValue.ARROW);
    }
}
