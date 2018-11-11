package zelda;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("zelda.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Kinda Zelda");
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        scene.setOnKeyPressed(controller);
        primaryStage.show();

        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
