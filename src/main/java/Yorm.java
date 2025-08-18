import java.util.ArrayList;
import java.util.Scanner;

public class Yorm {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");

        Chatbot bot = new Chatbot();

        while (true) {
            String string = s.nextLine();
            if (string.equals("bye")) {
                break;
            } else if (string.equals("list")) {
                bot.printTexts();
            } else {
                bot.addText(string);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        s.close();
    }
}

class Chatbot {
    private ArrayList<String> texts = new ArrayList<>();

    public void addText(String string) {
        this.texts.add(string);
        System.out.println(String.format("added: %s", string));
    }

    public void printTexts() {
        int counter = 1;
        for (String string : this.texts) {
            System.out.println(String.format("%d. %s", counter, string));
            counter++;
        }
    }
}
