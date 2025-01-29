import java.util.ArrayList;
import java.util.Scanner;

public class Bob {
    private String filePath;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;
    private Ui ui;

    public Bob(String filePath) {
        this.filePath = filePath;
        this.tasks = new TaskList(new ArrayList<Task>(100));
        this.storage = new Storage(this.tasks);
        this.parser = new Parser(this.tasks, this.storage);
        this.ui = new Ui(this.parser);
    }

    public void run() throws Exception {
        try {
            storage.loadFile(filePath);
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        this.ui.interact(this.parser);
    }

    public static void main(String[] args) throws Exception {
        new Bob("./data/tasks.txt").run();
    }
}
