import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Storage {

    private TaskList tasks;
    protected boolean isNewFile = false;

    public Storage(TaskList tasks) {
        this.tasks = tasks;
    }

    // create a method to write over text
    // method adapted from course website, under W3.4
    // downcasting code adapted from https://www.geeksforgeeks.org/rules-of-downcasting-objects-in-java/
    public void writeToFile(String filePath, Task task) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        String text = "";
        if (task instanceof Deadline) {
            Deadline deadlineTask = (Deadline) task;
            text = "D / " + deadlineTask.getStatus() + " / " + deadlineTask.getDescription()
                    + " / " + deadlineTask.getDeadline();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            text = "E / " + event.getStatus() + " / " + event.getDescription()
                    + " / " + event.getFrom() + " / " + event.getTo();
        } else if (task instanceof Todos) {
            Todos todo = (Todos) task;
            text = " T / " + todo.getStatus() + " / " + todo.getDescription();
        }
        fw.write(text);
        fw.close();
    }

    // create a method to append text to file instead of write over
    // method adapted from course website, under W3.4
    // downcasting code adapted from https://www.geeksforgeeks.org/rules-of-downcasting-objects-in-java/
    public void appendToFile(String filePath, Task task) throws IOException {
        FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
        String text = "";
        if (task instanceof Deadline) {
            Deadline deadlineTask = (Deadline) task;
            text = System.lineSeparator() + "D / " + deadlineTask.getStatus() + " / " + deadlineTask.getDescription()
                    + " / " + deadlineTask.getDeadline();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            text = System.lineSeparator() +  "E / " + event.getStatus() + " / " + event.getDescription()
                    + " / " + event.getFrom() + " / " + event.getTo();
        } else if (task instanceof Todos) {
            Todos todo = (Todos) task;
            text = System.lineSeparator() + "T / " + todo.getStatus() + " / " + todo.getDescription();
        }
        fw.write(text);
        fw.close();
    }

    public Task createTaskFromFile(String storedInput) {
        Task output = null;
        String[] split = storedInput.split(" / ");
        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        if (storedInput.startsWith("D")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime deadline = LocalDateTime.parse(split[3], formatter);
            output = new Deadline(split[2], deadline);
        } else if (storedInput.startsWith("E")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime from = LocalDateTime.parse(split[3], formatter);
            LocalDateTime to = LocalDateTime.parse(split[4], formatter);
            output = new Event(split[2], from, to);
        } else {
            output = new Todos(split[2]);
        }
        if (storedInput.charAt(4) == '1') {
            output.markAsDone();
        }
        return output;
    }

    // code adapted from course website, W3.4c
    public void addFileContents() throws FileNotFoundException {
        File f = new File("./data/tasks.txt");
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String storedInput = s.nextLine();
            Task storedTask = createTaskFromFile(storedInput);
            tasks.add(storedTask);
            tasks.count++;
        }
    }
}
