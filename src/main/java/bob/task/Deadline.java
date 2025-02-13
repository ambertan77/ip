package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Task with a deadline. A <code>Deadline</code> object corresponds to
 * a Task represented by its description and its deadline.
 */
public class Deadline extends Task {

    protected LocalDateTime deadline;

    /**
     * Creates a new instance of a Deadline task.
     *
     * @param description Description of the task.
     * @param deadline Deadline of the task.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description, Task.Type.DEADLINE);
        this.deadline = deadline;
    }

    /**
     * Returns the string representation of the task.
     * Includes the description and deadline of the task.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadline() + ")";
    }

    /**
     * Returns the string representation of the type of task.
     *
     * @return Letter representation of the type of task.
     */
    @Override
    public String getType() {
        return "D";
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of task.
     */
    public String getDescription() {
        return this.description;
    }

    // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
    /**
     * Returns a string showing the deadline of the task.
     * The deadline is shown in the format: date/month/year, hour:minute.
     *
     * @return Deadline of task.
     */
    public String getDeadline() {
        assert this.deadline != null : "the deadline of this task must be indicated";
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.deadline.format(outputStringFormat);
    }
}

