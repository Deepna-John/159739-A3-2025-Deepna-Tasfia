package edu.masseyuniversity.cinema;

import edu.masseyuniversity.cinema.controller.CinemaController;
import edu.masseyuniversity.cinema.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Simple unit tests for CinemaController.
 * Checks core movie operations such as add, update, delete, and search.
 */
public class CinemaControllerTest {
    private CinemaController controller;

    /**
     * Set up a new CinemaController and add sample movies before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new CinemaController();
        controller.addMovie(new ActionMovie("A001", "Fast & Furious", "Justin Lin", 143, 15.5, "18:30", "High", 50));
        controller.addMovie(new ComedyMovie("C001", "Mr. Bean", "Steve Bendelack", 90, 12.0, "14:00", 50));
        controller.addMovie(new RomanceMovie("R001", "Titanic", "James Cameron", 195, 18.0, "20:00", "R13", 50));
    }

    /**
     * Check if all movies are loaded correctly.
     */
    @Test
    void testGetAllMovies() {
        assertEquals(3, controller.getAllMovies().size());
    }

    /**
     * Check if search by category returns correct movies.
     */
    @Test
    void testSearchByCategory() {
        List<Movie> actionMovies = controller.searchByCategory("Action");
        assertEquals(1, actionMovies.size());
    }

    /**
     * Check if search by category and title works correctly.
     */
    @Test
    void testSearchByCategoryAndTitle() {
        List<Movie> results = controller.searchByCategoryAndTitle("Action", "Fast");
        assertEquals(1, results.size());
    }

    /**
     * Check if finding a movie by ID works correctly.
     */
    @Test
    void testFindMovieById() {
        Movie movie = controller.findMovieById("A001");
        assertNotNull(movie);
        assertEquals("Fast & Furious", movie.getTitle());
    }

    /**
     * Check if adding a new movie increases the list size.
     */
    @Test
    void testAddMovie() {
        Movie newMovie = new ActionMovie("A999", "New Movie", "Director", 120, 15.0, "19:00", "Medium", 50);
        controller.addMovie(newMovie);
        assertEquals(4, controller.getAllMovies().size());
    }

    /**
     * Check if adding a movie with a duplicate ID throws an exception.
     */
    @Test
    void testAddMovieDuplicateId() {
        Movie duplicate = new ActionMovie("A001", "Duplicate", "Director", 120, 15.0, "19:00", "Medium", 50);
        assertThrows(IllegalArgumentException.class, () -> controller.addMovie(duplicate));
    }

    /**
     * Check if updating a movie changes its title correctly.
     */
    @Test
    void testUpdateMovie() {
        Movie movie = controller.findMovieById("A001");
        movie.setTitle("Updated Title");
        controller.updateMovie(movie);
        assertEquals("Updated Title", controller.findMovieById("A001").getTitle());
    }

    /**
     * Check if deleting a movie removes it from the list.
     */
    @Test
    void testDeleteMovie() {
        controller.deleteMovie("A001");
        assertEquals(2, controller.getAllMovies().size());
        assertNull(controller.findMovieById("A001"));
    }

    /**
     * Check if selling a ticket decreases available tickets by one.
     */
    @Test
    void testSellTicket() {
        Movie movie = controller.findMovieById("A001");
        int initialTickets = movie.getAvailableTickets();
        controller.sellTicket("A001");
        assertEquals(initialTickets - 1, movie.getAvailableTickets());
    }

    /**
     * Check if selling a ticket for a non-existing movie throws an exception.
     */
    @Test
    void testSellTicketMovieNotFound() {
        assertThrows(IllegalStateException.class, () -> controller.sellTicket("XXX"));
    }
}
