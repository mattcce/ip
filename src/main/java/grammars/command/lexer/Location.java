package grammars.command.lexer;

/**
 * Record storing the location of the token (contiguous indices [start, end)) in the original lexed string.
 */
record Location(int start, int end) {
}
