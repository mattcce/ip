package parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for parsing and formatting datetimes.
 */
public class DateTimeParser {
    private static final DateTimeFormatter DT_ENTRY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd kkmm");
    private static final DateTimeFormatter DT_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Parses a string into a LocalDateTime object, in the following format: `YYYY-MM-DD HHMM`, where HHMM is in 24hr *
     * format.
     *
     * @param s String matching the above schema to be parsed.
     * @return LocalDateTime object representing the given datetime.
     */
    public static LocalDateTime parseAsEntry(String s) {
        return LocalDateTime.parse(s, DT_ENTRY_FORMAT);
    }

    /**
     * Formats a LocalDateTime into a string that matches the parsed entry format to be used for storage purposes.
     *
     * @param ldt LocalDateTime object corresponding to some datetime.
     * @return String representation of that datetime.
     */
    public static String unparse(LocalDateTime ldt) {
        return ldt.format(DT_ENTRY_FORMAT);
    }

    /**
     * Formats a LocalDateTime into a string meant for display purposes only.
     *
     * @param ldt LocalDateTime object corresponding to some datetime.
     * @return Display string for that datetime.
     */
    public static String display(LocalDateTime ldt) {
        return ldt.format(DT_DISPLAY_FORMAT);
    }
}
