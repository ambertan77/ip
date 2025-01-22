import java.util.ArrayList;
import java.util.Scanner;

public class Bob {

    // store the current count of tasks in the list
    private static int i = 0;
    // store the list of tasks
    private static ArrayList<Task> tasks = new ArrayList<Task>(100);

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
                if (i == 0) {
                    // do not allow users to command "list" before adding to the list
                    throw new Exception("Please add tasks into the list first!");
                }
                // print tasks in the list
                System.out.println("  Here are the tasks currently in your list:");
                for (int j = 0; j < i; j++) {
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
                System.out.println(mark + "   " + taskToMark.toString() + "\n" + line);
                return;
            case UNMARK:
                // mark task as not done
                System.out.println(line);
                int indexToUnmark = Integer.valueOf(input.substring(7));
                indexToUnmark--;
                Task taskToUnmark = tasks.get(indexToUnmark);
                taskToUnmark.markAsUndone();
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
                i--; // decrement total count of tasks
                System.out.println(indent + "Now you have " + i + " tasks in the list.\n" + line);
                return;
            case CREATE:
                // call helper method to create the task
                tasks.add(createTask(input));
                System.out.println(line + "\n" + add);
                System.out.println(indent + " " + tasks.get(i).toString());
                i++; // increment total count of tasks
                System.out.println(indent + "Now you have " + i + " tasks in the list.\n" + line);
        }
    }

    public static void main(String[] args) throws Exception {

        Command command;

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
