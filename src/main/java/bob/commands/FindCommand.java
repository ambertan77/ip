package bob.commands;

// This class is adapted from the package structure in:
// https://github.com/juneha1120/ip/tree/master/src/main/java/chillguy/commands.
// The package structure and the OOP logic (related to commands in Parser.java) are inspired by the above repository,
// but the methods and logic within this class were created independently.

import bob.TaskList;
import bob.task.Task;

import java.util.ArrayList;

/**
 * Represents a FindCommand that has been called by the user.
 */
public class FindCommand {
    private TaskList tasks;

    /**
     * An immutable string containing the header to be printed when the find command is used.
     */
    public static final String FIND_HEADER = "Here are the tasks that match your search key:\n";

    /**
     * An immutable string containing the message to user that there have been no matches found.
     */
    public static final String NO_MATCHES_FOUND = "No matches to your search key.";

    /**
     * Creates a new instance of a "find" command.
     *
     * @param tasks List of tasks the user has input.
     */
    public FindCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes the "find" command.
     *
     * @return A string containing the search key.
     */
    public String execute(String input) {
        String key = input.substring(5);
        ArrayList<Task> matches = tasks.find(key);

        if (matches.isEmpty()) {
            return NO_MATCHES_FOUND;
        } else {
            // print tasks
            String outputForFind = "";
            for (int j = 0; j < matches.size(); j++) {
                int index = j + 1;
                if (j == matches.size() - 1) {
                    outputForFind = outputForFind + index + ". " + matches.get(j).toString();
                } else {
                    outputForFind = outputForFind + index + ". " + matches.get(j).toString() + "\n";
                }
            }
            return FIND_HEADER + outputForFind;
        }
    }
}
