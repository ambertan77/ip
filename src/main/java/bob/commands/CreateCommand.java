package bob.commands;

// This class is adapted from the package structure in:
// https://github.com/juneha1120/ip/tree/master/src/main/java/chillguy/commands.
// The package structure and the OOP logic (related to commands in Parser.java) are inspired by the above repository,
// but the methods and logic within this class were created independently.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bob.BobException;
import bob.Storage;
import bob.TaskList;
import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.Todos;

/**
 * Represents a CreateCommand that has been called by the user.
 */
public class CreateCommand {

    /**
     * An immutable string containing the header to be printed when the create command is used.
     */
    public static final String CREATE_HEADER = "Got it. I've added this task:\n";

    private TaskList tasks;
    private Storage storage;
    private String filePath;

    /**
     * Creates a new instance of a "create" command.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     * @param filePath File path of the file containing information about the task list.
     */
    public CreateCommand(TaskList tasks, Storage storage, String filePath) {
        this.tasks = tasks;
        this.storage = storage;
        this.filePath = filePath;
    }

    /**
     * Returns the new Todo task created.
     * If no description is entered, an exception is thrown.
     *
     * @param input The line input by the user.
     * @return New todo task with properties specified by the input.
     * @throws BobException If user input has no description.
     */
    public Todos createTodoTask(String input) throws BobException {
        String desc = input.substring(4);

        if (desc.equals("")) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        return new Todos(desc.substring(1));
    }

    /**
     * Returns the new Deadline task created.
     * If no description, no deadline or the wrong format is entered, an exception is thrown.
     *
     * @param input The line input by the user.
     * @return New deadline task with properties specified by the input.
     * @throws BobException If user input is in the wrong format.
     */
    public Deadline createDeadlineTask(String input) throws BobException {
        if (input.substring(8).equals("")) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        String[] split = input.split(" /");
        String desc = split[0].substring(9);

        if (desc.equals("")) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        try {
            String deadline = split[1].substring(3);
        } catch (ArrayIndexOutOfBoundsException e) {
            // throw exception since user did not add deadline
            throw new BobException("Please add a deadline in the format: [description] /by [dd-mm-yyyy hh:mm]!");
        }

        String deadline = split[1].substring(3);
        if (deadline.equals("")) {
            // throw exception since user did not add a deadline
            throw new BobException("Please add a deadline in the format: [description] /by [dd-mm-yyyy hh:mm]!");
        }

        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        DateTimeFormatter inputDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return new Deadline(desc, LocalDateTime.parse(deadline, inputDateTimeFormat));
    }

    /**
     * Returns the new Event task created. If no description,
     * no start or end time, or the wrong format is entered, an exception is thrown.
     *
     * @param input The line input by the user.
     * @return New event task with properties specified by the input.
     * @throws BobException If user input is in the wrong format.
     */
    public Event createEventTask(String input) throws BobException {
        if (input.substring(5).equals("")) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        String[] split = input.split(" /");

        try {
            String desc = split[0].substring(6);
        } catch (StringIndexOutOfBoundsException e1) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        String desc = split[0].substring(6);
        if (desc.equals("")) {
            // throw exception since description is empty
            throw new BobException("I can't create tasks with no descriptions :(");
        }

        try {
            String from = split[1].substring(5);
            String to = split[2].substring(3);
        } catch (StringIndexOutOfBoundsException e1) {
            // throw exception since there are empty "from" or "to fields
            String errorMessage = "Please add both the starting and ending date/time! "
                    + "Add an event in the format: [description] /from [dd-mm-yyyy hh:mm] /to [dd-mm-yyyy hh:mm]";
            throw new BobException(errorMessage);
        } catch (ArrayIndexOutOfBoundsException e2) {
            // throw exception since there are empty "from" or "to fields
            String errorMessage = "Please add both the starting and ending date/time! "
                    + "Add an event in the format: [description] /from [dd-mm-yyyy hh:mm] /to [dd-mm-yyyy hh:mm]";
            throw new BobException(errorMessage);
        }

        String from = split[1].substring(5);
        String to = split[2].substring(3);
        if (from.equals("") || to.equals("")) {
            // throw exception since there are empty "from" or "to fields
            String errorMessage = "Please add both the starting and ending date/time! "
                    + "Add an event in the format: [description] /from [dd-mm-yyyy hh:mm] /to [dd-mm-yyyy hh:mm]";
            throw new BobException(errorMessage);
        }

        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        DateTimeFormatter inputDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return new Event(desc, LocalDateTime.parse(from, inputDateTimeFormat),
                LocalDateTime.parse(to, inputDateTimeFormat));
    }

    /**
     * Checks the type of task to be created and returns the new Task created.
     * If the input is formatted wrongly, an exception is thrown.
     *
     * @param input The line input by the user.
     * @return New task with properties and type specified by the input.
     * @throws BobException If user input is in the wrong format.
     */
    public Task createTask(String input) throws BobException {
        if (input.startsWith("todo")) {
            return createTodoTask(input);
        } else if (input.startsWith("deadline")) {
            return createDeadlineTask(input);
        } else if (input.startsWith("event")) {
            return createEventTask(input);
        }

        // throw exception since user has input an unsupported command
        throw new BobException("Please choose between creating a todo, deadline or event!");
    }

    /**
     * Executes the "create" command.
     *
     * @return A string containing the information of the newly created task.
     */
    public String execute(String input) throws BobException {
        Task task = createTask(input); // call helper method to create the task
        tasks.add(task);

        try {
            this.storage.appendToFile(filePath, task);
        } catch (BobException e) {
            return "Unable to write to file: " + e.getMessage();
        }

        String outputForCreate = "";
        outputForCreate = outputForCreate + tasks.get(tasks.getCount()).toString();
        tasks.incrementCount();
        outputForCreate = outputForCreate + "\nNow you have " + tasks.getCount() + " tasks in the list.";
        return CREATE_HEADER + outputForCreate;
    }
}
