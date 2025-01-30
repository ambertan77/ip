package bob;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LIST, MARK, UNMARK, DELETE, CREATE
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
     * @throws Exception If user input has no description.
     */
    public Todos createTodoTask(String input) throws Exception {
        String desc = input.substring(4);
        if (desc.equals("")) {
            // empty description
            throw new Exception("I can't create tasks with no descriptions :(");
        }
        return new Todos(desc.substring(1));
    }

    /**
     * Returns the new Deadline task created.
     * If no description, no deadline or the wrong format is entered, an exception is thrown.
     *
     * @param input The line input by the user.
     * @return New deadline task with properties specified by the input.
     * @throws Exception If user input is in the wrong format.
     */
    public Deadline createDeadlineTask(String input) throws Exception {
        if (input.substring(8).equals("")) {
            // empty description
            throw new Exception("I can't create tasks with no descriptions :(");
        }
        // split string input into 2 parts
        String[] split = input.split(" /");
        String desc = split[0].substring(9);
        if (desc.equals("")) {
            // empty description
            throw new Exception("I can't create tasks with no descriptions :(");
        }
        try {
            String deadline = split[1].substring(3);
        } catch (ArrayIndexOutOfBoundsException e) {
            // user did not add deadline
            throw new ArrayIndexOutOfBoundsException("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!");
        }
        String deadline = split[1].substring(3);
        if (deadline.equals("")) {
            // user did not add a deadline
            throw new Exception("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!");
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
     * @throws Exception If user input is in the wrong format.
     */
    public Event createEventTask(String input) throws Exception {
        if (input.substring(5).equals("")) {
            // empty description
            throw new Exception("I can't create tasks with no descriptions :(");
        }
        // split string input into 3 parts
        String[] split = input.split(" /");
        try {
            String desc = split[0].substring(6);
        } catch (StringIndexOutOfBoundsException e1) {
            // empty description
            throw new StringIndexOutOfBoundsException("I can't create tasks with no descriptions :(");
        }
        String desc = split[0].substring(6);
        if (desc.equals("")) {
            // empty description
            throw new Exception("I can't create tasks with no descriptions :(");
        }
        try {
            String from = split[1].substring(5);
            String to = split[2].substring(3);
        } catch (StringIndexOutOfBoundsException e1) {
            // empty "from" or "to fields
            throw new StringIndexOutOfBoundsException("Please add both the starting and ending date/time!");
        } catch (ArrayIndexOutOfBoundsException e2){
            // empty "from" or "to fields
            throw new ArrayIndexOutOfBoundsException("Please add both the starting and ending date/time!");
        }
        String from = split[1].substring(5);
        String to = split[2].substring(3);
        if (from.equals("") || to.equals("")) {
            // empty "from" or "to fields
            throw new Exception("Please add both the starting and ending date/time!");
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
     * @throws Exception If user input is in the wrong format.
     */
    public Task createTask(String input) throws Exception {
        if (input.startsWith("todo")) {
            return createTodoTask(input);
        } else if (input.startsWith("deadline")) {
            return createDeadlineTask(input);
        } else if (input.startsWith("event")) {
            return createEventTask(input);
        }
        // user inputs an unsupported command
        throw new Exception("Please choose between creating a todo, deadline or event!");
    }

    /**
     * Checks the action that the user wants the bot to take and executes it.
     * If the user's input is formatted wrongly, an exception is thrown.
     *
     * @param command The type of action that the user wants to take.
     * @param input The line input by the user.
     * @throws Exception If user input is in the wrong format.
     */
    public void execute(Command command, String input) throws Exception {
        // strings to be printed in the different scenarios
        String indent = "  ";
        String line = "  ______________________________________________";
        String mark = "  Nice! I've marked this task as done:\n";
        String unmark = "  OK, I've marked this task as not done yet:\n";
        String add = "  Got it. I've added this task:";
        String delete = "  Alright, I've removed this task from your list:";

        switch (command) {
        case LIST:
            System.out.println(line);
            if (tasks.count == 0) {
                // do not allow users to command "list" before adding to the list
                throw new Exception("Please add tasks into the list first!");
            }
            // print tasks in the list
            System.out.println("  Here are the tasks currently in your list:");
            for (int j = 0; j < tasks.count; j++) {
                int index = j + 1;
                System.out.println("  " + index + ". " + tasks.get(j).toString());
            }
            System.out.println(line);
            return;
        case MARK:
            // mark task as done
            System.out.println(line);
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
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(mark + "   " + taskToMark.toString() + "\n" + line);
            return;
        case UNMARK:
            // mark task as not done
            System.out.println(line);
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
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(unmark + "   " + taskToUnmark.toString() + "\n" + line);
            return;
        case DELETE:
            // delete the task specified by the user
            System.out.println(line + "\n" + delete);
            int indexToDelete = Integer.valueOf(input.substring(7));
            indexToDelete--;
            Task taskToDelete = tasks.get(indexToDelete);
            System.out.println(indent + " " + taskToDelete.toString());
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
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(indent + "Now you have " + tasks.count + " tasks in the list.\n" + line);
            return;
        case CREATE:
            // call helper method to create the task
            Task task = createTask(input);
            tasks.add(task);
            try {
                String filePath = "./data/tasks.txt";
                storage.appendToFile(filePath, task);
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(line + "\n" + add);
            System.out.println(indent + " " + tasks.get(tasks.count).toString());
            tasks.count++; // increment total count of tasks
            System.out.println(indent + "Now you have " + tasks.count + " tasks in the list.\n" + line);
        }
    }
}
