package yorm.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void deadline_creation_correctStrings() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 6, 6));
        assertEquals("[D][ ] return book (by: Jun 6 2026)", deadline.toString());
        assertEquals("D | 0 | return book | 2026-06-06", deadline.toSaveString());

        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Jun 6 2026)", deadline.toString());
        assertEquals("D | 1 | return book | 2026-06-06", deadline.toSaveString());
    }
}
