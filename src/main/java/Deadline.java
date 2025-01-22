// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

public class Deadline extends Task {

    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}

