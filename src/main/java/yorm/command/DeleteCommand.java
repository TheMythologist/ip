package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YormException {
        Task task;
        try {
            task = tasks.remove(this.taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new YormException("Invalid task number");
        }
        ui.showDeletedTask(task, tasks);
        storage.save(tasks);
    }
}
