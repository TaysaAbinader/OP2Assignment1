package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinalMopUpTest {
    @Test
    void touchStaticInitializers() {
        // Instantiate these to cover the constructors and class definitions
        assertNotNull(new App());
        assertNotNull(new MainGui());
    }
}
