import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Models.ScrubMode;
import Services.MainScrubber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MainScrubberTest {

    @Mock private IScrubDigits digitMock;
    @Mock private IScrubEmails emailMock;

    @InjectMocks private MainScrubber mainScrubber;

    @Test
    void testScrub_FullScrubbing_CallsBothDependencies() {
        String input = "user@test.com 123";
        when(digitMock.scrub(input)).thenReturn("user@test.com XXX");
        when(emailMock.scrub("user@test.com XXX")).thenReturn("[EMAIL_HIDDEN] XXX");

        String result = mainScrubber.scrub(input, ScrubMode.FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXX", result);

        verify(digitMock, times(1)).scrub(input);
        verify(emailMock, times(1)).scrub("user@test.com XXX");
    }

    @Test
    void testScrub_OnlyDigits_DoesNotCallEmailScrubber() {
        String input = "123";
        mainScrubber.scrub(input, ScrubMode.ONLY_DIGITS);

        verify(digitMock, times(1)).scrub(input);
        verifyNoInteractions(emailMock);
    }

    @Test
    void testScrub_OnlyMail_DoesNotCallDigitScrubber() {
        String input = "user@test.com";
        mainScrubber.scrub(input, ScrubMode.ONLY_EMAILS);

        verify(emailMock, times(1)).scrub(input);
        verifyNoInteractions(digitMock);
    }
}