package bob;

// code adapted from https://se-education.org/guides/tutorials/javaFxPart3.html

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image bobImage = new Image(this.getClass().getResourceAsStream("/images/RoBob.png"));
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
    public void start(Stage stage) {
        // Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();
        try {
            greet();
        } catch (BobException e) {
            System.out.println("Error loading up data in hard disk.");
        }

        // Formatting the window to look as expected

        stage.setTitle("Bob");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Handling user input
        sendButton.setOnMouseClicked((event) -> {
            try {
                handleUserInput();
            } catch (BobException e) {
                System.out.println("An error has occurred while running Bob.");
                e.printStackTrace();
            }
        });
        userInput.setOnAction((event) -> {
            try {
                handleUserInput();
            } catch (BobException e) {
                System.out.println("An error has occurred while running Bob.");
                e.printStackTrace();
            }
        });

        // Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        //More code to be added here later
    }

    /**
     * Creates a dialog box containing greeting message, and appends it to
     * the dialog container.
     *
     * @throws BobException If an error has occurred during execution of user's command.
     */
    private void greet() throws BobException{
        String greeting = bob.run();
        dialogContainer.getChildren().addAll(
                DialogBox.getBobDialog(greeting, bobImage)
        );
        userInput.clear();
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     *
     * @throws BobException If an error has occurred during execution of user's command.
     */
    private void handleUserInput() throws BobException {
        String userText = userInput.getText();
        String dukeText = bob.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBobDialog(dukeText, bobImage)
        );
        userInput.clear();
    }
}