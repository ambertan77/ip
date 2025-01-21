import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line = "______________________________________________";
        String name = "Bob";
        String greeting = "Hello! I'm " + name + " :) " + "\nWhat can I do for you?\n" + line;
        String exit = line + "\n" + "Goodbye, hope to see you again soon! \n" + line;

        System.out.println(line);
        System.out.println(greeting);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(line + "\n" + input + "\n" + line);
            input = scanner.nextLine();
        }
        scanner.close();
        System.out.println(exit);
    }
}
