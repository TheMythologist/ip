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
     * @param s      The string to remove the prefix from.
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
     * Parses a delete command string and returns the delete command to be executed.
     *
     * @param command The string to be parsed into a delete command.
     * @return The delete command for the application to execute.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static DeleteCommand parseDeleteCommand(String command) throws YormException {
        String[] split = command.split(" ", 2);
        if (split.length != 2) {
            throw new YormException("Error in delete instruction");
        }

        try {
            int index = Integer.parseInt(split[1]);
            if (index < 1) {
                throw new YormException("Error in delete instruction");
            }
            assert index > 0 : "Index should be positive";
            return new DeleteCommand(index - 1);
        } catch (NumberFormatException e) {
            throw new YormException("Error in delete instruction");
        }
    }

    /**
     * Parses a mark command string and returns the mark command to be executed.
     *
     * @param command The string to be parsed into a mark command.
     * @return The mark command for the application to execute.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static MarkCommand parseMarkCommand(String command) throws YormException {
        String[] split = command.split(" ", 2);
        if (split.length != 2) {
            throw new YormException("Error in mark instruction");
        }

        int index;
        try {
            index = Integer.parseInt(split[1]);
            if (index < 1) {
                throw new YormException("Error in mark instruction");
            }
            assert index > 0 : "Index should be positive";
            return new MarkCommand(index - 1, true);
        } catch (NumberFormatException e) {
            throw new YormException("Error in mark instruction");
        }
    }

    /**
     * Parses an unmark command string and returns the unmark command to be
     * executed.
     *
     * @param command The string to be parsed into an unmark command.
     * @return The unmark command for the application to execute.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static MarkCommand parseUnmarkCommand(String command) throws YormException {
        String[] split = command.split(" ", 2);
        if (split.length != 2) {
            throw new YormException("Error in unmark instruction");
        }

        int index;
        try {
            index = Integer.parseInt(split[1]);
            if (index < 1) {
                throw new YormException("Error in unmark instruction");
            }
            assert index > 0 : "Index should be positive";
            return new MarkCommand(index - 1, false);
        } catch (NumberFormatException e) {
            throw new YormException("Error in unmark instruction");
        }
    }

    /**
     * Parses a deadline task string and returns the deadline task to be added.
     *
     * @param command The string to be parsed into a deadline task.
     * @return The deadline task for the application to add.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static Deadline parseDeadlineTask(String command) throws YormException {
        String toParse = removePrefix(command, "deadline ");
        String[] split = toParse.split(" /by ", 2);
        if (split.length != 2) {
            throw new YormException("Error in deadline instruction");
        }
        try {
            return new Deadline(split[0], LocalDate.parse(split[1]));
        } catch (DateTimeParseException e) {
            throw new YormException("Error in deadline instruction");
        }
    }

    /**
     * Parses an event task string and returns the event task to be added.
     *
     * @param command The string to be parsed into an event task.
     * @return The event task for the application to add.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static Event parseEventTask(String command) throws YormException {
        String toParse = removePrefix(command, "event ");
        String[] split = toParse.split(" /from ", 2);
        if (split.length != 2) {
            throw new YormException("Error in event instruction");
        }
        String[] split2 = split[1].split(" /to ", 2);
        if (split2.length != 2) {
            throw new YormException("Error in event instruction");
        }
        try {
            return new Event(split[0], LocalDate.parse(split2[0]), LocalDate.parse(split2[1]));
        } catch (DateTimeParseException e) {
            throw new YormException("Error in event instruction");
        }
    }

    /**
     * Parses an after task string and returns the after task to be added.
     *
     * @param command The string to be parsed into an after task.
     * @return The after task for the application to add.
     * @throws YormException If an error occurs during parsing of the string.
     */
    private static After parseAfterTask(String command) throws YormException {
        String toParse = removePrefix(command, "after ");
        String[] split = toParse.split(" /after ", 2);
        if (split.length != 2) {
            throw new YormException("Error in after instruction");
        }
        try {
            return new After(split[0], LocalDate.parse(split[1]));
        } catch (DateTimeParseException e) {
            throw new YormException("Error in after instruction");
        }
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
            return Parser.parseDeleteCommand(command);
        } else if (command.startsWith("mark ")) {
            return Parser.parseMarkCommand(command);
        } else if (command.startsWith("unmark ")) {
            return Parser.parseUnmarkCommand(command);
        } else if (command.startsWith("find ")) {
            return new FindCommand(removePrefix(command, "find "));
        } else {
            Task task;
            if (command.startsWith("todo ")) {
                task = new Todo(removePrefix(command, "todo "));
            } else if (command.startsWith("deadline ")) {
                task = Parser.parseDeadlineTask(command);
            } else if (command.startsWith("event ")) {
                task = Parser.parseEventTask(command);
            } else if (command.startsWith("after ")) {
                task = Parser.parseAfterTask(command);
            } else {
                throw new YormException("Invalid instruction");
            }
            return new AddCommand(task);
        }
    }
}
