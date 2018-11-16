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

import static zelda.Player.DIRECTION.SOUTH;


/**
 *Controller.java
 * The controller that takes keyboard inputs for GameBoard.java
 *
 * @author Gustavo Diaz Banuelos
 */
public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 60.0;
    final private double ENEMY_UPDATE_PER_SECOND = 10;
    final private double ARROW_UPDATE_PER_SECOND = 60;

    @FXML private View zeldaView;
    @FXML private Button pauseButton;
    @FXML private Label scoreLabel;
    @FXML private Label healthLabel;
    @FXML private int rowCount  = 21;
    @FXML private int columnCount = 40;

    private Image attackWest = new Image("/res/arrowWest.png");
    private Image attackEast = new Image("/res/arrowEast.png");
    private Image attackNorth = new Image("/res/arrowNorth.png");
    private Image attackSouth = new Image("/res/arrowSouth.png");
    private Image chest = new Image("/res/chest.png");
    private Image chest_open = new Image("/res/chest_open.png");

    private Player link;
    private Enemy ganon;
    private Arrow arrow;
    private GameBoard gameBoard;
    private Timer timer;
    private Timer timer_two;


    public Controller() {
    }

    /**
     * Initializes the player, enemy, and  gameboard models
     */
    public void initialize() {
        // Instantiate the board and the player
        link = new Player(6,5, 6, Player.DIRECTION.SOUTH);
        ganon = new Enemy(10,10, 3, Enemy.DIRECTION.SOUTH);
        gameBoard = new GameBoard(rowCount, columnCount, link, ganon);

        // Set the properties of the board and player
        this.gameBoard.setPaused(false);
        this.gameBoard.setScore(0);
        this.scoreLabel.setText(String.format("Score: %d", this.gameBoard.getScore()));
        this.healthLabel.setText(String.format("Health: %d", this.link.getHealth()));
        this.startTimer();
        this.startEnemy_timer();
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

    private void startArrow_timer() {
        this.timer_two = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateArrow();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(8000.0 / ARROW_UPDATE_PER_SECOND);
        this.timer_two.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateArrow(){
        if(this.arrow.isDelete() != true){
            this.arrow.moveBy(gameBoard);
        } else {
            this.arrow.stopMovement();
        }
    }


    /**
     * Updates the enemy to move randomly
     * The player and the enemy update on two different timers
     * so that the enemy does not move extremely fast compared
     * the player
     */
    private void updateEnemy(){
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
     * Updates the models based on the timer
     * Generates a random movement for the enemy
     * TO-DO: Implement better move alg
     */
    private void updateAnimation() {
        zeldaView.update(gameBoard, link, ganon, arrow);
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
            //this.link.setImg(eastStance);
            this.link.moveLinkBy(0, 1, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            // move player up
            //this.link.setImg(northStance);
            this.link.moveLinkBy(-1, 0, gameBoard);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            // move player up
            //this.link.setImg(southStance);
            this.link.moveLinkBy(1, 0, gameBoard);
            keyEvent.consume();
        } else if(code == KeyCode.SPACE) {
            arrow = new Arrow(this.link);
            startArrow_timer();
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
