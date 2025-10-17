package edu.masseyuniversity.cinema;

import edu.masseyuniversity.cinema.controller.AuthenticationController;
import edu.masseyuniversity.cinema.model.staff.Staff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit tests for user authentication and role checking.
 */
public class AuthenticationTest {
    private AuthenticationController authController;

    /**
     * Set up a new AuthenticationController before each test.
     */
    @BeforeEach
    void setUp() {
        authController = new AuthenticationController();
    }

    /**
     * Check if a Ticket Seller can log in successfully.
     */
    @Test
    void testSuccessfulTicketSellerLogin() {
        assertTrue(authController.login("s1", "s1"));
        assertNotNull(authController.getCurrentUser());
        assertEquals("Ticket Seller", authController.getCurrentUser().getRole());
    }

    /**
     * Check if a Manager can log in successfully.
     */
    @Test
    void testSuccessfulManagerLogin() {
        assertTrue(authController.login("m1", "m1"));
        assertNotNull(authController.getCurrentUser());
        assertEquals("Manager", authController.getCurrentUser().getRole());
    }

    /**
     * Check login fails with wrong username or password.
     */
    @Test
    void testFailedLogin() {
        assertFalse(authController.login("wrong", "wrong"));
        assertNull(authController.getCurrentUser());
    }

    /**
     * Check logout works after a successful login.
     */
    @Test
    void testLogout() {
        authController.login("s1", "s1");
        assertTrue(authController.isLoggedIn());
        authController.logout();
        assertFalse(authController.isLoggedIn());
    }

    /**
     * Check that Ticket Sellers cannot manage movies.
     */
    @Test
    void testTicketSellerCannotManageMovies() {
        authController.login("s1", "s1");
        assertFalse(authController.getCurrentUser().canManageMovies());
    }

    /**
     * Check that Managers can manage movies.
     */
    @Test
    void testManagerCanManageMovies() {
        authController.login("m1", "m1");
        assertTrue(authController.getCurrentUser().canManageMovies());
    }

    /**
     * Check multiple login and logout actions work correctly.
     */
    @Test
    void testMultipleLogins() {
        assertTrue(authController.login("s2", "s2"));
        authController.logout();
        assertTrue(authController.login("m2", "m2"));
    }
}
