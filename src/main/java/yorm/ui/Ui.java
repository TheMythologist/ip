package yorm.ui;

import java.util.Scanner;

import yorm.task.Task;
import yorm.tasklist.TaskList;

public class Ui implements AutoCloseable {
    Scanner s = new Scanner(System.in);

    @Override
    public void close() {
        s.close();
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String string) {
        System.out.printf("YormError: %s%n", string);
    }

    public void showLoadingError(String filePath) {
        System.out.printf("Error loading save from path %s%n", filePath);
    }

    public void showTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("There are no tasks yet!");
            return;
        }

        int counter = 1;
        for (Task task : tasks) {
            System.out.printf("%d.%s%n", counter, task);
            counter++;
        }
    }

    public void showDeletedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list.%n", taskSize, taskSize == 1 ? "s" : "");
    }

    public void showAddedTask(Task task, TaskList tasks) {
        int taskSize = tasks.size();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d task%s in the list%n", taskSize, taskSize == 1 ? "s" : "");
    }

    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as not done yet:");
        System.out.println(task);
    }

    public String readCommand() {
        return s.nextLine();
    }
}
