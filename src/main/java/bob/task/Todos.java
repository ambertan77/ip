package bob.task;// solution below adapted from partial solution provided in course website
// https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html under A-Inheritance

import bob.task.Task;

public class Todos extends Task {

    public Todos(String description) {
        super(description, Type.TODO);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String getType() {
        return "T";
    }

    public String getDescription() {
        return this.description;
    }
}
