/**
 * Controller.java
 * Gustavo Diaz, 2018
 *
 * The Controller for the zelda game.
 *
 */

package zelda;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;


/**
 *Controller.java
 * The controller that takes keyboard inputs for GameBoard.java
 *
 * @author Gustavo Diaz Banuelos
 */
public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 60.0;
    final private double ENEMY_UPDATE_PER_SECOND = 10;

    @FXML private View zeldaView;
    @FXML private Label scoreLabel;
    @FXML private Label deathLabel;
    @FXML private int rowCount  = 21;
    @FXML private int columnCount = 40;

    private Player link;
    //private Enemy goblin;
    private ArrayList<Enemy> goblinsList = new ArrayList<>();
    private int level = 1;
    private Arrow arrow;
    private GameBoard gameBoard;
    private Timer timer;
    private Timer timer_two;
    private Clip clip;

    public Controller() {
    }

    /**
     * Initializes the player, enemy, and  GameBoard models
     * Link is always placed in the same location
     * Creates number of enemies based on the current level of the game
     * and places them randomly onto the board
     */
    public void initialize() {
        // Instantiate the board and the player
        link = new Player(6,5, Player.DIRECTION.SOUTH);
        for(int i = 0; i < level + 1; i++){
            Random random = new Random();
            int randomIntX = random.nextInt(30 + 1 + 1);
            int randomIntY = random.nextInt(18 + 1 + 1);
            Enemy goblin = new Enemy(randomIntY, randomIntX, Enemy.DIRECTION.SOUTH);
            goblinsList.add(goblin);
        }
        gameBoard = new GameBoard(rowCount, columnCount, link, goblinsList, this.level);

        // Set the properties of the board and player
        this.gameBoard.setPaused(false);
        this.gameBoard.setGameOver(false);
        this.gameBoard.setScore(0);
        this.scoreLabel.setText(String.format("Score: %d", this.gameBoard.getScore()));
        this.startTimer();
        this.startEnemy_timer();
        startSong();
    }

    /**
     * Creates a timer that calls updateAnimation based on timer settings
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                        if(arrow != null){
                            updateArrow();
                        }
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(8000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Create the timer that calls the updateEnemy based timer setting
     */
    private void startEnemy_timer() {
        this.timer_two = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateEnemy();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(8000.0 / ENEMY_UPDATE_PER_SECOND);
        this.timer_two.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateArrow(){
        if(this.arrow.isDelete() != true){
            this.arrow.moveBy(gameBoard, this.link, this.goblinsList);
        } else {
            this.link.setCanAttack(true);
        }
    }

    /**
     * Updates the enemy to move randomly
     * The player and the enemy update on two different timers
     * so that the enemy does not move extremely fast compared
     * the player
     */
    private void updateEnemy(){
        for(int i = 0; i < goblinsList.size() -1; i++) {
            if(goblinsList.get(i).isDelete() != true) {
                Random random = new Random();
                int randomIntX = random.nextInt(1 + 1 + 1) - 1;
                int randomIntY = 0;
                if (randomIntX == 0) {
                    randomIntY = random.nextInt(1 + 1 + 1) - 1;
                }
                goblinsList.get(i).moveBy(randomIntX, randomIntY, gameBoard, this.link);
            }
        } if(goblinsList.size() == 1){
            nextLevel();
        }
    }

    /**
     * Updates the models based on the timer
     * Generates a random movement for the enemy
     * TO-DO: Implement better move alg
     */
    private void updateAnimation() {
        if(this.link.isGameOver() != true) {
            zeldaView.update(gameBoard, link, goblinsList, arrow);
            this.scoreLabel.setText(String.format("Score: %d", this.gameBoard.getScore()));
        } else{
            zeldaView.update(gameBoard, link, goblinsList, arrow);
            this.deathLabel.setText(String.format("GAME OVER"));
            this.clip.stop();
            this.link.playGameOverSound();
            onPauseButton();
        }
    }

    /**
     * Listens for key events and updates the player model
     * @param keyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            // move player left
            this.link.moveLinkBy(0, -1, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            // move player right
            this.link.moveLinkBy(0, 1, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            // move player up
            this.link.moveLinkBy(-1, 0, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            // move player up
            this.link.moveLinkBy(1, 0, gameBoard);
            keyEvent.consume();
        } else if(code == KeyCode.SPACE) {
            if(this.link.getCanAttack() != false) {
                arrow = new Arrow(this.link);
                startArrowSound();
                this.link.setCanAttack(false);
                keyEvent.consume();
            }
            keyEvent.consume();
        } else if(code == KeyCode.G){
            newGame();
            keyEvent.consume();
        } else if(code == KeyCode.P){
            onPauseButton();
            keyEvent.consume();
        } else {
            this.link.moveLinkBy(0, 0, gameBoard);
        }
    }

    /**
     * Increases the level of the game when the current level is beat
     * Cancels all the timers and resets the GameOver label
     * Stops the music and calls the initialize function
     * to create a new GameBoard for the next level
     */
    public void nextLevel(){
        this.level += 1;
        this.timer.cancel();
        this.timer_two.cancel();
        this.arrow = null;
        this.deathLabel.setText("");
        this.clip.stop();
        this.goblinsList.clear();
        initialize();
    }

    /**
     * Cancels all the timers and resets the GameOver label
     * Stops the music and calls the initialize function
     * to create a new GameBoard for a new game
     */
    public void newGame(){
        this.timer.cancel();
        this.timer_two.cancel();
        this.arrow = null;
        this.deathLabel.setText("");
        this.clip.stop();
        this.goblinsList.clear();
        initialize();
    }

    /**
     * Creates a clip that plays the overworld.wav
     * sound file to play in the background
     */
    public void startSong(){
        String song = "./src/res/overworld.wav";
        try
        {
            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(new File(song)));
            this.clip.start();
        }
        catch (Exception exc) {}
    }

    /**
     * Creates a clip that plays the arrowsound.wav
     * sound file to play when Link attacks
     */
    public void startArrowSound(){
        String song = "./src/res/arrowsound.wav";
        Clip arrowSound;
        try
        {
            arrowSound = AudioSystem.getClip();
            arrowSound.open(AudioSystem.getAudioInputStream(new File(song)));
            arrowSound.start();
        }
        catch (Exception exc) {}
    }

    /**
     * Pauses the game on button "P" press
     */
    public void onPauseButton() {
        if (this.gameBoard.isPaused()){
            this.startTimer();
            this.startEnemy_timer();
        } else {
            this.timer.cancel();
            this.timer_two.cancel();
        }
        this.gameBoard.setPaused(!this.gameBoard.isPaused());
    }



}
