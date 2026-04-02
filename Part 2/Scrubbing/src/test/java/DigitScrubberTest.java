import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Services.DigitScrubber;

public class DigitScrubberTest {

    private DigitScrubber scrubber;

    @BeforeEach
    void setUp() {
        scrubber = new DigitScrubber();
    }

    @Test
    void testScrub_NullInput_ThrowsException() {
        assertThrows(NullPointerException.class, () -> scrubber.scrub(null));
    }

    @Test
    void testScrub_BlankInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub("   "));
    }

    @Test
    void testScrub_NormalDigits_ReplacesWithX() {
        String input = "My ID is 1234";
        String expected = "My ID is XXXX";
        Assertions.assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_PriceDigits_DoesNotReplace() {
        String input = "The cost is 50$";
        String expected = "The cost is 50$";
        Assertions.assertEquals(expected, scrubber.scrub(input));
    }
}