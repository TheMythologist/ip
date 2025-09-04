package yorm.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that contains a deadline to be completed by.
 */
public class Deadline extends Task {
    /** The date of the deadline to be completed by */
    protected LocalDate by;

    /**
     * Creates a new {@code Deadline}.
     *
     * @param description Description of the deadline.
     * @param by Date the deadline is to be completed by.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(),
                this.by.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
        );
    }
}
