package zelda;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 *Controller.java
 * The controller that takes keyboard inputs for gameBoard.java
 *
 * @author Gustavo Diaz Banuelos
 */
public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 15.0;

    @FXML private View zeldaView;
    @FXML private Button pauseButton;
    @FXML private Label scoreLabel;
    @FXML private Label healthLabel;
    @FXML private int rowCount  = 21;
    @FXML private int columnCount = 40;

    // Image locations for the sprites
    private Image southStance = new Image("/res/south.png");
    private Image northStance = new Image("/res/north.png");
    private Image eastStance = new Image("/res/east.png");
    private Image westStance = new Image("/res/west.png");
    private Image attackWest = new Image("/res/arrowWest.png");
    private Image attackEast = new Image("/res/arrowEast.png");
    private Image attackNorth = new Image("/res/arrowNorth.png");
    private Image attackSouth = new Image("/res/arrowSouth.png");
    private Image chest = new Image("/res/chest.png");
    private Image chest_open = new Image("/res/chest_open.png");
    private Image enemy_south = new Image("/res/enemy.png");

    private player link;
    private enemy ganon;
    private gameBoard gameBoard;
    private Timer timer;


    public Controller() {
    }

    /**
     * Initializes the player, enemy, and  gameboard models
     */
    public void initialize() {
        // Instantiate the board and the player
        link = new player(6,5, 6, southStance);
        ganon = new enemy(10,10, 3, enemy_south);
        gameBoard = new gameBoard(rowCount, columnCount, link, ganon);

        // Set the properties of the board and player
        this.gameBoard.setPaused(false);
        this.gameBoard.setScore(0);
        this.scoreLabel.setText(String.format("Score: %d", this.gameBoard.getScore()));
        this.healthLabel.setText(String.format("Health: %d", this.link.getHealth()));
        this.startTimer();
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
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(8000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Updates the models based on the timer
     * Generates a random movement for the enemy
     * TO-DO: Implement better move alg
     */
    private void updateAnimation() {
        zeldaView.update(gameBoard, link, ganon);
        Random random = new Random();
        int randomIntX = random.nextInt(1 + 1 + 1) - 1;
        int randomIntY = 0;
        if(randomIntX != 0){
            randomIntY = 0;
        } else{
            randomIntY = random.nextInt(1 + 1 + 1) - 1;
        }
        ganon.moveBy(randomIntX, randomIntY, gameBoard);
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
            this.link.setImg(westStance);
            this.link.moveLinkBy(0, -1, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            // move player right
            this.link.setImg(eastStance);
            this.link.moveLinkBy(0, 1, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            // move player up
            this.link.setImg(northStance);
            this.link.moveLinkBy(-1, 0, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            // move player up
            this.link.setImg(southStance);
            this.link.moveLinkBy(1, 0, gameBoard);
            keyEvent.consume();
        }
    }

    /**
     * Pauses the game on button click
     * @param actionEvent
     */
    public void onPauseButton(ActionEvent actionEvent) {
        if (this.gameBoard.isPaused()){
            this.pauseButton.setText("Pause");
            this.startTimer();
        } else {
            this.pauseButton.setText("Continue");
            this.timer.cancel();
        }
        this.gameBoard.setPaused(!this.gameBoard.isPaused());
    }
}
