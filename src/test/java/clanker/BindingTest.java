package clanker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BindingTest {
    @Test
    public void bindingResolution_exactMatch_resolvesToTrue() {
        assertTrue(Binding.isCandidate("test", "test"));
    }

    @Test
    public void bindingResolution_prefixMatch_resolvesToTrue() {
        assertTrue(Binding.isCandidate("test", "t"));
    }
}
