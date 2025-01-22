import java.sql.Array;
import java.util.Scanner;

public class Bob {

    public static Task createTask(String input) throws Exception {
        if (input.startsWith("todo")) {
            String desc = input.substring(4);
            if (desc.equals("")) {
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            return new Todos(desc.substring(1));
        } else if (input.startsWith("deadline")) {
            if (input.substring(8).equals("")) {
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            String[] split = input.split(" /");
            String desc = split[0].substring(9);
            if (desc.equals("")) {
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            try {
                String deadline = split[1].substring(3);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Please add the deadline!");
            }
            String deadline = split[1].substring(3);
            if (deadline.equals("")) {
                throw new Exception("Please add a deadline!");
            }
            return new Deadline(desc, deadline);
        } else if (input.startsWith("event")) {
            if (input.substring(5).equals("")) {
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            String[] split = input.split(" /");
            try {
                String desc = split[0].substring(6);
            } catch (StringIndexOutOfBoundsException e1) {
                throw new StringIndexOutOfBoundsException("I can't create tasks with no descriptions :(");
            }
            String desc = split[0].substring(6);
            if (desc.equals("")) {
                throw new Exception("I can't create tasks with no descriptions :(");
            }
            try {
                String from = split[1].substring(5);
                String to = split[2].substring(3);
            } catch (StringIndexOutOfBoundsException e1) {
                throw new StringIndexOutOfBoundsException("Please add both the starting and ending date/time!");
            } catch (ArrayIndexOutOfBoundsException e2){
                throw new ArrayIndexOutOfBoundsException("Please add both the starting and ending date/time!");
            }
            String from = split[1].substring(5);
            String to = split[2].substring(3);
            if (from.equals("") || to.equals("")) {
                throw new Exception("Please add both the starting and ending date/time!");
            }
            return new Event(desc, from, to);
        }

        throw new Exception("Please choose between creating a todo, deadline or event!");
    }

    public static void main(String[] args) throws Exception {
        String indent = "  ";
        String line = "  ______________________________________________";
        String name = "Bob";
        String greeting = "  Hello! I'm " + name + " :)" + "\n  Let's add to your list!\n" + line;
        String mark = "  Nice! I've marked this task as done:\n";
        String unmark = "  OK, I've marked this task as not done yet:\n";
        String add = "  Got it. I've added this task:";
        String exit = line + "\n" + "  Goodbye, hope to see you again soon!\n" + line;
        Task[] tasks = new Task[100];
        int i = 0;

        System.out.println(line);
        System.out.println(greeting);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(line);
                if (i == 0) {
                    throw new Exception("Please add tasks into the list first!");
                }
                System.out.println("  Here are the tasks currently in your list:");
                for (int j = 0; j < i; j++) {
                    int index = j + 1;
                    System.out.println("  " + index + ". " + tasks[j].toString());
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                System.out.println(line);
                int index = Integer.valueOf(input.substring(5));
                index--;
                Task task = tasks[index];
                task.markAsDone();
                System.out.println(mark + "   " + task.toString() + "\n" + line);
            } else if (input.startsWith("unmark ")) {
                System.out.println(line);
                int index = Integer.valueOf(input.substring(7));
                index--;
                Task task = tasks[index];
                task.markAsUndone();
                System.out.println(unmark + "   " + task.toString() + "\n" + line);
            } else {
                if (i >= 100) {
                    System.out.println("List is full.");
                }
                tasks[i] = createTask(input);
                System.out.println(line + "\n" + add);
                System.out.println(indent + " " + tasks[i].toString());
                i++;
                System.out.println(indent + "Now you have " + i + " tasks in the list.\n" + line);
            }
            input = scanner.nextLine();
        }

        scanner.close();
        System.out.println(exit);
    }
}
