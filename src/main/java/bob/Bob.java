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
     * @throws BobException If there is an error loading the file in the hard disk.
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
     * @throws BobException If the file containing data in the hard disk cannot be loaded.
     */
    public String run() throws BobException {
        assert this.storage != null : "Bob's storage should be initialised before it is ran";
        assert this.ui != null : "Bob's UI should be initialised before it is ran";
        try {
            this.storage.loadFile(filePath);
        } catch (Exception e) {
            throw new BobException(e.getMessage());
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
     * @throws BobException If any error occurs during user interaction.
     **/
    public static void main(String[] args) throws BobException {
        new Bob("./data/tasks.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @return Bob's response to the user's command.
     * @throws BobException If an error has occurred during execution of user's command.
     */
    public String getResponse(String input) throws BobException {
        assert this.ui != null : "Bob's UI should be initialised before it is ran";
        assert this.parser != null : "Bob's parser should be initialised before it is ran";
        return this.ui.interact(this.parser, input);
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
