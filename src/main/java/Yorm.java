public class Yorm {
    public static void main(String[] args) {
        Chatbot bot = Chatbot.readFromFile();
        bot.eventLoop();
    }
}
