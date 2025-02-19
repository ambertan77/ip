package bob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.Todos;

/**
 * Represents the Storage component that stores all the tasks created
 * in all instances of the bot.
 */
public class Storage {

    protected boolean isNewFile = false;
    private TaskList tasks;

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
     * @throws BobException If text in the file is not formatted correctly.
     */
    public void loadFile(String filePath) throws BobException {
        // create file to store the list of tasks
        // code adapted from:
        // https://stackoverflow.com/questions/64401340/java-create-directory-and-subdirectory-if-not-exist
        File data = new File(filePath);
        File directory = data.getParentFile();

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch (Exception e) {
                throw new BobException("Unable to create new file: data.txt");
            }
            isNewFile = true;
        } else {
            try {
                addFileContents();
            } catch (Exception e) {
                throw new BobException("Unable to add file contents into data.txt.");
            }
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
     * @throws BobException If the file cannot be read.
     */
    public void writeToFile(String filePath, Task task) throws BobException {
        assert (new File(filePath)).exists() : "file in hard disk should be loaded";
        try {
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
                text = "T / " + todo.getStatus() + " / " + todo.getDescription();
            }
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            throw new BobException("Unable to write to file: " + e.getMessage());
        }
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
     * @throws BobException If the file cannot be read.
     */
    public void appendToFile(String filePath, Task task) throws BobException {
        assert (new File(filePath)).exists() : "file in hard disk should be loaded";
        try {
            FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
            String text = "";
            if (task instanceof Deadline) {
                Deadline deadlineTask = (Deadline) task;
                text = System.lineSeparator() + "D / " + deadlineTask.getStatus() + " / "
                        + deadlineTask.getDescription() + " / " + deadlineTask.getDeadline();
            } else if (task instanceof Event) {
                Event event = (Event) task;
                text = System.lineSeparator() + "E / " + event.getStatus() + " / " + event.getDescription()
                        + " / " + event.getFrom() + " / " + event.getTo();
            } else if (task instanceof Todos) {
                Todos todo = (Todos) task;
                text = System.lineSeparator() + "T / " + todo.getStatus() + " / " + todo.getDescription();
            }
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            throw new BobException("Unable to append to file: " + e.getMessage());
        }
    }

    /**
     * Returns a newly created Deadline task with details as specified in the stored data.
     *
     * @param splitInput An array of strings created from the user's input.
     * @return The newly created task of type deadline.
     * @throws BobException If the file cannot be read or if the data is formatted wrongly.
     */
    public Deadline createDeadlineTaskFromFile(String[] splitInput) throws BobException {
        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
            LocalDateTime deadline = LocalDateTime.parse(splitInput[3], formatter);
            return new Deadline(splitInput[2], deadline);
        } catch (ArrayIndexOutOfBoundsException e1) {
            throw new BobException("Ensure that the tasks in file are in the correct format.");
        } catch (DateTimeParseException e2) {
            throw new BobException("Ensure that the deadline is given in the correct format.");
        }
    }

    /**
     * Returns a newly created Event task with details as specified in the stored data.
     *
     * @param splitInput An array of strings created from the user's input.
     * @return The newly created task of type event.
     * @throws BobException If the file cannot be read or if the data is formatted wrongly.
     */
    public Event createEventTaskFromFile(String[] splitInput) throws BobException {
        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
            LocalDateTime from = LocalDateTime.parse(splitInput[3], formatter);
            LocalDateTime to = LocalDateTime.parse(splitInput[4], formatter);
            return new Event(splitInput[2], from, to);
        } catch (ArrayIndexOutOfBoundsException e1) {
            throw new BobException("Ensure that the tasks in file are in the correct format.");
        } catch (DateTimeParseException e2) {
            throw new BobException("Ensure that the from and to fields are given in the correct format.");
        }
    }

    /**
     * Returns a newly created Todo task with details as specified in the stored data.
     *
     * @param splitInput An array of strings created from the user's input.
     * @return The newly created task of type todo.
     * @throws BobException If the file cannot be read or if the data is formatted wrongly.
     */
    public Todos createTodoTaskFromFile(String[] splitInput) throws BobException {
        try {
            return new Todos(splitInput[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BobException("Ensure that the tasks in file are in the correct format.");
        }
    }

    /**
     * Returns a newly created task with details as specified in the stored data.
     *
     * @param storedInput A line of text stored in the file of data.
     * @return The newly created task.
     * @throws BobException If the file cannot be read or if the data is formatted wrongly.
     */
    public Task createTaskFromFile(String storedInput) throws BobException {
        assert storedInput != null : "storedInput in file should not be null";
        Task output = null;
        String[] split = storedInput.split(" / ");

        if (storedInput.startsWith("D")) {
            output = createDeadlineTaskFromFile(split);
        } else if (storedInput.startsWith("E")) {
            output = createEventTaskFromFile(split);
        } else if (storedInput.startsWith("T")) {
            output = createTodoTaskFromFile(split);
        } else {
            throw new BobException("Ensure that the tasks in file are "
                    + "either a Deadline task, Event task or Todo task.");
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
     * @throws BobException If the file cannot be read or if the data is formatted wrongly.
     */
    public void addFileContents() throws BobException {
        assert (new File("./data/tasks.txt")).exists() : "file in hard disk should be loaded";
        try {
            File f = new File("./data/tasks.txt");
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String storedInput = s.nextLine();
                Task storedTask = createTaskFromFile(storedInput);
                tasks.add(storedTask);
                tasks.count++;
            }
        } catch (Exception e) {
            throw new BobException("Unable to add file contents: " + e.getMessage());
        }
    }

    /**
     * Returns a boolean indicating whether the file in hard disk is newly created.
     *
     * @return A boolean indicating if the file in hard disk is new.
     */
    public boolean getIsNewFile() {
        return this.isNewFile;
    }

    /**
     * Sets the isNewFile attribute to be the parameter passed.
     *
     * @param indicator A boolean indicating if the file in hard disk is new.
     */
    public void setIsNewFile(boolean indicator) {
        this.isNewFile = indicator;
    }
}
