package yorm.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import yorm.exception.YormException;

/**
 * Base abstract class for all tasks to extend from.
 */
public abstract class Task {
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

    /**
     * Returns the save string of the task, used when saving tasks to the disk.
     *
     * @return The save string of the task.
     */
    public abstract String toSaveString();

    /**
     * Loads a task from save string, used when loading tasks from the disk.
     *
     * @param string The save string of the task.
     * @return The task parsed from the save string.
     * @throws YormException If an error occurs during the parsing of the save string.
     */
    public static Task fromSaveString(String string) throws YormException {
        Task task;
        String[] split = string.split(" \\| ");
        try {
            switch (split[0]) {
            case "T" -> {
                if (split.length != 3) {
                    throw new YormException("Invalid todo save string");
                }
                task = new Todo(split[2]);
            }
            case "D" -> {
                if (split.length != 4) {
                    throw new YormException("Invalid deadline save string");
                }
                task = new Deadline(split[2], LocalDate.parse(split[3]));
            }
            case "E" -> {
                if (split.length != 5) {
                    throw new YormException("Invalid event save string");
                }
                task = new Event(split[2], LocalDate.parse(split[3]), LocalDate.parse(split[4]));
            }
            case "A" -> {
                if (split.length != 4) {
                    throw new YormException("Invalid after save string");
                }
                task = new After(split[2], LocalDate.parse(split[3]));
            }
            default -> throw new YormException("Invalid save string");
            }
        } catch (DateTimeParseException e) {
            throw new YormException("Invalid save string");
        }
        if (Objects.equals(split[1], "1")) {
            task.markAsDone();
        }
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Task other) {
            return this.toSaveString().equals(other.toSaveString());
        }
        return false;
    }
}
