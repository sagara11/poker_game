package org.poker_game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockingTest {
    @Mock
    User user;

    @Test
    @DisplayName("Permission assigned successfully")
    void assignPermissions() {
        Mocking mocking = new Mocking();
        mocking.setUser(user);
        user.setRole("admin");
        user.setUsername("kunal");

        List<String> filteredPosts = new ArrayList<>();
        filteredPosts.add("Awesome Day");
        filteredPosts.add("This place is awesome");

        when(user.getAllPostsContainingWord("awesome")).thenReturn(filteredPosts);
        when(user.getRole()).thenReturn("admin");
        when(user.getUsername()).thenReturn("kunal");

        Assertions.assertEquals(1, mocking.assignPermission());
        Assertions.assertEquals(2, user.getAllPostsContainingWord("awesome").size());
    }
}