package yorm.parser;

import org.junit.jupiter.api.Test;

import yorm.command.Command;
import yorm.command.DeleteCommand;
import yorm.command.ExitCommand;
import yorm.command.FindCommand;
import yorm.command.ListCommand;
import yorm.command.MarkCommand;
import yorm.exception.YormException;
import yorm.command.AddCommand;
import yorm.task.Deadline;
import yorm.task.Event;
import yorm.task.Todo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class ParserTest {
    @Test
    public void parser_addTodo_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("todo read book"));
        assertEquals(new AddCommand(new Todo("read book")), command);
    }

    @Test
    public void parser_addDeadline_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("deadline return book /by 2026-06-06"));
        assertEquals(new AddCommand(new Deadline("return book", LocalDate.of(2026, 6, 6))), command);
    }

    @Test
    public void parser_addEvent_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("event project meeting /from 2026-08-06 /to 2026-08-07"));
        assertEquals(new AddCommand(new Event("project meeting", LocalDate.of(2026, 8, 6), LocalDate.of(2026, 8, 7))), command);
    }

    @Test
    public void parser_exit_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("bye"));
        assertEquals(new ExitCommand(), command);
    }

    @Test
    public void parser_list_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("list"));
        assertEquals(new ListCommand(), command);
    }

    @Test
    public void parser_mark_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("mark 1"));
        assertEquals(new MarkCommand(0, true), command);
    }

    @Test
    public void parser_delete_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("delete 1"));
        assertEquals(new DeleteCommand(0), command);
    }

    @Test
    public void parser_unmark_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("unmark 1"));
        assertEquals(new MarkCommand(0, false), command);
    }

    public void parser_find_correctCommand() {
        Command command = assertDoesNotThrow(() -> Parser.parse("find task"));
        assertEquals(command, new FindCommand("task"));
    }

    @Test
    public void parser_markInvalidNumber_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("mark -1"), "Error in mark instruction");
        assertThrows(YormException.class, () -> Parser.parse("mark not a number"), "Error in mark instruction");
        assertThrows(YormException.class, () -> Parser.parse("mark 0"), "Error in mark instruction");
    }

    @Test
    public void parser_unmarkInvalidNumber_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("unmark -1"), "Error in unmark instruction");
        assertThrows(YormException.class, () -> Parser.parse("unmark not a number"), "Error in unmark instruction");
        assertThrows(YormException.class, () -> Parser.parse("unmark 0"), "Error in unmark instruction");
    }

    @Test
    public void parser_deleteInvalidNumber_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("delete -1"), "Error in delete instruction");
        assertThrows(YormException.class, () -> Parser.parse("delete not a number"), "Error in delete instruction");
        assertThrows(YormException.class, () -> Parser.parse("delete 0"), "Error in delete instruction");
    }

    @Test
    public void parser_invalidInstruction_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("invalid instruction"), "Invalid instruction");
    }

    @Test
    public void parser_invalidDeadline_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("deadline hello"), "Error in deadline instruction");
        assertThrows(YormException.class, () -> Parser.parse("deadline hello /by invalidDate"), "Error in deadline instruction");
    }

    @Test
    public void parser_invalidEvent_exceptionThrown() {
        assertThrows(YormException.class, () -> Parser.parse("event hello"), "Error in event instruction");
        assertThrows(YormException.class, () -> Parser.parse("event hello /from invalidDate /to 2026-08-07"), "Error in deadline instruction");
        assertThrows(YormException.class, () -> Parser.parse("event hello /from 2026-08-07 /to invalidDate"), "Error in deadline instruction");
    }
}
