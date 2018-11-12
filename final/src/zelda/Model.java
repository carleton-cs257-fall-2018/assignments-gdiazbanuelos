package zelda;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Model extends Rectangle {

    private int score;
    private int health;
    private boolean gameOver;
    private boolean paused;

    public Model() {
    }

    /**
     * Retuns current score
     * @return score
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns current health of object
     * @return health
     */
    public int getHealth(){
        return health;
    }

    /**
     * Returns if the game is currently paused
     * @return paused
     */
    public boolean isPaused(){
        return paused;
    }

    /**
     * Sets the score for the game, used for initialing
     * @param this.score
     */
    public void setScore(int newScore){
        this.score = newScore;
    }

    /**
     * Adds points to the total score
     * @param this.score
     */
    public void addScore(int add){
        this.score += add;
    }

    /**
     * Sets the health of the object
     * @param newHealth
     */
    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    /**
     * Adds and subtracts to the current health of the object
     * @param add
     */
    public void addHealth(int add){
        this.health += add;
    }

    /**
     * Sets the gameOver status to the param
     * @param gameOver
     */
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    /**
     * Returns the current status of gameOver
     * @return
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Sets the pause status to the param
     * @param setPause
     */
    public void setPaused(boolean setPause){
        this.paused = setPause;
    }

    /**
     * Fills the Model object to the image that is passed in as the parameter
     * @param img
     */
    public void setImg(Image img){
        this.setFill(new ImagePattern(img));
    }

}
