package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Classes

public class Task {
    protected String description;
    protected boolean isDone;
    public enum Type {
        TODO, DEADLINE, EVENT
    }
    protected Type type;

    public Task(String description, Type type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    public String getType() throws Exception {
        if (type == Type.DEADLINE) {
            return "D";
        } else if (type == Type.EVENT) {
            return "E";
        } else if (type == Type.TODO) {
            return "T";
        } else {
            throw new Exception("The type of task is not supported by Bob.");
        }
    }

    public int getStatus() {
        if (isDone) {
            return 1;
        } else {
            return 0;
        }
    }
}

