package bob.commands;

import bob.Bob;
import bob.BobException;
import bob.Storage;
import bob.TaskList;
import bob.task.Task;

/**
 * Represents a RemoveDuplicateCommand that has been called by the user.
 */
public class RemoveDuplicatesCommand {

    private TaskList tasks;
    private Storage storage;
    private Bob bob;
    private String filePath;
    private boolean hasDuplicates;

    /**
     * Creates a new instance of a "remove duplicates" command.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     * @param bob The current instance of the Bob chatbot.
     */
    public RemoveDuplicatesCommand(TaskList tasks, Storage storage, Bob bob) {
        this.tasks = tasks;
        this.storage = storage;
        this.bob = bob;
        this.filePath = bob.getFilePath();
        this.hasDuplicates = tasks.detectDuplicates();
    }

    /**
     * Executes the "remove duplicates" command.
     *
     * @return A string notifying the user whether duplicates have been removed.
     */
    public String execute() {
        if (!hasDuplicates) {
            return "No duplicates found.";
        }

        for (int i = 0; i < tasks.getCount(); i++) {
            for (int j = i + 1; j < tasks.getCount(); j++) {
                if (tasks.get(i).toString().equals(tasks.get(j).toString())) {
                    tasks.remove(tasks.get(j));
                    tasks.decrementCount();
                }
            }
        }
        tasks.resetDuplicates();

        try {
            Task firstTask = tasks.get(0);
            storage.writeToFile(filePath, firstTask);
            for (int i = 1; i < tasks.getCount(); i++) {
                Task task = tasks.get(i);
                if (storage.getIsNewFile()) {
                    storage.writeToFile(filePath, task);
                    storage.setIsNewFile(false);
                } else {
                    storage.appendToFile(filePath, task);
                }
            }
        } catch (BobException e) {
            return "Unable to write to file.";
        }

        return "All duplicates have been removed.";
    }

}
