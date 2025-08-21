package yorm.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import yorm.command.AddCommand;
import yorm.command.Command;
import yorm.command.DeleteCommand;
import yorm.command.ExitCommand;
import yorm.command.ListCommand;
import yorm.command.MarkCommand;
import yorm.exception.YormException;
import yorm.task.Deadline;
import yorm.task.Event;
import yorm.task.Task;
import yorm.task.Todo;

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
                if (index < 1) {
                    throw new YormException("Error in mark instruction");
                }
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
                    throw new YormException("Error in mark instruction");
                }
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
            } else {
                throw new YormException("Invalid instruction");
            }
            return new AddCommand(task);
        }
    }
}
