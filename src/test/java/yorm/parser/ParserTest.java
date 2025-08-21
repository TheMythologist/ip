package yorm.parser;

import org.junit.jupiter.api.Test;

import yorm.command.Command;
import yorm.command.ExitCommand;
import yorm.command.ListCommand;
import yorm.command.MarkCommand;
import yorm.exception.YormException;
import yorm.command.AddCommand;
import yorm.task.Deadline;
import yorm.task.Event;
import yorm.task.Todo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ParserTest {
    @Test
    public void testAddTodo() {
        Command command = assertDoesNotThrow(() -> Parser.parse("todo read book"));
        assertEquals(command, new AddCommand(new Todo("read book")));
    }

    @Test
    public void testAddDeadline() {
        Command command = assertDoesNotThrow(() -> Parser.parse("deadline return book /by 2026-06-06"));
        assertEquals(command, new AddCommand(new Deadline("return book", LocalDate.of(2026, 6, 6))));
    }

    @Test
    public void testAddEvent() {
        Command command = assertDoesNotThrow(() -> Parser.parse("event project meeting /from 2026-08-06 /to 2026-08-07"));
        assertEquals(command, new AddCommand(new Event("project meeting", LocalDate.of(2026, 8, 6), LocalDate.of(2026, 8, 7))));
    }

    @Test
    public void testExit() {
        Command command = assertDoesNotThrow(() -> Parser.parse("bye"));
        assertEquals(command, new ExitCommand());
    }

    @Test
    public void testList() {
        Command command = assertDoesNotThrow(() -> Parser.parse("list"));
        assertEquals(command, new ListCommand());
    }

    @Test
    public void testMark() {
        Command command = assertDoesNotThrow(() -> Parser.parse("mark 1"));
        assertEquals(command, new MarkCommand(0, true));
    }

    @Test
    public void testUnmark() {
        Command command = assertDoesNotThrow(() -> Parser.parse("unmark 1"));
        assertEquals(command, new MarkCommand(0, false));
    }

    @Test
    public void testMarkError() {
        assertThrows(YormException.class, () -> Parser.parse("mark -1"), "Error in mark instruction");
        assertThrows(YormException.class, () -> Parser.parse("mark not a number"), "Error in mark instruction");
        assertThrows(YormException.class, () -> Parser.parse("mark 0"), "Error in mark instruction");
    }

    @Test
    public void testUnmarkError() {
        assertThrows(YormException.class, () -> Parser.parse("unmark -1"), "Error in unmark instruction");
        assertThrows(YormException.class, () -> Parser.parse("unmark not a number"), "Error in unmark instruction");
        assertThrows(YormException.class, () -> Parser.parse("unmark 0"), "Error in unmark instruction");
    }

    @Test
    public void testDeleteError() {
        assertThrows(YormException.class, () -> Parser.parse("delete -1"), "Error in delete instruction");
        assertThrows(YormException.class, () -> Parser.parse("delete not a number"), "Error in delete instruction");
        assertThrows(YormException.class, () -> Parser.parse("delete 0"), "Error in delete instruction");
    }

    @Test
    public void testInvalidInstruction() {
        assertThrows(YormException.class, () -> Parser.parse("invalid instruction"), "Invalid instruction");
    }

    @Test
    public void testInvalidDeadline() {
        assertThrows(YormException.class, () -> Parser.parse("deadline hello"), "Error in deadline instruction");
        assertThrows(YormException.class, () -> Parser.parse("deadline hello /by invalidDate"), "Error in deadline instruction");
    }

    @Test
    public void testInvalidEvent() {
        assertThrows(YormException.class, () -> Parser.parse("event hello"), "Error in event instruction");
        assertThrows(YormException.class, () -> Parser.parse("event hello /from invalidDate /to 2026-08-07"), "Error in deadline instruction");
        assertThrows(YormException.class, () -> Parser.parse("event hello /from 2026-08-07 /to invalidDate"), "Error in deadline instruction");
    }
}
