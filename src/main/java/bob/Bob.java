package bob;

import java.util.ArrayList;

import bob.task.Task;

/**
 * Represents the chatbot, Bob, that the user is interacting with.
 */
public class Bob {
    private String filePath;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;
    private Ui ui;

    /**
     * Creates a new instance of Bob. Loads the file containing data in the hard disk.
     *
     * @param filePath File path of the file in hard disk containing previous data.
     */
    public Bob(String filePath) {
        this.filePath = filePath;
        this.tasks = new TaskList(new ArrayList<Task>(100));
        this.storage = new Storage(this.tasks);
        this.parser = new Parser(this.tasks, this.storage, this);
        this.ui = new Ui();
    }

    /**
     * Loads the file in the specified file path.
     * This method kick-starts all interactions between the chatbot and the user.
     *
     * @return A string containing the greeting message, or error message if an error has occurred.
     */
    public String run() {
        assert this.storage != null : "Bob's storage should be initialised before it is ran";
        assert this.ui != null : "Bob's UI should be initialised before it is ran";
        try {
            this.storage.loadFile(filePath);
        } catch (BobException e) {
            return e.getMessage();
        }
        return this.ui.greet();
    }

    // JavaDoc comments adapted from:
    // https://stackoverflow.com/questions/27696538/how-should-the-parameter-of-the-main-method-be-documented
    /**
     * The main method. This is the entry point for all
     * interactions between the user and the chatbot.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Bob("./data/tasks.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @return Bob's response to the user's command.
     */
    public String getResponse(String input) {
        assert this.ui != null : "Bob's UI should be initialised before it is ran";
        assert this.parser != null : "Bob's parser should be initialised before it is ran";
        return this.ui.interactWithErrorsHandled(this.parser, input);
    }

    /**
     * Returns the file path of the file containing the data of items in the task list.
     *
     * @return The file path for the file stored in the hard disk.
     */
    public String getFilePath() {
        return this.filePath;
    }
}
