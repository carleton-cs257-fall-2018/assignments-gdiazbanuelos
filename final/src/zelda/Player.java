/**
 * Player.java
 * Gustavo Diaz, 2018
 *
 * The Player model for the character "Link"
 *
 */

package zelda;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Player extends Rectangle {

    private int linkRow;
    private int linkCol;
    private Image stance;
    private DIRECTION currentDir;
    private boolean canAttack;
    private boolean gameOver;
    private Clip gameoverSound;

    // Image locations for the sprites
    private Image southStance = new Image("/res/south.png");
    private Image northStance = new Image("/res/north.png");
    private Image eastStance = new Image("/res/east.png");
    private Image westStance = new Image("/res/west.png");

    public enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;
    }

    /**
     * Creates the player model with the param constraints
     * @param row
     * @param col
     * @param direction
     */
    public Player(int row, int col, DIRECTION direction){
        this.linkRow = row;
        this.linkCol = col;
        this.currentDir = direction;
        this.canAttack = true;
        this.gameOver = false;
        setNewImage();
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
     * returns the current image of the player
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

    public boolean getCanAttack(){
        return this.canAttack;
    }

    /**
     * Sets the boolean canAttack to the param given
     * @param canAttack
     */
    public void setCanAttack(boolean canAttack){
        this.canAttack = canAttack;
    }

    /**
     * Sets the new image of the model depending on the Enum direction it is facing
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
     * Changes the player row and col on the GameBoard by the param values
     * If Link touches and enemy he is killed and calls the gameOverSound function
     * to indicate to the player died and sets the gameOver boolean to true
     *
     * @param rowChange can only move by increments of one in the positive
     *                  or negative direction
     * @param columnChange can only move by increments of one in the positive
     *      *              or negative direction
     * @param gameBoard the GameBoard model is passed only to store any changes
     *                  made to the models that are stored in the GameBaord
     */
    public void moveLinkBy(int rowChange, int columnChange, GameBoard gameBoard) {
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
        if (newColumn >= gameBoard.getColumnCount() - 6) {
            newColumn = gameBoard.getColumnCount() - 6;
        }

        gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.GameBoard.CellValue.EMPTY);
        this.linkRow = newRow;
        this.linkCol = newColumn;

        if(gameBoard.getCellValue(this.getLinkRow(), this.getLinkCol()) == GameBoard.CellValue.ENEMY){
            gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.GameBoard.CellValue.SCRAPHEAP);
            setGameOver();
        } else {
            gameBoard.changeCell(this.getLinkRow(), this.getLinkCol(), zelda.GameBoard.CellValue.LINK);
        }
    }

    /**
     * Retunrs the game over status of the game
     * @return
     */
    public boolean isGameOver(){
        return this.gameOver;
    }

    /**
     * Sets the gameOver boolean to true
     */
    public void setGameOver(){
        this.gameOver = true;

    }

    /**
     * In the moveLinkBy function, it checks if link collides with an enemy
     * Then creates an audioclip and plays the gameover.wav
     */
    public void playGameOverSound() {
        String song = "./src/res/gameover.wav";
        if (gameoverSound == null) {
            try {
                gameoverSound = AudioSystem.getClip();
                gameoverSound.open(AudioSystem.getAudioInputStream(new File(song)));
                gameoverSound.start();
            } catch (Exception exc) {
            }
        }
    }

}
