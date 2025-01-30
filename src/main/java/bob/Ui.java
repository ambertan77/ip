package bob;

import java.util.Scanner;

public class Ui {

    public void interact(Parser parser) throws Exception {
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
            } else if (input.startsWith("find ")) {
                parser.execute(Parser.Command.FIND, input);
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
