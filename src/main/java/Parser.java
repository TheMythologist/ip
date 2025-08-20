import java.time.LocalDate;

public class Parser {
    public static String removePrefix(String s, String prefix) {
        if (s != null && s.startsWith(prefix)) {
            return s.split(prefix, 2)[1];
        }
        return s;
    }

    public static Command parse(String command) throws YormException {
        if (command.equals("bye")) {
            return new ExitCommand();
        } else if (command.equals("list")) {
            return new ListCommand();
        } else if (command.startsWith("delete ")) {
            String[] split = command.split(" ");
            if (split.length != 2) {
                throw new YormException("Error in delete instruction");
            }

            try {
                int index = Integer.parseInt(split[1]);
                return new DeleteCommand(index - 1);
            } catch (NumberFormatException e) {
                throw new YormException("Error in delete instruction");
            }
        } else if (command.startsWith("mark ")) {
            String[] split = command.split(" ");
            if (split.length != 2) {
                throw new YormException("Error in mark instruction");
            }

            int index;
            try {
                index = Integer.parseInt(split[1]);
                return new MarkCommand(index - 1, true);
            } catch (NumberFormatException e) {
                throw new YormException("Error in mark instruction");
            }
        } else if (command.startsWith("unmark ")) {
            String[] split = command.split(" ");
            if (split.length != 2) {
                throw new YormException("Error in unmark instruction");
            }

            int index;
            try {
                index = Integer.parseInt(split[1]);
                return new MarkCommand(index - 1, false);
            } catch (NumberFormatException e) {
                throw new YormException("Error in unmark instruction");
            }
        } else {
            Task task;
            if (command.startsWith("todo ")) {
                task = new Todo(removePrefix(command, "todo "));
            } else if (command.startsWith("deadline ")) {
                String toParse = removePrefix(command, "deadline ");
                String[] split = toParse.split(" /by ");
                if (split.length != 2) {
                    throw new YormException("Error in deadline instruction");
                }
                task = new Deadline(split[0], LocalDate.parse(split[1]));
            } else if (command.startsWith("event ")) {
                String toParse = removePrefix(command, "event ");
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
            return new AddCommand(task);
        }
    }
}
