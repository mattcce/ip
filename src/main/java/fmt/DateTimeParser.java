package fmt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    private static final DateTimeFormatter DT_ENTRY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd kkmm");
    private static final DateTimeFormatter DT_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public static LocalDateTime parseAsEntry(String s) {
        return LocalDateTime.parse(s, DT_ENTRY_FORMAT);
    }

    public static String unparse(LocalDateTime ldt) {
        return ldt.format(DT_ENTRY_FORMAT);
    }

    public static String display(LocalDateTime ldt) {
        return ldt.format(DT_DISPLAY_FORMAT);
    }
}
