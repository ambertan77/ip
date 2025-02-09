package bob.gui;

// code adapted from https://se-education.org/guides/tutorials/javaFxPart4.html

import bob.Bob;
import bob.BobException;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Bob bob = new Bob("./data/tasks.txt");


    /**
     * Starts up the GUI for Bob.
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     *
     * @throws BobException If an error has occurred during execution of user's command.
     */
    @Override
    public void start(Stage stage) throws BobException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBob(bob);  // inject the Bob instance
            stage.show();
            fxmlLoader.<MainWindow>getController().greet(bob);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}