package yorm.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class DeadlineTest {
    @Test
    public void testStrings() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 6, 6));
        assertEquals(deadline.toString(), "[D][ ] return book (by: Jun 6 2026)");
        assertEquals(deadline.toSaveString(), "D | 0 | return book | 2026-06-06");

        deadline.markAsDone();
        assertEquals(deadline.toString(), "[D][X] return book (by: Jun 6 2026)");
        assertEquals(deadline.toSaveString(), "D | 1 | return book | 2026-06-06");
    }
}
