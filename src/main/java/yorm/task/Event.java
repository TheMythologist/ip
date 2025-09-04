package yorm.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that contains a start an and end date.
 */
public class Event extends Task {
    /** The start date of the event */
    protected LocalDate from;

    /** The end date of the event */
    protected LocalDate to;

    /**
     * Creates a new {@code Event}.
     *
     * @param description Description of the event.
     * @param from Start date of the event.
     * @param to End date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
        );
    }
}
