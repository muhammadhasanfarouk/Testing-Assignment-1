import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Services.EmailScrubber;

class EmailScrubberTest {

    private EmailScrubber scrubber;

    @BeforeEach
    void setUp() {
        scrubber = new EmailScrubber();
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

    //  Happy Paths 

    @Test
    // this tests single email & email at the end of the string
    void testScrub_SingleEmail_ReplacedCorrectly() {
        String input = "Contact me at test@example.com";
        String expected = "Contact me at [EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    // this tests multiple emails & email at the beginning OR end of the string
    void testScrub_MultipleEmails_AllReplaced() {
        String input = "a@test.com b@test.com";
        String expected = "[EMAIL_HIDDEN] [EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailInMiddle_ReplacedCorrectly() {
        String input = "My email is test@example.com for more info.";
        String expected = "My email is [EMAIL_HIDDEN] for more info.";
        assertEquals(expected, scrubber.scrub(input));
    }

    //  Edge Cases 

    @Test
    void testScrub_NoEmail_ReturnsSameString() {
        String input = "Hello world";
        assertEquals(input, scrubber.scrub(input));
    }

    @Test
    void testScrub_InvalidEmail_NotReplaced() {
        String input = "test@com";
        assertEquals(input, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailWithSubdomain_ReplacedCorrectly() {
        String input = "user@mail.example.com";
        String expected = "[EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailWithPlus_ReplacedCorrectly() {
        String input = "user+tag@example.com";
        String expected = "[EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailWithPeriod_ReplacedCorrectly() {
        String input = "user.name@example.com";
        String expected = "[EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailWithNumbers_ReplacedCorrectly() {
        String input = "user123@example.com";
        String expected = "[EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }

    @Test
    void testScrub_EmailWithUpperCase_ReplacedCorrectly() {
        String input = "TEST@EXAMPLE.COM";
        String expected = "[EMAIL_HIDDEN]";
        assertEquals(expected, scrubber.scrub(input));
    }
}