package Tests;


import Services.DigitScrubber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitScrubberTest {

    private DigitScrubber scrubber;

    @BeforeEach
    void setUp() {
        scrubber = new DigitScrubber();
    }

    @Test
    @DisplayName("Should throw NullPointerException when input is null")
    void testScrub_NullInput_ThrowsException() {
        assertThrows(NullPointerException.class, () -> scrubber.scrub(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when input is blank")
    void testScrub_BlankInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub("   "));
    }

    @Test
    @DisplayName("Should replace standard digits with X")
    void testScrub_NormalDigits_ReplacesWithX() {
        String input = "My ID is 1234";
        String expected = "My ID is XXXX";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    @DisplayName("Should NOT scrub digits if they are part of a price (followed by $)")
    void testScrub_PriceDigits_DoesNotReplace() {
        String input = "The cost is 50$";
        String expected = "The cost is 50$";
        assertEquals(expected, scrubber.scrub(input));
    }
}