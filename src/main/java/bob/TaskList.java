package bob;

import java.util.ArrayList;
import java.util.HashMap;

import bob.task.Task;

/**
 * Represents the list of Tasks that have been added by the user before.
 */
public class TaskList {
    // store the current count of tasks in the list
    protected int count = 0;

    // store the list of tasks
    protected ArrayList<Task> tasks;
    protected HashMap<Task, ArrayList<Task>> duplicates = new HashMap<>();

    /**
     * Creates a new instance of a TaskList.
     *
     * @param tasks List of tasks the user has input.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns a Task at the specified index of the array list.
     *
     * @param index Index of the task that should be returned.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        assert index < tasks.size() : "index should be an integer less than the size of the task list";
        return tasks.get(index);
    }

    /**
     * Removes the specified task in the array list.
     *
     * @param index The task that should be removed.
     */
    public void remove(Task index) {
        tasks.remove(index);
    }

    /**
     * Adds the specified task to the back of the array list.
     *
     * @param newTask The task that should be added.
     */
    public void add(Task newTask) {
        tasks.add(newTask);
    }

    /**
     * Finds all the tasks that match the keyword used.
     *
     * @param key The keyword used to search.
     */
    public ArrayList<Task> find(String key) {
        ArrayList<Task> result = new ArrayList<>();
        tasks.stream()
                .filter((task) -> task.toString().contains(key))
                .forEach((task) -> result.add(task));
        return result;
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks in the task list.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Increases the number of tasks in the task list by 1.
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     * Decreases the number of tasks in the task list by 1.
     */
    public void decrementCount() {
        this.count--;
    }

    public void trackDuplicate(Task key, Task duplicateTask) {
        if (duplicates.containsKey(key)) {
            ArrayList<Task> list = duplicates.get(key);
            list.add(duplicateTask);
            duplicates.put(key, list);
        } else {
            ArrayList<Task> list = new ArrayList<>();
            list.add(duplicateTask);
            duplicates.put(key, list);
        }
    }

    public boolean detectDuplicates() {
        boolean hasDuplicates = false;
        for (int i = 0; i < tasks.size(); i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).toString().equals(tasks.get(j).toString())) {
                    hasDuplicates = true;
                    trackDuplicate(tasks.get(i), tasks.get(j));
                }
            }
        }
        return hasDuplicates;
    }


    public void resetDuplicates() {
        duplicates = new HashMap<Task, ArrayList<Task>>();
    }
}
