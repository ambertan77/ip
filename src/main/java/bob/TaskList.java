package bob;

import bob.task.Task;

import java.util.ArrayList;

/**
 * Represents the list of Tasks that have been added by the user before.
 */
public class TaskList {
    // store the current count of tasks in the list
    protected int count = 0;

    // store the list of tasks
    protected ArrayList<Task> tasks;

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
}
