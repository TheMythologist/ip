public class YormException extends Exception {
    public YormException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return String.format("Yorm chatbot error: %s", super.toString());
    }
}
