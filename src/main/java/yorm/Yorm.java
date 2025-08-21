package yorm;

import yorm.command.Command;
import yorm.exception.YormException;
import yorm.parser.Parser;
import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Base chatbot application.
 */
public class Yorm {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates the {@code Yorm} application.
     *
     * @param filePath The filepath to read the save file from.
     */
    public Yorm(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (YormException e) {
            ui.showLoadingError(filePath);
            tasks = new TaskList();
        }
    }

    /**
     * The main event loop of the {@code Yorm} application.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (YormException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Yorm("data/yorm.txt").run();
    }
}
