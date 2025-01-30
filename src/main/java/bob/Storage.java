package bob;

import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.Todos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Represents the Storage component that stores all the tasks created
 * in all instances of the bot.
 */
public class Storage {

    private TaskList tasks;
    protected boolean isNewFile = false;

    /**
     * Creates a new instance of Storage.
     *
     * @param tasks List of tasks the user has input.
     */
    public Storage(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates a directory and a new file if they do not exist yet.
     * Adds the file contents to the task list in this instance of the bot.
     * If text in the file at the specified file path is not formatted accurately, an exception is thrown.
     *
     * @param filePath The file path of the file storing data.
     * @throws Exception If text in the file is not formatted correctly.
     */
    public void loadFile(String filePath) throws Exception {
        // create file to store the list of tasks
        // code adapted from:
        // https://stackoverflow.com/questions/64401340/java-create-directory-and-subdirectory-if-not-exist
        File data = new File("./data/tasks.txt");
        File directory = data.getParentFile();
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!data.exists()) {
            data.createNewFile();
            isNewFile = true;
        } else {
            addFileContents();
        }
    }

    // create a method to write over text
    // method adapted from course website, under W3.4
    // downcasting code adapted from https://www.geeksforgeeks.org/rules-of-downcasting-objects-in-java/
    /**
     * Stores the string representation of the task passed into the method by
     * writing over the current items in the file.
     *
     * @param filePath The file path of the file storing data.
     * @param task The task to be stored into the file.
     * @throws IOException If the file cannot be read.
     */
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
    /**
     * Stores the string representation of the task passed into the method by
     * appending to the current items in the file.
     *
     * @param filePath The file path of the file storing data.
     * @param task The task to be stored into the file.
     * @throws IOException If the file cannot be read.
     */
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

    /**
     * Returns a newly created task with details as specified in the stored data.
     *
     * @param storedInput A line of text stored in the file of data.
     * @return The newly created task.
     * @throws Exception If the file cannot be read or if the data is formatted wrongly.
     */
    public Task createTaskFromFile(String storedInput) throws Exception {
        Task output = null;
        String[] split = storedInput.split(" / ");
        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        if (storedInput.startsWith("D")) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
                LocalDateTime deadline = LocalDateTime.parse(split[3], formatter);
                output = new Deadline(split[2], deadline);
            } catch (ArrayIndexOutOfBoundsException e1) {
                throw new ArrayIndexOutOfBoundsException("Ensure that the tasks in file are in the correct format.");
            } catch (DateTimeParseException e2) {
                throw new Exception("Ensure that the deadline is given in the correct format.");
            }
        } else if (storedInput.startsWith("E")) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
                LocalDateTime from = LocalDateTime.parse(split[3], formatter);
                LocalDateTime to = LocalDateTime.parse(split[4], formatter);
                output = new Event(split[2], from, to);
            } catch (ArrayIndexOutOfBoundsException e1) {
                throw new ArrayIndexOutOfBoundsException("Ensure that the tasks in file are in the correct format.");
            } catch (DateTimeParseException e2) {
                throw new Exception("Ensure that the from and to fields are given in the correct format.");
            }
        } else if (storedInput.startsWith("T")) {
            try {
                output = new Todos(split[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Ensure that the tasks in file are in the correct format.");
            }
        } else {
            throw new Exception("Ensure that the tasks in file are either a Deadline task, Event task or Todo task.");
        }
        if (storedInput.charAt(4) == '1') {
            output.markAsDone();
        }
        return output;
    }

    // code adapted from course website, W3.4c
    /**
     * Adds the tasks stored in the data file into the current task list.
     *
     * @throws Exception If the file cannot be read or if the data is formatted wrongly.
     */
    public void addFileContents() throws Exception {
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
