package bob;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.Todos;

/**
 * Represents the Parser that reads and executes the user's commands.
 */
public class Parser {
    // all supported commands
    /**
     * Lists the different user commands supported.
     */
    public enum Command {
        LIST, MARK, UNMARK, DELETE, CREATE, FIND
    }

    private TaskList tasks;
    private Storage storage;

    /**
     * Creates a new instance of a Task.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     */
    public Parser(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
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
            // empty description
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
            // empty description
            throw new BobException("I can't create tasks with no descriptions :(");
        }
        // split string input into 2 parts
        String[] split = input.split(" /");
        String desc = split[0].substring(9);
        if (desc.equals("")) {
            // empty description
            throw new BobException("I can't create tasks with no descriptions :(");
        }
        try {
            String deadline = split[1].substring(3);
        } catch (ArrayIndexOutOfBoundsException e) {
            // user did not add deadline
            throw new BobException("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!");
        }
        String deadline = split[1].substring(3);
        if (deadline.equals("")) {
            // user did not add a deadline
            throw new BobException("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!");
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
            // empty description
            throw new BobException("I can't create tasks with no descriptions :(");
        }
        // split string input into 3 parts
        String[] split = input.split(" /");
        try {
            String desc = split[0].substring(6);
        } catch (StringIndexOutOfBoundsException e1) {
            // empty description
            throw new BobException("I can't create tasks with no descriptions :(");
        }
        String desc = split[0].substring(6);
        if (desc.equals("")) {
            // empty description
            throw new BobException("I can't create tasks with no descriptions :(");
        }
        try {
            String from = split[1].substring(5);
            String to = split[2].substring(3);
        } catch (StringIndexOutOfBoundsException e1) {
            // empty "from" or "to fields
            throw new BobException("Please add both the starting and ending date/time!");
        } catch (ArrayIndexOutOfBoundsException e2) {
            // empty "from" or "to fields
            throw new BobException("Please add both the starting and ending date/time!");
        }
        String from = split[1].substring(5);
        String to = split[2].substring(3);
        if (from.equals("") || to.equals("")) {
            // empty "from" or "to fields
            throw new BobException("Please add both the starting and ending date/time!");
        }
        // code adapted from https://www.geeksforgeeks.org/java-time-localdatetime-class-in-java/ (Example 3)
        // and https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        DateTimeFormatter inputDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return new Event(desc, LocalDateTime.parse(from, inputDateTimeFormat),
                LocalDateTime.parse(to, inputDateTimeFormat));
    }

    // method to support the creation of new tasks
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
        // user inputs an unsupported command
        throw new BobException("Please choose between creating a todo, deadline or event!");
    }

    /**
     * Checks the action that the user wants the bot to take and executes it.
     * If the user's input is formatted wrongly, an exception is thrown.
     *
     * @param command The type of action that the user wants to take.
     * @param input The line input by the user.
     * @return The message detailing the command executed.
     * @throws BobException If user input is in the wrong format.
     */
    public String execute(Command command, String input) throws BobException {
        // strings to be printed in the different scenarios
        String indent = "  ";
        String line = "  ______________________________________________";
        String mark = "Nice! I've marked this task as done:\n";
        String unmark = "OK, I've marked this task as not done yet:\n";
        String add = "Got it. I've added this task:";
        String delete = "Alright, I've removed this task from your list:";
        String list = "Here are the tasks currently in your list:\n";

        switch (command) {
        case LIST:
            if (tasks.count == 0) {
                // let users know that there is no task in list
                return "No tasks in list currently. Let's add one now!";
            } else {
                // add tasks in the list
                String allTasks = "";
                for (int j = 0; j < tasks.count - 1; j++) {
                    int index = j + 1;
                    allTasks = allTasks + index + ". " + tasks.get(j).toString() + "\n";
                }
                allTasks = allTasks + tasks.count + ". " + tasks.get(tasks.count - 1).toString();
                return list + allTasks;
            }
        case MARK:
            // mark task as done
            int indexToMark = Integer.valueOf(input.substring(5));
            indexToMark--;
            Task taskToMark = tasks.get(indexToMark);
            taskToMark.markAsDone();
            try {
                String filePath = "./data/tasks.txt";
                Task firstTask = tasks.get(0);
                storage.writeToFile(filePath, firstTask);
                for (int i = 1; i < tasks.count; i++) {
                    Task task = tasks.get(i);
                    storage.appendToFile(filePath, task);
                }
            } catch (BobException e) {
                return "Unable to write to file.";
            }
            return mark + taskToMark.toString();
        case UNMARK:
            // mark task as not done
            int indexToUnmark = Integer.valueOf(input.substring(7));
            indexToUnmark--;
            Task taskToUnmark = tasks.get(indexToUnmark);
            taskToUnmark.markAsUndone();
            try {
                String filePath = "./data/tasks.txt";
                Task firstTask = tasks.get(0);
                storage.writeToFile(filePath, firstTask);
                for (int i = 1; i < tasks.count; i++) {
                    Task task = tasks.get(i);
                    storage.appendToFile(filePath, task);
                }
            } catch (BobException e) {
                return "Unable to write to file.";
            }
            return unmark + taskToUnmark.toString();
        case DELETE:
            String outputForDelete = delete + "\n";
            // delete the task specified by the user
            int indexToDelete = Integer.valueOf(input.substring(7));
            indexToDelete--;
            Task taskToDelete = tasks.get(indexToDelete);
            outputForDelete = outputForDelete + taskToDelete.toString() + "\n";
            tasks.remove(taskToDelete); // remove task from the list of tasks
            tasks.count--; // decrement total count of tasks
            try {
                String filePath = "./data/tasks.txt";
                File data = new File(filePath);
                Task firstTask = tasks.get(0);
                storage.writeToFile(filePath, firstTask);
                for (int i = 1; i < tasks.count; i++) {
                    Task task = tasks.get(i);
                    if (storage.isNewFile) {
                        storage.writeToFile(filePath, task);
                        storage.isNewFile = false;
                    } else {
                        storage.appendToFile(filePath, task);
                    }
                }
            } catch (BobException e) {
                return "Unable to write to file.";
            }
            return outputForDelete + "Now you have " + tasks.count + " tasks in the list.";
        case CREATE:
            String outputForCreate = add + "\n";
            // call helper method to create the task
            Task task = createTask(input);
            tasks.add(task);
            try {
                String filePath = "./data/tasks.txt";
                this.storage.appendToFile(filePath, task);
            } catch (BobException e) {
                return "Unable to write to file: " + e.getMessage();
            }
            outputForCreate = outputForCreate + tasks.get(tasks.count).toString();
            tasks.count++; // increment total count of tasks
            outputForCreate = outputForCreate + "\nNow you have " + tasks.count + " tasks in the list.";
            return outputForCreate;
        case FIND:
            String key = input.substring(5);
            ArrayList<Task> matches = tasks.find(key);
            if (matches.isEmpty()) {
                return "No matches to your search key.";
            } else {
                // print tasks
                String outputForFind = "Here are the tasks that match your search key:\n";
                for (int j = 0; j < matches.size(); j++) {
                    int index = j + 1;
                    if (j == matches.size() - 1) {
                        outputForFind = outputForFind + index + ". " + matches.get(j).toString();
                    } else {
                        outputForFind = outputForFind + index + ". " + matches.get(j).toString() + "\n";
                    }
                }
                return outputForFind;
            }
        default:
            throw new BobException("The command you have entered is currently not supported by Bob :(");
        }
    }
}