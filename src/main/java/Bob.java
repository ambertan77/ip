import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Bob {
    // store the list of tasks
    private static TaskList tasks = new TaskList(new ArrayList<Task>(100));
    private static Storage storage = new Storage(tasks);
    private static Parser parser = new Parser(tasks, storage);

    // all supported commands
    public enum Command {
        LIST, MARK, UNMARK, DELETE, CREATE
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
            storage.isNewFile = true;
        } else {
            storage.addFileContents();
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
                parser.execute(Parser.Command.LIST, input);
            } else if (input.startsWith("mark ")) {
                parser.execute(Parser.Command.MARK, input);
            } else if (input.startsWith("unmark ")) {
                parser.execute(Parser.Command.UNMARK, input);
            } else if (input.startsWith("delete ")) {
                parser.execute(Parser.Command.DELETE, input);
            } else {
                parser.execute(Parser.Command.CREATE, input);
            }
            input = scanner.nextLine();
        }

        // close the scanner and exit the program
        scanner.close();
        System.out.println(exit);
    }
}
