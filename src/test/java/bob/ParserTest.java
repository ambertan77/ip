package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bob.task.Deadline;
import bob.task.Task;

public class ParserTest {

    @Test
    public void createDeadlineTask_success() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Parser parser = new Parser(tasks, new Storage(tasks));
        assertEquals(expected.toString(),
                parser.createDeadlineTask("deadline assignment 1 /by 25-01-2025 23:59").toString());
    }

    @Test
    public void createDeadlineTask_noDetails_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Parser parser = new Parser(tasks, new Storage(tasks));
        try {
            assertEquals(expected, parser.createDeadlineTask("deadline"));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("I can't create tasks with no descriptions :(", e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDescription_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Parser parser = new Parser(tasks, new Storage(tasks));
        try {
            assertEquals(expected, parser.createDeadlineTask("deadline  /by 25-01-2025 23:59"));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("I can't create tasks with no descriptions :(", e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDeadline_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Parser parser = new Parser(tasks, new Storage(tasks));
        try {
            assertEquals(expected, parser.createDeadlineTask("deadline assignment 1"));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!", e.getMessage());
        }
    }

    @Test
    public void createDeadlineTask_noDeadlineAfterSlash_exceptionThrown() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, Month.JANUARY, 25, 23, 59);
        Deadline expected = new Deadline("assignment 1", deadline);
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Parser parser = new Parser(tasks, new Storage(tasks));
        try {
            assertEquals(expected, parser.createDeadlineTask("deadline assignment 1 / by"));
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please add a deadline in the format: /by [dd-mm-yyyy hh:mm]!", e.getMessage());
        }
    }
}
