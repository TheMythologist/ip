public abstract class Command {
    abstract boolean isExit();

    abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YormException;
}
