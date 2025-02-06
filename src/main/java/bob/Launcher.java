package bob;

// code adapted from https://se-education.org/guides/tutorials/javaFxPart3.html

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
