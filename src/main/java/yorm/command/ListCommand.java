package yorm.command;

import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public class ListCommand extends Command {
    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
