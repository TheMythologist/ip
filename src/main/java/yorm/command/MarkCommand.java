package yorm.command;

import yorm.exception.YormException;
import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Command to mark a task as done or not done.
 */
public class MarkCommand extends Command {
    /** Index of task to be marked */
    protected final int taskIndex;

    /** Whether the task should be marked as done or not done */
    protected final boolean isDone;

    /**
     * Creates a new {@code MarkCommand} that will mark a specified task
     * as either done or not done.
     *
     * @param taskIndex Index of task to be marked.
     * @param isDone    {@code true} If the task should be marked as done,
     *                  {@code false} if it should be marked as not done.
     */
    public MarkCommand(int taskIndex, boolean isDone) {
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YormException {
        assert this.taskIndex > 0 : "Task index should be positive";

        Task task;
        try {
            task = tasks.get(this.taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new YormException("Invalid task number");
        }

        if (this.isDone) {
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
            return this.taskIndex == other.taskIndex && this.isDone == other.isDone;
        }
        return false;
    }
}
