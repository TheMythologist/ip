package yorm.ui;

import java.io.PrintStream;
import java.util.Scanner;

import yorm.exception.YormException;
import yorm.task.Task;
import yorm.tasklist.TaskList;

/**
 * Handles the printing to Stdout, and reading of commands from Stdin.
 */
public class Ui implements AutoCloseable {
    private final Scanner s = new Scanner(System.in);

    @Override
    public void close() {
        this.s.close();
    }

    public void setOut(PrintStream stream) {
        System.setOut(stream);
    }

    /**
     * Prints the welcome message to Stdout.
     */
    public void showWelcome() {
        System.out.println("✨ Hello! I'm Yorm — your tiny task assistant.");
        System.out.println("Ask me to add, list, find, or remove tasks. I'm all ears!");
    }

    /**
     * Prints the goodbye message to Stdout.
     */
    public void showGoodbye() {
        System.out.println("Goodbye — you've got this! Come back when you need a hand.");
    }

    /**
     * Prints an empty line to Stdout.
     * Commonly used to demarcate separations between user inputs and program
     * outputs.
     */
    public void showLine() {
        System.out.println("────────────────────────────────────────────────────────────");
    }

    /**
     * Returns the error message to be printed to Stdout.
     *
     * @param e The caught exception.
     * @return The error message to be printed to Stdout.
     */
    public String getErrorMessage(YormException e) {
        return String.format("YormError — oops: %s", e.getMessage());
    }

    /**
     * Prints the error message to Stdout.
     *
     * @param e The caught exception.
     */
    public void showError(YormException e) {
        System.out.println(this.getErrorMessage(e));
    }

    /**
     * Prints the loading error message to Stdout.
     * Used when an error occurs when loading tasks from a save file.
     *
     * @param filePath The filepath of the save file.
     */
    public void showLoadingError(String filePath) {
        System.out.printf("Oops — couldn't load save from: %s%n", filePath);
    }

    /**
     * Prints the tasks to Stdout in a human-readable form.
     *
     * @param tasks The tasks to be displayed.
     */
    public void showTasks(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty — time to get productive!");
            return;
        }

        int counter = 1;
        for (Task task : tasks) {
            System.out.printf("%d.%s%n", counter, task);
            counter++;
        }
    }

    /**
     * Prints the deleted task to Stdout.
     *
     * @param task  The deleted task.
     * @param tasks The remaining tasks.
     */
    public void showDeletedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list.%n", taskSize, taskSize == 1 ? "" : "s");
    }

    /**
     * Prints the added task to Stdout.
     *
     * @param task  The added task.
     * @param tasks The list of tasks after addition.
     */
    public void showAddedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list.%n", taskSize, taskSize == 1 ? "" : "s");
    }

    /**
     * Prints the marked task to Stdout.
     *
     * @param task The marked task.
     */
    public void showMarkedTask(Task task) {
        System.out.println("Nice! ✅ I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Prints the unmarked task to Stdout.
     *
     * @param task The unmarked task.
     */
    public void showUnmarkedTask(Task task) {
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Prints the found tasks to Stdout.
     * Used to display filtered tasks after the find command.
     *
     * @param foundTasks The tasks that were filtered after the find command.
     */
    public void showFoundTasks(TaskList foundTasks) {
        if (foundTasks.isEmpty()) {
            System.out.println("No matches found — try a different keyword.");
            return;
        }

        System.out.println("Here are the matching tasks I found:");
        int counter = 1;
        for (Task task : foundTasks) {
            System.out.printf("%d.%s%n", counter, task);
            counter++;
        }
    }

    /**
     * Reads a command from Stdin.
     *
     * @return The command string retrieved from Stdin.
     */
    public String readCommand() {
        return this.s.nextLine();
    }
}
