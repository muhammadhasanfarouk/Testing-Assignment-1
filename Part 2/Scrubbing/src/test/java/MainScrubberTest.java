import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Models.ScrubMode;
import Services.MainScrubber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class MainScrubberTest {

    @Mock private IScrubDigits digitMock;
    @Mock private IScrubEmails emailMock;

    private MainScrubber mainScrubber;

    @BeforeEach
    void setUp() {
        mainScrubber = new MainScrubber(digitMock, emailMock);
    }

    //  Null Input Tests

    @Test
    void testScrub_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mainScrubber.scrub(null, ScrubMode.FULL_SCRUBBING));
    }
    // blank input tests
    @Test
    void testScrub_emptyInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> mainScrubber.scrub("", ScrubMode.FULL_SCRUBBING));
    }
    @Test
    void testScrub_whitespaceInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> mainScrubber.scrub("   ", ScrubMode.FULL_SCRUBBING));
    }
    @Test
    void testScrub_tabsAndNewlinesInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> mainScrubber.scrub("\n\t", ScrubMode.FULL_SCRUBBING));
    }

    //  Happy Paths

    @Test
    void testScrub_FullScrubbing_CallsBothDependencies() {
        String input = "user@test.com 123";

        when(digitMock.scrub(input)).thenReturn("user@test.com XXX");
        when(emailMock.scrub("user@test.com XXX")).thenReturn("[EMAIL_HIDDEN] XXX");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXX", result);
        verify(digitMock).scrub(input);
        verify(emailMock).scrub("user@test.com XXX");
        verifyNoMoreInteractions(digitMock, emailMock);
    }

    @Test
    void testScrub_fullScrubbing_withMultipleSensitiveData() {
        String input = "user@test.com 123 another@test.com 456";

        when(digitMock.scrub(input))
                .thenReturn("user@test.com XXX another@test.com XXX");

        when(emailMock.scrub("user@test.com XXX another@test.com XXX"))
                .thenReturn("[EMAIL_HIDDEN] XXX [EMAIL_HIDDEN] XXX");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXX [EMAIL_HIDDEN] XXX", result);

        verify(digitMock).scrub(input);
        verify(emailMock).scrub("user@test.com XXX another@test.com XXX");

        verifyNoMoreInteractions(digitMock, emailMock);
    }


    //  ONLY DIGITS 

    @Test
    void testScrub_OnlyDigits_DoesNotCallEmailScrubber() {
        String input = "123";
        when(digitMock.scrub(input)).thenReturn("XXX");

        String result = mainScrubber.scrub(input, ScrubMode.ONLY_DIGITS);

        assertEquals("XXX", result);

        verify(digitMock).scrub(input);
        verifyNoInteractions(emailMock);
        verifyNoMoreInteractions(digitMock);
    }

    //  ONLY EMAILS 

    @Test
    void testScrub_OnlyEmails_DoesNotCallDigitScrubber() {
        String input = "user@test.com";
        when(emailMock.scrub(input)).thenReturn("[EMAIL_HIDDEN]");

        String result = mainScrubber.scrub(input, ScrubMode.ONLY_EMAILS);

        assertEquals("[EMAIL_HIDDEN]", result);

        verify(emailMock).scrub(input);
        verifyNoInteractions(digitMock);
        verifyNoMoreInteractions(emailMock);
    }

    //  Edge Case 

    @Test
    void testScrub_fullScrubbing_whenNoChanges_returnsSameString() {
        String input = "hello";

        when(digitMock.scrub(input)).thenReturn("hello");
        when(emailMock.scrub("hello")).thenReturn("hello");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("hello", result);

        verify(digitMock).scrub(input);
        verify(emailMock).scrub("hello");
        verifyNoMoreInteractions(digitMock, emailMock);
    }

    @Test
    void testScrub_fullScrubbing_emailWithDigits() {
        String input = "user123@test.com";

        when(digitMock.scrub(input)).thenReturn("userXXX@test.com");
        when(emailMock.scrub("userXXX@test.com")).thenReturn("[EMAIL_HIDDEN]");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN]", result);

        verify(digitMock).scrub(input);
        verify(emailMock).scrub("userXXX@test.com");
        verifyNoMoreInteractions(digitMock, emailMock);
    }
}