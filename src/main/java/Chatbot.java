import java.util.ArrayList;
import java.util.Scanner;

public class Chatbot {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void printTasks() {
        int counter = 1;
        for (Task task : this.tasks) {
            System.out.println(String.format("%d.%s", counter, task));
            counter++;
        }
    }

    public void markTaskAsDone(int index) {
        this.tasks.get(index).markAsDone();
    }

    public void markTaskAsNotDone(int index) {
        this.tasks.get(index).markAsNotDone();
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    public int getTaskLength() {
        return this.tasks.size();
    }

    public static String removePrefix(String s, String prefix) {
        if (s != null && s.startsWith(prefix)) {
            return s.split(prefix, 2)[1];
        }
        return s;
    }

    public void eventLoop() {
        Scanner s = new Scanner(System.in);

        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");

        while (true) {
            String string = s.nextLine();
            if (string.equals("bye")) {
                break;
            } else if (string.equals("list")) {
                this.printTasks();
            } else if (string.startsWith("mark ")) {
                String[] split = string.split(" ");
                if (split.length != 2) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                int index;
                try {
                    index = Integer.parseInt(split[1]);
                    this.markTaskAsDone(index - 1);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(this.getTask(index - 1));
            } else if (string.startsWith("unmark ")) {
                String[] split = string.split(" ");
                if (split.length != 2) {
                    System.out.println("Error in unmark instruction");
                    continue;
                }

                int index;
                try {
                    index = Integer.parseInt(split[1]);
                    this.markTaskAsNotDone(index - 1);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Error in unmark instruction");
                    continue;
                }

                System.out.println("Nice! I've marked this task as not done yet:");
                System.out.println(this.getTask(index - 1));
            } else {
                Task task;
                if (string.startsWith("todo ")) {
                    task = new Todo(removePrefix(string, "todo "));
                } else if (string.startsWith("deadline ")) {
                    String toParse = removePrefix(string, "deadline ");
                    String[] split = toParse.split(" /by ");
                    if (split.length != 2) {
                        System.out.println("Error in deadline instruction");
                        continue;
                    }
                    task = new Deadline(split[0], split[1]);
                } else if (string.startsWith("event ")) {
                    String toParse = removePrefix(string, "event ");
                    String[] split = toParse.split(" /from ");
                    if (split.length != 2) {
                        System.out.println("Error in event instruction");
                        continue;
                    }
                    String[] split2 = split[1].split(" /to ");
                    if (split2.length != 2) {
                        System.out.println("Error in event instruction");
                        continue;
                    }
                    task = new Event(split[0], split2[0], split2[1]);
                } else {
                    System.out.println("Invalid instruction");
                    continue;
                }
                this.addTask(task);
                System.out.println("Got it. I've added this task:");
                System.out.println(task);
                System.out.println(String.format("Now you have %d tasks in the list", this.getTaskLength()));
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        s.close();
    }
}
