package bob.commands;

import bob.TaskList;

/**
 * Represents a CheckDuplicateCommand that has been called by the user.
 */
public class CheckDuplicatesCommand {
    private TaskList tasks;

    /**
     * Creates a new instance of a "check duplicates" command.
     *
     * @param tasks List of tasks the user has input.
     */
    public CheckDuplicatesCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes the "check duplicates" command.
     *
     * @return A string notifying the user whether there are duplicates.
     */
    public String execute() {
        if (tasks.detectDuplicates()) {
            return "Duplicates exist. Type 'remove duplicates' to remove all duplicates in list.";
        } else {
            return "No duplicates in list! :)";
        }
    }

}
