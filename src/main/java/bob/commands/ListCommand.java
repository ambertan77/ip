package bob.commands;

// This class is adapted from the package structure in:
// https://github.com/juneha1120/ip/tree/master/src/main/java/chillguy/commands.
// The package structure and the OOP logic (related to commands in Parser.java) are inspired by the above repository,
// but the methods and logic within this class were created independently.

import bob.TaskList;

/**
 * Represents a ListCommand that has been called by the user.
 */
public class ListCommand {

    private TaskList tasks;

    /**
     * An immutable string containing the header to be printed when the list command is used.
     */
    public static final String LIST_HEADER = "Here are the tasks currently in your list:\n";

    /**
     * An immutable string informing the user that there are no items in the task list.
     */
    public static final String NO_ITEMS_RESPONSE = "No tasks in list currently. Let's add one now!";

    /**
     * Creates a new instance of a "list" command.
     *
     * @param tasks List of tasks the user has input.
     */
    public ListCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes the "list" command by printing all the tasks in the task list.
     *
     * @return A string containing the information of all the tasks in list.
     */
    public String execute() {
        if (tasks.getCount() == 0) {
            return NO_ITEMS_RESPONSE;
        } else {
            String allTasks = "";
            for (int j = 0; j < tasks.getCount() - 1; j++) {
                int index = j + 1;
                allTasks = allTasks + index + ". " + tasks.get(j).toString() + "\n";
            }
            allTasks = allTasks + tasks.getCount() + ". " + tasks.get(tasks.getCount() - 1).toString();
            return LIST_HEADER + allTasks;
        }
    }
}
