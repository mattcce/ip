package parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandParserTest {
    @Test
    public void parse_simpleCommand_success() {
        String cmdString = "test";

        CommandParser.Command cmd = CommandParser.parse(cmdString);

        assertEquals("test", cmd.getImperative());
        assertEquals(0, cmd.getAllParameters().length);
    }

    @Test
    public void parse_simpleCommandWithParameters_success() {
        String cmdString = "test param0 param1";

        CommandParser.Command cmd = CommandParser.parse(cmdString);

        assertEquals("test", cmd.getImperative());
        assertEquals(2, cmd.getAllParameters().length);
        assertEquals("param0", cmd.getParameter(0));
        assertEquals("param1", cmd.getParameter(1));
    }

    @Test
    public void parse_commandWithOptions_success() {
        String cmdString = "test /opt1 long value /opt2 single";

        CommandParser.Command cmd = CommandParser.parse(cmdString);

        assertEquals("test", cmd.getImperative());
        assertEquals(0, cmd.getAllParameters().length);
        assertEquals("long value", cmd.getOptionValue("opt1"));
        assertEquals("single", cmd.getOptionValue("opt2"));
    }

    @Test
    public void parse_complexCommand_success() {
        String cmdString = "complex param0 param1 /opt1 long value /opt2 single";

        CommandParser.Command cmd = CommandParser.parse(cmdString);

        assertEquals("complex", cmd.getImperative());
        assertEquals(2, cmd.getAllParameters().length);
        assertEquals("param0", cmd.getParameter(0));
        assertEquals("param1", cmd.getParameter(1));
        assertEquals("long value", cmd.getOptionValue("opt1"));
        assertEquals("single", cmd.getOptionValue("opt2"));
    }
}
