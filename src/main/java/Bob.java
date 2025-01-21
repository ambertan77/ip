import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line = "  ______________________________________________";
        String name = "Bob";
        String greeting = "  Hello! I'm " + name + " :) " + "\n  Let's add to your to-do list!\n" + line;
        String exit = line + "\n" + "  Goodbye, hope to see you again soon! \n" + line;
        String[] tasks = new String[100];
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
                    System.out.println("  " + index + ". " + tasks[j]);
                }
                System.out.println(line);
            } else {
                if (i >= 100) {
                    System.out.println("List is full.");
                }
                tasks[i] = input;
                i++;
                System.out.println(line + "\n  added: " + input + "\n" + line);
            }
            input = scanner.nextLine();
        }
        scanner.close();
        System.out.println(exit);
    }
}
