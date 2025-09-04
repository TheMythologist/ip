package yorm.yormjava;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import yorm.command.Command;
import yorm.enums.CommandEnum;
import yorm.exception.YormException;
import yorm.parser.Parser;
import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Base chatbot application.
 */
public class Yorm {
    private static final String DEFAULT_FILE_PATH = "data/yorm.bin";

    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final PrintStream redirectedStream = new PrintStream(this.buffer);

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    private String commandType;

    /**
     * Creates the {@code Yorm} application with the default save filepath.
     */
    public Yorm() {
        this(Yorm.DEFAULT_FILE_PATH);
    }

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
                ui.showError(e);
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Yorm().run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        this.ui.setOut(this.redirectedStream);
        try {
            Command c = Parser.parse(input);
            c.execute(this.tasks, this.ui, this.storage);
            this.commandType = c.getClass().getSimpleName();
            String output = buffer.toString();
            buffer.reset();
            return output;
        } catch (YormException e) {
            this.commandType = "YormException";
            return ui.getErrorMessage(e);
        }
    }

    public CommandEnum getCommandType() {
        try {
            return CommandEnum.valueOf(this.commandType);
        } catch (IllegalArgumentException e) {
            return CommandEnum.Default;
        }
    }
}
