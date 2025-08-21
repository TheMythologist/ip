package yorm.tasklist;

import java.util.ArrayList;

import yorm.task.Task;

/**
 * List of tasks.
 */
public class TaskList extends ArrayList<Task> {
    public TaskList() {
        super();
    }

    public TaskList(ArrayList<Task> tasks) {
        super(tasks);
    }
}
