import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class VerificationWithMatchersExample {

    @Test
    void testVerifyWithArgumentMatchers() {
        // Create a mock list
        List<String> mockedList = mock(List.class);

        // Perform some operations on the mock
        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("three");

        // Verify that add() was called with "one" and any string
//        verify(mockedList).add("one");
//        verify(mockedList, times(1)).add(anyString()); // matches any single call with a String argument
        verify(mockedList, times(3)).add(any(String.class)); // verifies exactly three calls with any string
    }
}
