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
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 60.0;

    @FXML private AnchorPane gameBoard;
    @FXML private Model link;
    @FXML private Rectangle treasureChest_one;
    @FXML private Button pauseButton;
    @FXML private Label scoreLabel;
    @FXML private Label healthLabel;

    // Images locations for the sprites
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

    private Model model;
    private Timer timer;

    public Controller() {
        model = new Model();
    }

    public void initialize() {
        this.link.setFill(new ImagePattern(this.southStance));
        this.model.setPaused(false);
        this.model.setScore(0);
        this.model.setHealth(6);
        this.scoreLabel.setText(String.format("Bounces: %d", this.model.getScore()));
        this.healthLabel.setText(String.format("Health: %d", this.model.getHealth()));
        this.treasureChest_one.setFill(new ImagePattern(chest));
        this.startTimer();
    }

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

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateAnimation() {

    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        double paddlePositionX = this.link.getLayoutX();
        double stepSize = 15.0;
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            // move paddle left
            this.link.setImg(westStance);
            if (paddlePositionX > stepSize) {
                this.link.setLayoutX(this.link.getLayoutX() - stepSize);
            } else {
                this.link.setLayoutX(0);
            }
            keyEvent.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            // move paddle right
            this.link.setImg(eastStance);
            if (paddlePositionX + this.link.getWidth() + stepSize < this.gameBoard.getWidth()) {
                this.link.setLayoutX(this.link.getLayoutX() + stepSize);
            } else {
                this.link.setLayoutX(this.gameBoard.getWidth() - this.link.getWidth());
            }
            keyEvent.consume();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            // move paddle up
            this.link.setImg(northStance);
            this.link.setLayoutY(this.link.getLayoutY() - stepSize);
            keyEvent.consume();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            // move paddle up
            this.link.setImg(southStance);
            this.link.setLayoutY(this.link.getLayoutY() + stepSize);
            keyEvent.consume();
        } else if (code == KeyCode.SPACE) {
            this.treasureChest_one.setFill(new ImagePattern(chest_open));
            keyEvent.consume();
        }
    }

    public void onPauseButton(ActionEvent actionEvent) {
        if (this.model.isPaused()){
            this.pauseButton.setText("Pause");
            this.startTimer();
        } else {
            this.pauseButton.setText("Continue");
            this.timer.cancel();
        }
        this.model.setPaused(!this.model.isPaused());
    }
}
