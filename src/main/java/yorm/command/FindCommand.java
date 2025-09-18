package yorm.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import yorm.storage.Storage;
import yorm.tasklist.TaskList;
import yorm.ui.Ui;

/**
 * Command to find a task from existing tasks.
 */
public class FindCommand extends Command {
    /** The keyword to search in the tasks */
    protected final String[] keywords;

    public FindCommand(String... keyword) {
        this.keywords = keyword;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList found = (TaskList) tasks.stream()
                .filter(task -> Arrays.asList(this.keywords).stream()
                        .anyMatch(keyword -> task.getDescription().toLowerCase().contains(keyword)))
                .collect(Collectors.toCollection(ArrayList::new));

        ui.showFoundTasks(found);
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            FindCommand other = (FindCommand) o;
            return Arrays.equals(this.keywords, other.keywords);
        }
        return false;
    }
}
