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

    /**
     * Marks the task at the index as done.
     * 
     * @param index The index of the task.
     */
    public void markTaskAsDone(int index) {
        this.get(index).markAsDone();
    }

    /**
     * Marks the task at the index as not done.
     * 
     * @param index The index of the task.
     */
    public void markTaskAsNotDone(int index) {
        this.get(index).markAsNotDone();
    }
}
