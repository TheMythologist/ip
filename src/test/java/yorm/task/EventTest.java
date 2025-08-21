package yorm.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class EventTest {
    @Test
    public void event_creation_correctStrings() {
        Event event = new Event("project meeting", LocalDate.of(2026, 8, 6), LocalDate.of(2026, 8, 7));
        assertEquals("[E][ ] project meeting (from: Aug 6 2026 to: Aug 7 2026)", event.toString());
        assertEquals("E | 0 | project meeting | 2026-08-06 | 2026-08-07", event.toSaveString());

        event.markAsDone();
        assertEquals("[E][X] project meeting (from: Aug 6 2026 to: Aug 7 2026)", event.toString());
        assertEquals("E | 1 | project meeting | 2026-08-06 | 2026-08-07", event.toSaveString());
    }
}
