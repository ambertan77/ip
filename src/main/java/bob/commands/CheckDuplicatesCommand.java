package bob.commands;

import bob.TaskList;

public class CheckDuplicatesCommand {
    private TaskList tasks;

    public CheckDuplicatesCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    public String execute() {
        if (tasks.detectDuplicates()) {
            return "Duplicates exist. Type 'remove duplicates' to remove all duplicates in list.";
        } else {
            return "No duplicates in list! :)";
        }
    }

}
