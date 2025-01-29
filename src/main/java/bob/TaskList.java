package bob;

import bob.task.Task;

import java.util.ArrayList;

public class TaskList {
    // store the current count of tasks in the list
    protected int count = 0;

    // store the list of tasks
    protected ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void remove(Task index) {
        tasks.remove(index);
    }

    public void add(Task newTask) {
        tasks.add(newTask);
    }
}
