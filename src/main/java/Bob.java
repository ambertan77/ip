import java.util.Scanner;

public class Bob {

    public static void main(String[] args) {
        String line = "  ______________________________________________";
        String name = "Bob";
        String greeting = "  Hello! I'm " + name + " :) " + "\n  Let's add to your to-do list!\n" + line;
        String mark = "  Nice! I've marked this task as done:\n";
        String unmark = "  OK, I've marked this task as not done yet:\n";
        String exit = line + "\n" + "  Goodbye, hope to see you again soon! \n" + line;
        Task[] tasks = new Task[100];
        int i = 0;

        System.out.println(line);
        System.out.println(greeting);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(line);
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
            }else {
                if (i >= 100) {
                    System.out.println("List is full.");
                }
                tasks[i] = new Task(input);
                i++;
                System.out.println(line + "\n  added: " + input + "\n" + line);
            }
            input = scanner.nextLine();
        }
        scanner.close();
        System.out.println(exit);
    }
}
