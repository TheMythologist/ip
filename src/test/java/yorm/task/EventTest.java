package yorm.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void event_creation_correctStrings() {
        Event event = new Event("project meeting", LocalDate.of(2026, 8, 6), LocalDate.of(2026, 8, 7));
        assertEquals("[E][ ] project meeting (from: Aug 6 2026 to: Aug 7 2026)", event.toString());

        event.markAsDone();
        assertEquals("[E][X] project meeting (from: Aug 6 2026 to: Aug 7 2026)", event.toString());
    }
}
