package serde;

// Ser(ialise)-de(serialise)r
public class Serde {
    public static String serialise(Serialisable obj) {
        return obj.serialise();
    }
}
