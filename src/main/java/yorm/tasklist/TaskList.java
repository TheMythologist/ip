package yorm.tasklist;

import java.util.ArrayList;

import yorm.task.Task;

public class TaskList extends ArrayList<Task> {
    public TaskList() {
        super();
    }

    public TaskList(ArrayList<Task> tasks) {
        super(tasks);
    }

    public void markTaskAsDone(int index) {
        this.get(index).markAsDone();
    }

    public void markTaskAsNotDone(int index) {
        this.get(index).markAsNotDone();
    }
}
