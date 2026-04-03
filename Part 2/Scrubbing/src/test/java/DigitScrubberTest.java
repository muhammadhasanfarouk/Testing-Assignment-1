import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Services.DigitScrubber;

public class DigitScrubberTest {

    private DigitScrubber scrubber;

    @BeforeEach
    void setUp() {
        scrubber = new DigitScrubber();
    }

    // Exceptions

    @Test
    void testScrub_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scrubber.scrub(null));
    }
    // blank input tests
    @Test
    void testScrub_EmptyInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub(""));
    }
    @Test
    void testScrub_WhitespaceOnly_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub("   "));
    }

    @Test
    void testScrub_TabsAndNewlines_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> scrubber.scrub("\n\t"));
    }

    // Happy Paths

    @Test
    void testScrub_DigitsOnly_ReplacesAllDigits() {
        assertEquals("XXXX", scrubber.scrub("1234"));
    }

    @Test
    void testScrub_MixedTextAndDigits_ReplacesOnlyDigits() {
        assertEquals("ID XXX", scrubber.scrub("ID 123"));
    }

    @Test
    void testScrub_NoDigits_ReturnsSameString() {
        assertEquals("Hello World", scrubber.scrub("Hello World"));
    }

    // Price Handling

    @Test
    void testScrub_Price_ShouldNotBeScrubbed() {
        assertEquals("Cost is 50$", scrubber.scrub("Cost is 50$"));
    }

    @Test
    void testScrub_PriceAndOtherDigits_OnlyNonPriceDigitsScrubbed() {
        assertEquals("Cost is 50$ and XXX", scrubber.scrub("Cost is 50$ and 123"));
    }
    
    // Edge Cases

    @Test
    void testScrub_PriceWithSpace_ShouldBeScrubbed() {
        assertEquals("Cost is XX $", scrubber.scrub("Cost is 50 $"));
    }

    @Test
    void testScrub_EmbeddedPricePattern() {
        assertEquals("abc50$xyz", scrubber.scrub("abc50$xyz"));
    }

    @Test
    void testScrub_SingleDigit() {
        assertEquals("X", scrubber.scrub("1"));
    }

    @Test
    void testScrub_DigitsWithSpecialCharacters() {
        assertEquals("X-X-X", scrubber.scrub("1-2-3"));
    }

    @Test
    void testScrub_DigitsAtStartAndEnd() {
        assertEquals("XabcX", scrubber.scrub("1abc2"));
    }

    @Test
    void testScrub_MultipleNumbers() {
        assertEquals("XX XX XX", scrubber.scrub("12 34 56"));
    }

    @Test
    void testScrub_DigitsWithNewlines() {
        assertEquals("X\nX\nX", scrubber.scrub("1\n2\n3"));
    }

    @Test
    void testScrub_LargeNumbers() {
        assertEquals("XXXXXXXXXXXXXXXXXXXX", scrubber.scrub("12345678901234567890"));
    }

    @Test
    void testScrub_MixedComplexString() {
        String input = "User123 has 4 items costing 50$ each.";
        String expected = "UserXXX has X items costing 50$ each.";
        assertEquals(expected, scrubber.scrub(input));
    }
}