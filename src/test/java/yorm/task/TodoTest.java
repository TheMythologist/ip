package yorm.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {
    @Test
    public void testStrings() {
        Todo todo = new Todo("read book");
        assertEquals(todo.toString(), "[T][ ] read book");
        assertEquals(todo.toSaveString(), "T | 0 | read book");

        todo.markAsDone();
        assertEquals(todo.toString(), "[T][X] read book");
        assertEquals(todo.toSaveString(), "T | 1 | read book");
    }
}
