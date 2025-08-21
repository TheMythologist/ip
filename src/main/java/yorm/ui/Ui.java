package yorm.ui;

import java.util.Scanner;

import yorm.task.Task;
import yorm.tasklist.TaskList;

/**
 * Handles the printing to Stdout, and reading of commands from Stdin.
 */
public class Ui implements AutoCloseable {
    private final Scanner s = new Scanner(System.in);

    @Override
    public void close() {
        s.close();
    }

    /**
     * Prints the welcome message to Stdout.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");
    }

    /**
     * Prints the goodbye message to Stdout.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an empty line to Stdout.
     * Commonly used to demarcate separations between user inputs and program outputs.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the error message to Stdout.
     *
     * @param string The error message.
     */
    public void showError(String string) {
        System.out.printf("YormError: %s%n", string);
    }

    /**
     * Prints the loading error message to Stdout.
     * Used when an error occurs when loading tasks from a save file.
     *
     * @param filePath The filepath of the save file.
     */
    public void showLoadingError(String filePath) {
        System.out.printf("Error loading save from path %s%n", filePath);
    }

    /**
     * Prints the tasks to Stdout in a human-readable form.
     *
     * @param tasks The tasks to be displayed.
     */
    public void showTasks(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks yet!");
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
     * @param task The deleted task.
     * @param tasks The remaining tasks.
     */
    public void showDeletedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list.%n", taskSize, taskSize == 1 ? "s" : "");
    }

    /**
     * Prints the added task to Stdout.
     *
     * @param task The added task.
     * @param tasks The list of tasks after addition.
     */
    public void showAddedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list%n", taskSize, taskSize == 1 ? "s" : "");
    }

    /**
     * Prints the marked task to Stdout.
     *
     * @param task The marked task.
     */
    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Prints the unmarked task to Stdout.
     *
     * @param task The unmarked task.
     */
    public void showUnmarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as not done yet:");
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
            System.out.println("Could not find any matching tasks!");
            return;
        }

        System.out.println("Here are the matching tasks in your list:");
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
        return s.nextLine();
    }
}
