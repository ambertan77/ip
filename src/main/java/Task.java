// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Classes

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
}

