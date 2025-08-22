package yorm.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that contains a date to be completed after.
 */
public class After extends Task {
    /** The date of the task to be completed after */
    protected LocalDate after;

    /**
     * Creates a new {@code After}.
     *
     * @param description Description of the after task.
     * @param by Date the task is to be completed after.
     */
    public After(String description, LocalDate after) {
        super(description);
        this.after = after;
    }

    @Override
    public String toSaveString() {
        return String.format("A | %d | %s | %s", this.isDone ? 1 : 0, this.description, this.after);
    }

    @Override
    public String toString() {
        return String.format("[A]%s (after: %s)",
                super.toString(),
                this.after.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
        );
    }
}
