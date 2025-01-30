package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Task with a start and end time. A <code>Event</code> object corresponds to
 * a Task represented by its description, its start time and its end time.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, Task.Type.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the task.
     * Includes the description, start and end time of the task.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from " + this.getFrom() + " to " + this.getTo() + ")";
    }

    /**
     * Returns the string representation of the type of task.
     *
     * @return Letter representation of the type of task.
     */
    public String getType() {
        return "E";
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
     * Returns a string showing the start time of the event.
     * The deadline is shown in the format: date/month/year, hour:minute.
     *
     * @return Start time of event.
     */
    public String getFrom() {
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.from.format(outputStringFormat);
    }

    // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
    /**
     * Returns a string showing the end time of the event.
     * The deadline is shown in the format: date/month/year, hour:minute.
     *
     * @return End time of event.
     */
    public String getTo() {
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.to.format(outputStringFormat);
    }
}
