package yorm.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import yorm.command.AddCommand;
import yorm.command.Command;
import yorm.command.DeleteCommand;
import yorm.command.ExitCommand;
import yorm.command.FindCommand;
import yorm.command.ListCommand;
import yorm.command.MarkCommand;
import yorm.exception.YormException;
import yorm.task.After;
import yorm.task.Deadline;
import yorm.task.Event;
import yorm.task.Task;
import yorm.task.Todo;

/**
 * In charge of parsing strings into the different commands.
 */
public class Parser {
    /**
     * Helper function to remove a prefix from a string.
     * If the string does not start with the prefix, the same string is returned.
     *
     * @param s The string to remove the prefix from.
     * @param prefix The prefix to be removed.
     * @return The string after prefix removal.
     */
    public static String removePrefix(String s, String prefix) {
        if (s != null && s.startsWith(prefix)) {
            return s.split(prefix, 2)[1];
        }
        return s;
    }

    /**
     * Parses the string and returns the corresponding command to be executed.
     *
     * @param command The string to be parsed into a command.
     * @return The command for the application to execute.
     * @throws YormException If an error occurs during parsing of the string.
     */
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
                if (index < 1) {
                    throw new YormException("Error in delete instruction");
                }
                assert index > 0;
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
                if (index < 1) {
                    throw new YormException("Error in mark instruction");
                }
                assert index > 0;
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
                if (index < 1) {
                    throw new YormException("Error in unmark instruction");
                }
                assert index > 0;
                return new MarkCommand(index - 1, false);
            } catch (NumberFormatException e) {
                throw new YormException("Error in unmark instruction");
            }
        } else if (command.startsWith("find ")) {
            String keyword = removePrefix(command, "find ");
            return new FindCommand(keyword);
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
                try {
                    task = new Deadline(split[0], LocalDate.parse(split[1]));
                } catch (DateTimeParseException e) {
                    throw new YormException("Error in deadline instruction");
                }
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
                try {
                    task = new Event(split[0], LocalDate.parse(split2[0]), LocalDate.parse(split2[1]));
                } catch (DateTimeParseException e) {
                    throw new YormException("Error in event instruction");
                }
            } else if (command.startsWith("after ")) {
                String toParse = removePrefix(command, "after ");
                String[] split = toParse.split(" /after ");
                if (split.length != 2) {
                    throw new YormException("Error in after instruction");
                }
                try {
                    task = new After(split[0], LocalDate.parse(split[1]));
                } catch (DateTimeParseException e) {
                    throw new YormException("Error in after instruction");
                }
            } else {
                throw new YormException("Invalid instruction");
            }
            return new AddCommand(task);
        }
    }
}
