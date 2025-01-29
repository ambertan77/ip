import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Bob {

    // store the current count of tasks in the list
    private static int count = 0;
    // store the list of tasks
    private static ArrayList<Task> tasks = new ArrayList<Task>(100);
    private static boolean isNewFile = false;

    // all supported commands
    public enum Command {
        LIST, MARK, UNMARK, DELETE, CREATE
    }

    // method to support the creation of new tasks
    public static Task createTask(String input) throws Exception {
        if (input.startsWith("todo")) {
            String desc = input.substring(4);
            if (desc.equals("")) {
                // empty description
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            return new Todos(desc.substring(1));
        } else if (input.startsWith("deadline")) {
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
                throw new ArrayIndexOutOfBoundsException("Please add the deadline!");
            }
            String deadline = split[1].substring(3);
            if (deadline.equals("")) {
                // user did not add a deadline
                throw new Exception("Please add a deadline!");
            }
            return new Deadline(desc, deadline);
        } else if (input.startsWith("event")) {
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
            return new Event(desc, from, to);
        }
        // user inputs an unsupported command
        throw new Exception("Please choose between creating a todo, deadline or event!");
    }

    public static void execute(Command command, String input) throws Exception {
        // strings to be printed in the different scenarios
        String indent = "  ";
        String line = "  ______________________________________________";
        String mark = "  Nice! I've marked this task as done:\n";
        String unmark = "  OK, I've marked this task as not done yet:\n";
        String add = "  Got it. I've added this task:";
        String delete = "  Alright, I've removed this task from your list:";
        String exit = line + "\n" + "  Goodbye, hope to see you again soon!\n" + line;

        switch(command) {
        case LIST:
            System.out.println(line);
            if (count == 0) {
                // do not allow users to command "list" before adding to the list
                throw new Exception("Please add tasks into the list first!");
            }
            // print tasks in the list
            System.out.println("  Here are the tasks currently in your list:");
            for (int j = 0; j < count; j++) {
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
                writeToFile(filePath, firstTask);
                for (int i = 1; i < count; i++) {
                    Task task = tasks.get(i);
                    appendToFile(filePath, task);
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
                writeToFile(filePath, firstTask);
                for (int i = 1; i < count; i++) {
                    Task task = tasks.get(i);
                    appendToFile(filePath, task);
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
            count--; // decrement total count of tasks
            try {
                String filePath = "./data/tasks.txt";
                File data = new File(filePath);
                Task firstTask = tasks.get(0);
                writeToFile(filePath, firstTask);
                for (int i = 1; i < count; i++) {
                    Task task = tasks.get(i);
                    if (isNewFile) {
                        writeToFile(filePath, task);
                        isNewFile = false;
                    } else {
                        appendToFile(filePath, task);
                    }
                }
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(indent + "Now you have " + count + " tasks in the list.\n" + line);
            return;
        case CREATE:
            // call helper method to create the task
            Task task = createTask(input);
            tasks.add(task);
            try {
                String filePath = "./data/tasks.txt";
                appendToFile(filePath, task);
            } catch (IOException e) {
                System.out.println("Unable to write to file: " + e.getMessage());
            }
            System.out.println(line + "\n" + add);
            System.out.println(indent + " " + tasks.get(count).toString());
            count++; // increment total count of tasks
            System.out.println(indent + "Now you have " + count + " tasks in the list.\n" + line);
        }
    }

    // create a method to write over text
    // method adapted from course website, under W3.4
    // downcasting code adapted from https://www.geeksforgeeks.org/rules-of-downcasting-objects-in-java/
    private static void writeToFile(String filePath, Task task) throws IOException {
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
    private static void appendToFile(String filePath, Task task) throws IOException {
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

    private static Task createTaskFromFile(String storedInput) {
        Task output = null;
        String[] split = storedInput.split(" / ");
        if (storedInput.startsWith("D")) {
            output = new Deadline(split[2], split[3]);
        } else if (storedInput.startsWith("E")) {
            output = new Event(split[2], split[3], split[4]);
        } else {
            output = new Todos(split[2]);
        }
        if (storedInput.charAt(4) == '1') {
            output.markAsDone();
        }
        return output;
    }

    // code adapted from course website, W3.4c
    private static void addFileContents() throws FileNotFoundException {
        File f = new File("./data/tasks.txt");
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String storedInput = s.nextLine();
            Task storedTask = createTaskFromFile(storedInput);
            tasks.add(storedTask);
            count++;
        }
    }


    public static void main(String[] args) throws Exception {

        Command command;

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

        // strings to be printed in the different scenarios
        String indent = "  ";
        String line = "  ______________________________________________";
        String name = "Bob";
        String greeting = "  Hello! I'm " + name + " :)" + "\n  Let's add to your list!\n" + line;
        String mark = "  Nice! I've marked this task as done:\n";
        String unmark = "  OK, I've marked this task as not done yet:\n";
        String add = "  Got it. I've added this task:";
        String delete = "  Alright, I've removed this task from your list:";
        String exit = line + "\n" + "  Goodbye, hope to see you again soon!\n" + line;

        // start with greeting the user
        System.out.println(line);
        System.out.println(greeting);

        // open the scanner to scan for user inputs
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                execute(Command.LIST, input);
            } else if (input.startsWith("mark ")) {
                execute(Command.MARK, input);
            } else if (input.startsWith("unmark ")) {
                execute(Command.UNMARK, input);
            } else if (input.startsWith("delete ")) {
                execute(Command.DELETE, input);
            } else {
                execute(Command.CREATE, input);
            }
            input = scanner.nextLine();
        }

        // close the scanner and exit the program
        scanner.close();
        System.out.println(exit);
    }
}
