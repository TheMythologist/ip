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
            } else if (string.startsWith("mark ")) {
                String[] split = string.split(" ");
                if (split.length != 2) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                int index;
                try {
                    index = Integer.parseInt(split[1]);
                    bot.markTaskAsDone(index - 1);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(bot.getTask(index - 1));
            } else if (string.startsWith("unmark ")) {
                String[] split = string.split(" ");
                if (split.length != 2) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                int index;
                try {
                    index = Integer.parseInt(split[1]);
                    bot.markTaskAsNotDone(index - 1);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Error in mark instruction");
                    continue;
                }

                System.out.println("Nice! I've marked this task as not done yet:");
                System.out.println(bot.getTask(index - 1));
            } else {
                bot.addText(string);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        s.close();
    }
}
