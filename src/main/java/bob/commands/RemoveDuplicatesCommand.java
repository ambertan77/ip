package bob.commands;

import bob.Bob;
import bob.BobException;
import bob.Storage;
import bob.TaskList;
import bob.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveDuplicatesCommand {

    private TaskList tasks;
    private Storage storage;
    private Bob bob;
    private String filePath;
    private boolean hasDuplicates;

    public RemoveDuplicatesCommand(TaskList tasks, Storage storage, Bob bob) {
        this.tasks = tasks;
        this.storage = storage;
        this.bob = bob;
        this.filePath = bob.getFilePath();
        this.hasDuplicates = tasks.detectDuplicates();
    }

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
