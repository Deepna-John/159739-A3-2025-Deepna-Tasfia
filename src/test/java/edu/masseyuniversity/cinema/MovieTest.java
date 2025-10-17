package edu.masseyuniversity.cinema;

import edu.masseyuniversity.cinema.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Movie classes
 */
public class MovieTest {
    private ActionMovie actionMovie;
    private ComedyMovie comedyMovie;
    private RomanceMovie romanceMovie;
    private ScienceFictionMovie sciFiMovie;

    @BeforeEach
    void setUp() {
        actionMovie = new ActionMovie("A001", "Test Action", "Director A", 120, 15.0, "18:00", "High", 50);
        comedyMovie = new ComedyMovie("C001", "Test Comedy", "Director C", 90, 12.0, "14:00", 50);
        romanceMovie = new RomanceMovie("R001", "Test Romance", "Director R", 110, 14.0, "20:00", "PG", 50);
        sciFiMovie = new ScienceFictionMovie("S001", "Test SciFi", "Director S", 150, 18.0, "21:00", "IMAX", 50);
    }

    @Test
    void testMovieCreation() {
        assertEquals("A001", actionMovie.getMovieId());
        assertEquals("Test Action", actionMovie.getTitle());
        assertEquals(120, actionMovie.getDuration());
        assertEquals(50, actionMovie.getAvailableTickets());
    }

    @Test
    void testSellTicket() {
        assertEquals(50, actionMovie.getAvailableTickets());
        actionMovie.sellTicket();
        assertEquals(49, actionMovie.getAvailableTickets());
    }

    @Test
    void testSellTicketWhenNoneAvailable() {
        Movie movie = new ActionMovie("A999", "No Tickets", "Director", 120, 15.0, "18:00", "High", 0);
        assertThrows(IllegalStateException.class, movie::sellTicket);
    }

    @Test
    void testActionMovieStuntLevel() {
        assertEquals("High", actionMovie.getStuntLevel());
        actionMovie.setStuntLevel("Extreme");
        assertEquals("Extreme", actionMovie.getStuntLevel());
    }

    @Test
    void testRomanceMovieAgeRestriction() {
        assertEquals("PG", romanceMovie.getAgeRestriction());
    }

    @Test
    void testSciFiMovieFormat() {
        assertEquals("IMAX", sciFiMovie.getFormat());
    }

    @Test
    void testMovieCategory() {
        assertEquals("Action", actionMovie.getCategory());
        assertEquals("Comedy", comedyMovie.getCategory());
        assertEquals("Romance", romanceMovie.getCategory());
        assertEquals("ScienceFiction", sciFiMovie.getCategory());
    }

    @Test
    void testExtraAttribute() {
        assertEquals("High", actionMovie.getExtraAttribute());
        assertEquals("-", comedyMovie.getExtraAttribute());
        assertEquals("PG", romanceMovie.getExtraAttribute());
        assertEquals("IMAX", sciFiMovie.getExtraAttribute());
    }

    @Test
    void testMovieSetters() {
        actionMovie.setTitle("New Title");
        actionMovie.setDirector("New Director");
        assertEquals("New Title", actionMovie.getTitle());
        assertEquals("New Director", actionMovie.getDirector());
    }

    @Test
    void testToFileString() {
        String fileString = actionMovie.toFileString();
        assertTrue(fileString.contains("Action"));
        assertTrue(fileString.contains("A001"));
    }
}
