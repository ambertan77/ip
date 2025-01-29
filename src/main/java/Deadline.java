// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description, Type.DEADLINE);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadline() + ")";
    }

    public String getType() {
        return "D";
    }

    public String getDescription() {
        return this.description;
    }

    // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
    public String getDeadline() {
        DateTimeFormatter outputStringFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        return this.deadline.format(outputStringFormat);
    }
}

