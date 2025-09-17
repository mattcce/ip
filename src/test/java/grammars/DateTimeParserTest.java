package grammars;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateTimeParserTest {
    @Test
    public void parse_wellFormedInputs_success() {
        String dt = "2025-10-10 1000";

        assertDoesNotThrow(() -> DateTimeParser.parseAsEntry(dt));
    }

    @Test
    public void constructor_malformedDateTime_exceptionThrown() {
        String dt = "20251010 1000h";

        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseAsEntry(dt));
    }

    @Test
    public void constructor_noTime_exceptionThrown() {
        String dt = "2025-10-10";

        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseAsEntry(dt));
    }

    @Test
    public void constructor_noDate_exceptionThrown() {
        String dt = "1000";

        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseAsEntry(dt));
    }

    @Test
    public void constructor_nonexistentDate_exceptionThrown() {
        String dt = "2025-25-10 1000";

        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseAsEntry(dt));
    }
}
