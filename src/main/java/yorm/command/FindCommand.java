package yorm.command;

import yorm.storage.Storage;
import yorm.task.Task;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

public class FindCommand extends Command {
    public final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList found = (TaskList) tasks.clone();
        found.removeIf((Task task) -> !task.description.contains(this.keyword));

        ui.showFoundTasks(found);
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            FindCommand other = (FindCommand) o;
            return this.keyword.equals(other.keyword);
        }
        return false;
    }
}
