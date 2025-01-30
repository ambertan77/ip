package bob;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import bob.task.Task;

import java.util.ArrayList;

public class StorageTest {
    @Test
    public void createTaskFromFile_createDeadlineTask_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("D / 0 / assignment 1 / 15/01/2025, 13:00");
        String expected = "[D][ ] assignment 1 (by: 15/01/2025, 13:00)";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_deadlineMarkAsDone_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("D / 1 / assignment 1 / 15/01/2025, 13:00");
        String expected = "[D][X] assignment 1 (by: 15/01/2025, 13:00)";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_createEventTask_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("E / 0 / consult / 19/01/2025, 16:30 / 19/01/2025, 17:30");
        String expected = "[E][ ] consult (from 19/01/2025, 16:30 to 19/01/2025, 17:30)";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_eventMarkAsDone_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("E / 1 / consult / 19/01/2025, 16:30 / 19/01/2025, 17:30");
        String expected = "[E][X] consult (from 19/01/2025, 16:30 to 19/01/2025, 17:30)";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_createTodoTask_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("T / 0 / exercise");
        String expected = "[T][ ] exercise";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_todoMarkAsDone_success() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        Task actual = storage.createTaskFromFile("T / 1 / exercise");
        String expected = "[T][X] exercise";
        assertEquals(expected, actual.toString());
    }

    @Test
    public void createTaskFromFile_wrongDeadlineTaskFormat_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        try {
            Task actual = storage.createTaskFromFile("D / assignment 1 15/01/2025, 13:00");
            assertEquals("[D][ ] assignment 1 (by: 15/01/2025, 13:00)", actual.toString());
            fail();
        } catch(ArrayIndexOutOfBoundsException e) {
            assertEquals("Ensure that the tasks in file are in the correct format.", e.getMessage());
        }
    }

    @Test
    public void createTaskFromFile_wrongDeadlineTimeFormat_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        try {
            Task actual = storage.createTaskFromFile("D / 0 / assignment 1 / 15-01-2025 13:00");
            assertEquals("[D][ ] assignment 1 (by: 15/01/2025, 13:00)", actual.toString());
            fail();
        } catch(Exception e) {
            assertEquals("Ensure that the deadline is given in the correct format.", e.getMessage());
        }
    }

    @Test
    public void createTaskFromFile_wrongEventTaskFormat_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        try {
            Task actual = storage.createTaskFromFile("E / consult, 19/01/2025, 16:30 to 19/01/2025, 17:30");
            assertEquals("[E][ ] consult (from 19/01/2025, 16:30 to 19/01/2025, 17:30)", actual.toString());
            fail();
        } catch(ArrayIndexOutOfBoundsException e) {
            assertEquals("Ensure that the tasks in file are in the correct format.", e.getMessage());
        }
    }

    @Test
    public void createTaskFromFile_wrongEventTimeFormat_exceptionThrown() {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        try {
            Task actual = storage.createTaskFromFile("E / 0 / consult / 19-01-2025 16:30 to 19-01-2025 17:30");
            assertEquals("[E][ ] consult (from 19/01/2025, 16:30 to 19/01/2025, 17:30)", actual.toString());
            fail();
        } catch(Exception e) {
            assertEquals("Ensure that the from and to fields are given in the correct format.", e.getMessage());
        }
    }

    @Test
    public void createTaskFromFile_wrongTodoTaskFormat_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList(new ArrayList<Task>(100));
        Storage storage = new Storage(tasks);
        try {
            Task actual = storage.createTaskFromFile("Todo: exercise");
            assertEquals("[T][ ] exercise", actual.toString());
            fail();
        } catch(ArrayIndexOutOfBoundsException e) {
            assertEquals("Ensure that the tasks in file are in the correct format.", e.getMessage());
        }
    }
}
