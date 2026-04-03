import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Models.ScrubMode;
import Services.MainScrubber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class MainScrubberTest {

    @Mock private IScrubDigits digitMock;
    @Mock private IScrubEmails emailMock;

    @InjectMocks private MainScrubber mainScrubber;

    //  Exceptions 

    @Test
    void scrub_nullInput_throwsException() {
        assertThrows(NullPointerException.class,
                () -> mainScrubber.scrub(null, ScrubMode.FULL_SCRUBBING));
    }
    // blank input tests
    @Test
    void scrub_emptyInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> mainScrubber.scrub("", ScrubMode.FULL_SCRUBBING));
    }
    @Test
    void scrub_whitespaceInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> mainScrubber.scrub("   ", ScrubMode.FULL_SCRUBBING));
    }
    @Test
    void scrub_tabsAndNewlinesInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> mainScrubber.scrub("\n\t", ScrubMode.FULL_SCRUBBING));
    }

    @Test
    void testScrub_IllegalArgumentException_returnsNull() {
        String input = "test input";

        when(digitMock.scrub(input)).thenThrow(new IllegalArgumentException());

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertNull(result);

        verify(digitMock).scrub(input);
        verifyNoInteractions(emailMock);
    }

    @Test
    void testScrub_NullPointerException_returnsNull() {
        String input = "test input";

        when(digitMock.scrub(input)).thenThrow(new NullPointerException());

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertNull(result);

        verify(digitMock).scrub(input);
        verifyNoInteractions(emailMock);
    }

    //  Happy Paths

    @Test
    void scrub_fullScrubbing_callsBothInCorrectOrder_andReturnsCorrectResult() {
        String input = "user@test.com 123";

        when(digitMock.scrub(input)).thenReturn("user@test.com XXX");
        when(emailMock.scrub("user@test.com XXX")).thenReturn("[EMAIL_HIDDEN] XXX");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXX", result);

        verify(digitMock, times(1)).scrub(input);
        verify(emailMock, times(1)).scrub("user@test.com XXX");
    }

    @Test
    void scrub_fullScrubbing_withMultipleSensitiveData() {
        String input = "user@test.com 123 another@test.com 456";

        when(digitMock.scrub(input))
                .thenReturn("user@test.com XXX another@test.com XXX");

        when(emailMock.scrub("user@test.com XXX another@test.com XXX"))
                .thenReturn("[EMAIL_HIDDEN] XXX [EMAIL_HIDDEN] XXX");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXX [EMAIL_HIDDEN] XXX", result);

        InOrder inOrder = inOrder(digitMock, emailMock);
        inOrder.verify(digitMock).scrub(input);
        inOrder.verify(emailMock).scrub("user@test.com XXX another@test.com XXX");

        verifyNoMoreInteractions(digitMock, emailMock);
    }


    //  ONLY DIGITS 

    @Test
    void scrub_onlyDigits_callsOnlyDigitScrubber_andReturnsResult() {
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
    void scrub_onlyEmails_callsOnlyEmailScrubber_andReturnsResult() {
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
    void scrub_fullScrubbing_whenNoChanges_returnsSameString() {
        String input = "hello";

        when(digitMock.scrub(input)).thenReturn("hello");
        when(emailMock.scrub("hello")).thenReturn("hello");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("hello", result);

        verify(digitMock).scrub(input);
        verify(emailMock).scrub("hello");
    }

    @Test
    void scrub_fullScrubbing_emailWithDigits() {
        String input = "user123@test.com";

        when(digitMock.scrub(input)).thenReturn("userXXX@test.com");
        when(emailMock.scrub("userXXX@test.com")).thenReturn("[EMAIL_HIDDEN]");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN]", result);

        InOrder inOrder = inOrder(digitMock, emailMock);
        inOrder.verify(digitMock).scrub(input);
        inOrder.verify(emailMock).scrub("userXXX@test.com");
    }

    @Test
    void scrub_fullScrubbing_emptyAfterProcessing() {
        String input = "test@test.com";

        when(digitMock.scrub(input)).thenReturn("test@test.com");
        when(emailMock.scrub("test@test.com")).thenReturn("");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("", result);

        verify(digitMock).scrub(input);
        verify(emailMock).scrub("test@test.com");
    }
}