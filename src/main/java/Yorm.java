import java.util.Scanner;

public class Yorm {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Hello! I'm Yorm!");
        System.out.println("What can I do for you?");

        while (true) {
            String word = s.next();
            if (word.equals("bye")) {
                break;
            }
            System.out.println(word);
        }

        System.out.println("Bye. Hope to see you again soon!");
        s.close();
    }
}
