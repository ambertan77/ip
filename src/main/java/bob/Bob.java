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
     * Creates a new instance of Bob.
     *
     * @param filePath File path of the file in hard disk containing previous data.
     */
    public Bob(String filePath) {
        this.filePath = filePath;
        this.tasks = new TaskList(new ArrayList<Task>(100));
        this.storage = new Storage(this.tasks);
        this.parser = new Parser(this.tasks, this.storage);
        this.ui = new Ui();
    }

    /**
     * Loads the file in the specified file path.
     * This method kick-starts all interactions between the chatbot and the user.
     *
     * @throws Exception If the user inputs commands in the wrong format.
     */
    public void run() throws Exception {
        try {
            storage.loadFile(filePath);
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        this.ui.interact(this.parser);
    }

    // JavaDoc comments adapted from:
    // https://stackoverflow.com/questions/27696538/how-should-the-parameter-of-the-main-method-be-documented
    /**
     * The main method. This is the entry point for all
     * interactions between the user and the chatbot.
     *
     * @param args The command line arguments.
     * @throws Exception If any error occurs during user interaction.
     **/
    public static void main(String[] args) throws Exception {
        new Bob("./data/tasks.txt").run();
    }
}
