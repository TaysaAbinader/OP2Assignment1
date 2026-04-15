package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEntryPoints {
    @Test
    void touchAppConstructor() {
        // Covers the implicit constructor and class declaration of App
        App app = new App();
        assertNotNull(app);
    }

    @Test
    void touchMainGuiConstructor() {
        // Covers the MainGui class without actually launching the UI thread
        MainGui gui = new MainGui();
        assertNotNull(gui);
    }
}
