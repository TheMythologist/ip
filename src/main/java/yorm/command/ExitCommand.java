package yorm.command;

import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Command to exit the application.
 */
public class ExitCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }
}
