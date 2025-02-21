package bob;

/**
 * Represents the component dealing with user interactions. If the chatbot is unable to
 * continue responding to the user due to errors in file path or inputs, an exception is thrown.
 */
public class Ui {

    /**
     * Greets the user when the chatbot is first opened.
     *
     * @return A string containing the greeting message.
     */
    public String greet() {
        return "Hello, I'm Bob! What can I do for you today? :)";
    }

    /**
     * Returns the exit message when the user says "bye".
     *
     * @return A string containing the exit message.
     */
    public String exit() {
        return "Goodbye, hope to see you again soon!";
    }

    /**
     * Responds to the user's commands through printing messages.
     *
     * @param parser The parser reading and executing the user's commands.
     * @param input The text input by the user in the GUI.
     * @return A String containing the response of the chatbot.
     * @throws BobException If the chatbot is unable to execute commands.
     */
    public String interact(Parser parser, String input) throws BobException {
        String output;

        boolean isCreateCommand = (input.startsWith("todo ") || input.startsWith("deadline "))
                || input.startsWith("event ");

        if (input.equals("list")) {
            output = parser.execute(Parser.Command.LIST, input);
        } else if (input.startsWith("mark ")) {
            output = parser.execute(Parser.Command.MARK, input);
        } else if (input.startsWith("unmark ")) {
            output = parser.execute(Parser.Command.UNMARK, input);
        } else if (input.startsWith("delete ")) {
            output = parser.execute(Parser.Command.DELETE, input);
        } else if (input.startsWith("find ")) {
            output = parser.execute(Parser.Command.FIND, input);
        } else if (input.equals("bye")) {
            output = this.exit();
        } else if (input.equals("check duplicates")) {
            output = parser.execute(Parser.Command.CHECK_DUPLICATES, input);
        } else if (input.equals("remove duplicates")) {
            output = parser.execute(Parser.Command.REMOVE_DUPLICATES, input);
        } else if (isCreateCommand) {
            output = parser.execute(Parser.Command.CREATE, input);
        } else {
            throw new BobException("This command is currently not supported by Bob :(");
        }

        return output;
    }

    /**
     * Responds to the user's commands through printing messages, with all errors handled.
     *
     * @param parser The parser reading and executing the user's commands.
     * @param input The text input by the user in the GUI.
     * @return A String containing the response of the chatbot, or details of the error that occurred.
     */
    public String interactWithErrorsHandled(Parser parser, String input) {
        try {
            return interact(parser, input);
        } catch (BobException e) {
            return e.getMessage();
        }
    }
}
