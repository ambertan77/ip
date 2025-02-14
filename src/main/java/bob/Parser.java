package bob;

import bob.commands.CheckDuplicatesCommand;
import bob.commands.CreateCommand;
import bob.commands.DeleteCommand;
import bob.commands.FindCommand;
import bob.commands.ListCommand;
import bob.commands.MarkCommand;
import bob.commands.RemoveDuplicatesCommand;
import bob.commands.UnmarkCommand;


/**
 * Represents the Parser that reads and executes the user's commands.
 */
public class Parser {
    // all supported commands
    /**
     * Lists the different user commands supported.
     */
    public enum Command {
        LIST, MARK, UNMARK, DELETE, CREATE, FIND, CHECK_DUPLICATES, REMOVE_DUPLICATES
    }

    private Bob bob;
    private TaskList tasks;
    private Storage storage;

    /**
     * Creates a new instance of a Parser.
     *
     * @param tasks List of tasks the user has input.
     * @param storage Where tasks created in all instances of the bot are stored.
     */
    public Parser(TaskList tasks, Storage storage, Bob bob) {
        this.tasks = tasks;
        this.storage = storage;
        this.bob = bob;
    }

    /**
     * Checks the action that the user wants the bot to take and executes it.
     * If the user's input is formatted wrongly, an exception is thrown.
     *
     * @param command The type of action that the user wants to take.
     * @param input The line input by the user.
     * @return The message detailing the command executed.
     * @throws BobException If user input is in the wrong format.
     */
    public String execute(Command command, String input) throws BobException {
        switch (command) {
        case LIST:
            ListCommand listCommand = new ListCommand(this.tasks);
            return listCommand.execute();
        case MARK:
            MarkCommand markCommand = new MarkCommand(this.tasks, this.storage, this.bob.getFilePath());
            return markCommand.execute(input);
        case UNMARK:
            UnmarkCommand unmarkCommand = new UnmarkCommand(this.tasks, this.storage, this.bob.getFilePath());
            return unmarkCommand.execute(input);
        case DELETE:
            DeleteCommand deleteCommand = new DeleteCommand(tasks, storage, this.bob.getFilePath());
            return deleteCommand.execute(input);
        case CREATE:
            CreateCommand createCommand = new CreateCommand(tasks, storage, this.bob.getFilePath());
            return createCommand.execute(input);
        case FIND:
            FindCommand findCommand = new FindCommand(tasks);
            return findCommand.execute(input);
        case CHECK_DUPLICATES:
            CheckDuplicatesCommand checkDuplicatesCommand = new CheckDuplicatesCommand(tasks);
            return checkDuplicatesCommand.execute();
        case REMOVE_DUPLICATES:
            RemoveDuplicatesCommand removeDuplicatesCommand = new RemoveDuplicatesCommand(tasks, storage, this.bob);
            return removeDuplicatesCommand.execute();
        default:
            throw new BobException("The command you have entered is currently not supported by Bob :(");
        }
    }
}
