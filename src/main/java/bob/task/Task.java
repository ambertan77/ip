package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Classes

/**
 * Represents a Task that must be of type Deadline, Event or Todo. A <code>Task</code> object
 * corresponds to a Task characterised by its type, description and whether it has been done.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Specifies the types of tasks supported.
     */
    public enum Type {
        TODO, DEADLINE, EVENT
    }

    protected Type type;

    /**
     * Creates a new instance of a Task.
     *
     * @param description Description of the task.
     * @param type Type of the task.
     */
    public Task(String description, Type type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns the string representation of the task's status.
     *
     * @return String representation of the task's status.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task.
     * Includes the status and description of the task.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    /**
     * Returns the string representation of the type of task.
     * If the type is neither of the supported types, an exception is thrown.
     *
     * @return Letter representation of the type of task.
     * @throws Exception If type is not equal to Type.DEADLINE, Type.EVENT or Type.TODO.
     */
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

    /**
     * Returns the integer representation of the status of task,
     * where 1 means the task is done, and 0 meaning the task is undone.
     *
     * @return Integer representation of the status of task.
     */
    public int getStatus() {
        if (isDone) {
            return 1;
        } else {
            return 0;
        }
    }
}

