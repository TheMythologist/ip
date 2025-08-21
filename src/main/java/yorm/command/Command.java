package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Base abstract class for all commands to extend from.
 */
public abstract class Command {
    /**
     * Returns if the command is an exit command.
     *
     * @return true If the command should exit, false otherwise.
     */
    public abstract boolean isExit();

    /**
      * Executes the command with the provided task list, UI, and storage.
      *
      * @param tasks The task list to operate on.
      * @param ui The UI to interact with.
      * @param storage The storage to use for data persistence.
      * @throws YormException If an error occurs during execution.
      */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YormException;

    @Override
    public boolean equals(Object o) {
        return this.getClass() == o.getClass();
    }
}
