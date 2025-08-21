package yorm.task;

/**
 * Basic task that has no associated dates.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveString() {
        return String.format("T | %d | %s", this.isDone ? 1 : 0, this.description);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
