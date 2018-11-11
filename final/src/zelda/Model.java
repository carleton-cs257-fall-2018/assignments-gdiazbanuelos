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

    public int getScore(){
        return score;
    }

    public int getHealth(){
        return health;
    }

    public boolean isPaused(){
        return paused;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public void addScore(int add){
        this.score += add;
    }

    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    public void addHealth(int add){
        this.health += add;
    }

    public void setGameOver(){
        this.gameOver = true;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void setPaused(boolean setPause){
        this.paused = setPause;
    }

    public void setImg(Image img){
        this.setFill(new ImagePattern(img));
    }

}
