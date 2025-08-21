package yorm.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    /** The start date of the event */
    protected LocalDate from;

    /** The end date of the event */
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveString() {
        return String.format("E | %d | %s | %s | %s", this.isDone ? 1 : 0, this.description, this.from, this.to);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy")), this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }
}
