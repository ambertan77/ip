package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

/**
 * Represents a Task with no start or end time. A <code>Todos</code> object corresponds to
 * a Task represented by only its description.
 */
public class Todos extends Task {

    /**
     * Creates a new instance of a Todo task.
     *
     * @param description Description of the task.
     */
    public Todos(String description) {
        super(description, Type.TODO);
    }

    /**
     * Returns the string representation of the task.
     * Includes the description of the task.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the string representation of the type of task.
     *
     * @return Letter representation of the type of task.
     */
    @Override
    public String getType() {
        return "T";
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of task.
     */
    public String getDescription() {
        return this.description;
    }
}
