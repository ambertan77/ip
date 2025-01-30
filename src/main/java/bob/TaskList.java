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

    public ArrayList<Task> find(String key) {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.toString().contains(key)) {
                result.add(current);
            }
        }
        return result;
    }
}
