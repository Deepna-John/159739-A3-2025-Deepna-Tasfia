package edu.masseyuniversity.cinema;

import edu.masseyuniversity.cinema.gui.MainFrame;
import edu.masseyuniversity.cinema.repository.MovieRepository;

public class App {
    public static void main(String[] args) {
        // Create a movie repository (loads data from file or default list)
        MovieRepository repo = new MovieRepository();

        try {
            // Try loading movie data from file (if it exists)
            repo.loadFromFile("movies.txt");
        } catch (Exception e) {
            System.out.println("No movie file found. Starting with an empty list.");
        }

        // Launch the main GUI window
        javax.swing.SwingUtilities.invokeLater(() -> new MainFrame(repo));
    }
}
