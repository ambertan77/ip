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
 * Represents a DeleteCommand that has been called by the user.
 */
public class DeleteCommand {

    /**
     * An immutable string containing the header to be printed when the delete command is used.
     */
    public static final String DELETE_HEADER = "Noted! I have deleted this task:\n";
    private TaskList tasks;
    private Storage storage;
    private String filePath;

    /**
     * Creates a new instance of a "delete" command.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     * @param filePath File path of the file containing information about the task list.
     */
    public DeleteCommand(TaskList tasks, Storage storage, String filePath) {
        this.tasks = tasks;
        this.storage = storage;
        this.filePath = filePath;
    }

    /**
     * Writes the items in the task list to storage, provided there are items in the task list.
     *
     * @throws BobException If an error has occurred when writing to storage.
     */
    public void writeToStorage() throws BobException {
        storage.writeToFileWithStringInput(filePath, "");
        for (int i = 0; i < tasks.getCount(); i++) {
            Task task = tasks.get(i);
            storage.appendToFile(filePath, task);
        }
    }

    /**
     * Executes the "delete" command.
     *
     * @return A string containing the information of the deleted task.
     */
    public String execute(String input) throws BobException {
        String outputForDelete = DELETE_HEADER;

        int indexToDelete = Integer.valueOf(input.substring(7));
        indexToDelete--;

        try {
            Task taskToDelete = tasks.get(indexToDelete);
            outputForDelete = outputForDelete + taskToDelete.toString() + "\n";
            tasks.remove(taskToDelete);
            tasks.decrementCount();
        } catch (IndexOutOfBoundsException e) {
            throw new BobException("Please provide a number that is smaller than the number of items in the list.");
        }

        try {
            if (tasks.getCount() == 0) {
                storage.writeToFileWithStringInput(filePath, "");
                storage.setIsNewFile(true);
            } else {
                writeToStorage();
            }
        } catch (BobException e1) {
            return "Unable to write to file.";
        }

        return outputForDelete + "Now you have " + tasks.getCount() + " tasks in the list.";
    }
}
