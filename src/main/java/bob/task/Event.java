package bob.task;

// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, Task.Type.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from " + this.getFrom() + " to " + this.getTo() + ")";
    }

    public String getType() {
        return "E";
    }

    public String getDescription() {
        return this.description;
    }

    // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
    public String getFrom() {
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.from.format(outputStringFormat);
    }

    // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
    public String getTo() {
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.to.format(outputStringFormat);
    }
}
