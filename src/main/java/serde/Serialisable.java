package serde;

/**
 * Interface for defining serialisations of data structures.
 */
public interface Serialisable {
    /**
     * Serialises the current object into a string representation that is sufficient to reconstruct the original object
     * with.
     *
     * @return String representation of the current object.
     */
    String serialise();
}