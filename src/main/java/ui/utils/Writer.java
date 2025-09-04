package ui.utils;

/**
 * Utility functional interface that acts as a callback to write to the UI.
 */
@FunctionalInterface
public interface Writer {
    void write(String s);
}
