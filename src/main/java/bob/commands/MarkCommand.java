package bob.commands;

// This class is adapted from the package structure in:
// https://github.com/juneha1120/ip/tree/master/src/main/java/chillguy/commands.
// The package structure and the OOP logic (related to commands in Parser.java) are inspired by the above repository,
// but the methods and logic within this class were created independently.

import bob.BobException;
import bob.Storage;
import bob.TaskList;
import bob.task.Task;

/**
 * Represents a MarkCommand that has been called by the user.
 */
public class MarkCommand {

    /**
     * An immutable string containing the header to be printed when the mark command is used.
     */
    public static final String MARK_HEADER = "Nice! I've marked this task as done:\n";

    private TaskList tasks;
    private Storage storage;
    private String filePath;

    /**
     * Creates a new instance of a "mark" command.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     * @param filePath File path of the file containing information about the task list.
     */
    public MarkCommand(TaskList tasks, Storage storage, String filePath) {
        this.tasks = tasks;
        this.storage = storage;
        this.filePath = filePath;
    }

    /**
     * Executes the "mark" command.
     *
     * @return A string containing the information of the marked task.
     */
    public String execute(String input) throws BobException {
        int indexToMark = Integer.valueOf(input.substring(5));
        indexToMark--;
        Task taskToMark;

        try {
            taskToMark = tasks.get(indexToMark);
            taskToMark.markAsDone();
        } catch (IndexOutOfBoundsException e) {
            throw new BobException("The index provided must be less than the "
                    + "number of tasks currently in the task list.");
        }

        try {
            Task firstTask = tasks.get(0);
            storage.writeToFile(filePath, firstTask);
            for (int i = 1; i < tasks.getCount(); i++) {
                Task task = tasks.get(i);
                storage.appendToFile(filePath, task);
            }
        } catch (BobException e) {
            return "Unable to write to file.";
        }

        return MARK_HEADER + taskToMark.toString();
    }
}
