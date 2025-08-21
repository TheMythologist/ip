package yorm.task;

import java.util.Objects;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import yorm.exception.YormException;

public abstract class Task {
    public final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (this.isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    public abstract String toSaveString();

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
                case null, default -> throw new YormException("Invalid save string");
            }
        } catch (DateTimeParseException _) {
            throw new YormException("Invalid save string");
        }
        if (Objects.equals(split[1], "1")) {
            task.markAsDone();
        }
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Task) {
            Task other = (Task) o;
            return this.toSaveString().equals(other.toSaveString());
        }
        return false;
    }
}
