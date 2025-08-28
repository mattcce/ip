package fmt;

import java.time.format.DateTimeFormatter;

public class Formatters {
    public static final DateTimeFormatter DT_ENTRY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd kkmm");
    public static final DateTimeFormatter DT_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
}
