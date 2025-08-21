package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public class MarkCommand extends Command {
    /** Index of task to be marked */
    public final int taskIndex;

    /** Whether the task should be marked as done or not done */
    public final boolean done;

    public MarkCommand(int taskIndex, boolean done) {
        this.taskIndex = taskIndex;
        this.done = done;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YormException {
        Task task;
        try {
            task = tasks.get(this.taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new YormException("Invalid task number");
        }

        if (this.done) {
            task.markAsDone();
            ui.showMarkedTask(task);
        } else {
            task.markAsNotDone();
            ui.showUnmarkedTask(task);
        }
        storage.save(tasks);
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            MarkCommand other = (MarkCommand) o;
            return this.taskIndex == other.taskIndex && this.done == other.done;
        }
        return false;
    }
}
