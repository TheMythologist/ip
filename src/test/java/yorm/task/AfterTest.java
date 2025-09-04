package yorm.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class AfterTest {
    @Test
    public void after_creation_correctStrings() {
        After after = new After("return book", LocalDate.of(2026, 6, 6));
        assertEquals("[A][ ] return book (after: Jun 6 2026)", after.toString());

        after.markAsDone();
        assertEquals("[A][X] return book (after: Jun 6 2026)", after.toString());
    }
}
