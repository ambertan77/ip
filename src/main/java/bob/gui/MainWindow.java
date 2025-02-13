package bob.gui;

// code adapted from https://se-education.org/guides/tutorials/javaFxPart4.html

import bob.Bob;
import bob.BobException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bob bob;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image bobImage = new Image(this.getClass().getResourceAsStream("/images/RoBob.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Duke instance
     */
    public void setBob(Bob b) {
        bob = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws BobException {
        assert this.bob != null : "Bob should be initialised before it can respond to the user";
        assert userImage != null : "image of the user cannot be null";
        assert bobImage != null : "image of the Bob cannot be null";
        String input = userInput.getText();
        String response = bob.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBobDialog(response, bobImage)
        );
        userInput.clear();
    }

    /**
      * Creates a dialog box containing greeting message, and appends it to
      * the dialog container.
      *
      * @throws BobException If an error has occurred during execution of user's command.
      */
    @FXML
    public void greet(Bob b) throws BobException {
        assert this.bob != null : "Bob should be initialised before it can greet the user";
        String greeting = b.run();
        assert greeting != null : "Bob's greeting cannot be null";
        assert bobImage != null : "image of the Bob cannot be null";
        dialogContainer.getChildren().addAll(
                DialogBox.getBobDialog(greeting, bobImage)
        );
        userInput.clear();
    }
}

