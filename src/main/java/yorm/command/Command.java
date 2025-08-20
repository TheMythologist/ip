package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public abstract class Command {
    public abstract boolean isExit();

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YormException;
}
