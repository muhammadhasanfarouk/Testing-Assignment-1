import static org.junit.jupiter.api.Assertions.*;

import Services.EmailScrubber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailScrubberTest {
    private EmailScrubber scrubber;

    @BeforeEach
    void setUp() {
        scrubber = new EmailScrubber();
    }

    @Test
    void testScrub_ValidEmail_ReplacesWithPlaceholder() {
        String input = "Contact me at test@example.com for more info.";
        String expected = "Contact me at [EMAIL_HIDDEN] for more info.";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scrubber.scrub(null));
    }

    @Test
    void testScrub_BlankInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub("   "));
    }
}