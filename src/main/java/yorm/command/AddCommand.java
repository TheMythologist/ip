package yorm.command;

import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public class AddCommand extends Command {
    public final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(this.task);
        ui.showAddedTask(task, tasks);
        storage.save(tasks);
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            AddCommand other = (AddCommand) o;
            return this.task.equals(other.task);
        }
        return false;
    }
}
