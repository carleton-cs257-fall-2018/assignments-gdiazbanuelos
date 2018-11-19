/**
 * Arrow.java
 * Gustavo Diaz, 2018
 *
 * The Arrow model for the "goblins"
 *
 */

package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

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

    public void changeDelete(boolean delete){
        this.delete = delete;
    }


    /**
     * Based on the Enum direction of Link,
     * It creates the arrow and gives sets
     * the appropriate image to the arrow
     */
    private void setNewImage(){
        if(this.getCurrentDir() == Player.DIRECTION.SOUTH){
            this.stance = this.southStance;
        } else if(this.getCurrentDir() == Player.DIRECTION.NORTH){
            this.stance = this.northStance;
        } else if(this.getCurrentDir() == Player.DIRECTION. EAST){
            this.stance = this.eastStance;
        } else{this.stance = this.westStance;}
    }

    /**
     * Moves the arrow in the direction that param Link is facing
     * Once the arrow is fired, moves in the direction until it
     * flies off the board, once the arrow is off the board Link
     * can attack again. If the arrow hits an enemy it calles the
     * playHitSound function
     * @param gameBoard GameBoard is passed along to update the location
     *                  of the arrow as it travels across the board
     * @param link      The arrow alters the attack state of Link
     *                  Once an arrow is created Link can no longer attack
     *                  until the arrow flies off the board
     * @param goblinsList List of the current enemies on the board
     */
    public void moveBy(GameBoard gameBoard, Player link, ArrayList<Enemy> goblinsList){
        if(this.arrowRow >= 21 || this.arrowRow <= -1){
            this.delete = true;
            link.setCanAttack(true);
        } else if(this.arrowCol <= -1 || this.arrowCol >= 35 ){
            this.delete = true;
            link.setCanAttack(true);
        }
        gameBoard.changeCell(this.getArrowRow(), this.getArrowCol(),  GameBoard.CellValue.EMPTY);
        this.arrowRow = this.getArrowRow() + this.vel_y;
        this.arrowCol = this.getArrowCol() + this.vel_x;
        if(gameBoard.getCellValue(this.arrowRow, this.arrowCol) == zelda.GameBoard.CellValue.ENEMY){
            for(int i = 0; i < goblinsList.size() -1; i++) {
                if(goblinsList.get(i).getRow()==this.arrowRow && goblinsList.get(i).getCol()==this.arrowCol){
                    goblinsList.get(i).setDelete(true);
                    goblinsList.remove(i);
                }
            }
            playHitSound();
            this.changeDelete(true);
            gameBoard.addScore(1);
            gameBoard.changeCell(this.getArrowRow(), this.getArrowCol(), GameBoard.CellValue.EMPTY);
        } else {
            gameBoard.changeCell(this.getArrowRow(), this.getArrowCol(), zelda.GameBoard.CellValue.ARROW);
        }
    }

    /**
     * When the arrow hits an enemy, moveBy calls this function
     * to create a clip that plays the kill.wav sound file
     */
    public void playHitSound(){
        String song = "./src/res/kill.wav";
        Clip clip;
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(song)));
            clip.start();
        }
        catch (Exception exc) {}


    }
}
