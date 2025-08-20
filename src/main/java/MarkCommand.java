public class MarkCommand extends Command {
    private final int taskIndex;
    private final boolean done;

    public MarkCommand(int taskIndex, boolean done) {
        this.taskIndex = taskIndex;
        this.done = done;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YormException {
        Task task;
        try {
            task = tasks.get(this.taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new YormException("Invalid task number");
        }

        if (this.done) {
            task.markAsDone();
            ui.showMarkedTask(task);
        } else {
            task.markAsNotDone();
            ui.showUnmarkedTask(task);
        }
        storage.save(tasks);
    }
}
