import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Chatbot {
    private final ArrayList<Task> tasks;

    public Chatbot() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        this.saveToFile();
    }

    public Task deleteTask(int index) {
        Task task = this.tasks.remove(index);
        this.saveToFile();
        return task;
    }

    public void printTasks() {
        int counter = 1;
        for (Task task : this.tasks) {
            System.out.printf("%d.%s%n", counter, task);
            counter++;
        }
    }

    public void markTaskAsDone(int index) {
        this.tasks.get(index).markAsDone();
        this.saveToFile();
    }

    public void markTaskAsNotDone(int index) {
        this.tasks.get(index).markAsNotDone();
        this.saveToFile();
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

    public void saveToFile() {
        try {
            File file = new File("data");
            file.mkdir();
            FileWriter fileWriter = new FileWriter("data/yorm.txt");
            for (Task task : this.tasks) {
                fileWriter.write(String.format("%s\n", task.toSaveString()));
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving to file");
        }
    }

    public static Chatbot readFromFile() {
        Chatbot bot = new Chatbot();
        File file = new File("data/yorm.txt");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String taskString = reader.nextLine();
                bot.addTask(Task.fromSaveString(taskString));
            }
        } catch (FileNotFoundException _) {
        } catch (YormException e) {
            System.out.println("Error parsing save file, creating backup file...");
            file.renameTo(new File("data/yorm.txt.bak"));
        }
        return bot;
    }

    public void eventLoop() {
        Scanner s = new Scanner(System.in);

        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");

        while (true) {
            try {
                String string = s.nextLine();
                if (string.equals("bye")) {
                    break;
                } else if (string.equals("list")) {
                    this.printTasks();
                } else if (string.startsWith("delete ")) {
                    String[] split = string.split(" ");
                    if (split.length != 2) {
                        throw new YormException("Error in delete instruction");
                    }

                    try {
                        int index = Integer.parseInt(split[1]);
                        Task deletedTask = this.deleteTask(index - 1);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println(deletedTask);
                        System.out.printf("Now you have %d tasks in the list.%n", this.getTaskLength());
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        throw new YormException("Error in delete instruction");
                    }

                } else if (string.startsWith("mark ")) {
                    String[] split = string.split(" ");
                    if (split.length != 2) {
                        throw new YormException("Error in mark instruction");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(split[1]);
                        this.markTaskAsDone(index - 1);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        throw new YormException("Error in mark instruction");
                    }

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(this.getTask(index - 1));
                } else if (string.startsWith("unmark ")) {
                    String[] split = string.split(" ");
                    if (split.length != 2) {
                        throw new YormException("Error in unmark instruction");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(split[1]);
                        this.markTaskAsNotDone(index - 1);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        throw new YormException("Error in unmark instruction");
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
                            throw new YormException("Error in deadline instruction");
                        }
                        task = new Deadline(split[0], LocalDate.parse(split[1]));
                    } else if (string.startsWith("event ")) {
                        String toParse = removePrefix(string, "event ");
                        String[] split = toParse.split(" /from ");
                        if (split.length != 2) {
                            throw new YormException("Error in event instruction");
                        }
                        String[] split2 = split[1].split(" /to ");
                        if (split2.length != 2) {
                            throw new YormException("Error in event instruction");
                        }
                        task = new Event(split[0], LocalDate.parse(split2[0]), LocalDate.parse(split2[1]));
                    } else {
                        throw new YormException("Invalid instruction");
                    }
                    this.addTask(task);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(task);
                    System.out.printf("Now you have %d tasks in the list%n", this.getTaskLength());
                }
            } catch (YormException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        s.close();
    }
}
