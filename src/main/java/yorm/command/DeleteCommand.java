package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Command to delete a task from existing tasks.
 */
public class DeleteCommand extends Command {
    /** Index of task to be deleted */
    protected final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YormException {
        assert this.taskIndex > 0;

        Task task;
        try {
            task = tasks.remove(this.taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new YormException("Invalid task number");
        }
        ui.showDeletedTask(task, tasks);
        storage.save(tasks);
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            DeleteCommand other = (DeleteCommand) o;
            return this.taskIndex == other.taskIndex;
        }
        return false;
    }
}
