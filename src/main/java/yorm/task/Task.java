package yorm.task;

import java.io.Serializable;

/**
 * Base abstract class for all tasks to extend from.
 */
public abstract class Task implements Serializable {
    /** The description of the task */
    protected String description;
    protected boolean isDone;

    /**
     * Creates a basic {@code Task}.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the status icon of the task.
     * Returns X if it is done, else returns a whitespace character.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Task other) {
            return this.toString().equals(other.toString());
        }
        return false;
    }
}
