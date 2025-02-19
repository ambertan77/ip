package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bob.commands.CreateCommand;
import bob.task.Deadline;
import bob.task.Task;

public class CreateCommandTest {

    @Test
    public void createDeadlineTask_success() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        assertEquals(expected.toString(),
                command.createDeadlineTask("deadline assignment 1 /by 25-01-2025 23:59").toString());
    }

    @Test
    public void createDeadlineTask_noDetails_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        try {
            assertEquals(expected, command.createDeadlineTask("deadline"));
            fail(); // the test should not reach this line
        } catch (BobException e) {
            assertEquals("I can't create tasks with no descriptions :(", e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDescription_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        try {
            assertEquals(expected, command.createDeadlineTask("deadline  /by 25-01-2025 23:59"));
            fail(); // the test should not reach this line
        } catch (BobException e) {
            assertEquals("I can't create tasks with no descriptions :(", e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDeadline_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        try {
            assertEquals(expected, command.createDeadlineTask("deadline assignment 1"));
            fail(); // the test should not reach this line
        } catch (BobException e) {
            String expectedErrorMessage = "Please add a deadline in the format: [description] /by [dd-mm-yyyy hh:mm]!";
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDeadlineAfterSlash_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        try {
            assertEquals(expected, command.createDeadlineTask("deadline assignment 1 / by"));
            fail(); // the test should not reach this line
        } catch (BobException e) {
            String expectedErrorMessage = "Please add a deadline in the format: [description] /by [dd-mm-yyyy hh:mm]!";
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_invalidDayInput_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        Exception exception = assertThrows(BobException.class, () -> {
            command.execute("deadline assignment 1 /by 40-02-2025 23:00");
        });

        String expectedMessage = "Please ensure that the date and time are valid and "
                + "are added in the format 'dd-mm-yyyy hh:mm!'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createDeadlineTask_invalidMonthInput_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        Exception exception = assertThrows(BobException.class, () -> {
            command.execute("deadline assignment 1 /by 10-16-2025 23:00");
        });

        String expectedMessage = "Please ensure that the date and time are valid and "
                + "are added in the format 'dd-mm-yyyy hh:mm!'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createDeadlineTask_invalidHourInput_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        Exception exception = assertThrows(BobException.class, () -> {
            command.execute("deadline assignment 1 /by 10-10-2025 25:00");
        });

        String expectedMessage = "Please ensure that the date and time are valid and "
                + "are added in the format 'dd-mm-yyyy hh:mm!'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createDeadlineTask_invalidMinuteInput_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        CreateCommand command = new CreateCommand(tasks, new Storage(tasks), "./data/tasks.txt");
        Exception exception = assertThrows(BobException.class, () -> {
            command.execute("deadline assignment 1 /by 10-10-2025 20:60");
        });

        String expectedMessage = "Please ensure that the date and time are valid and "
                + "are added in the format 'dd-mm-yyyy hh:mm!'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
