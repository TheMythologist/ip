import java.util.ArrayList;

public class Chatbot {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addText(String string) {
        this.tasks.add(new Task(string));
        System.out.println(String.format("added: %s", string));
    }

    public void printTexts() {
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
}
