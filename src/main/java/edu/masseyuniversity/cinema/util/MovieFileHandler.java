package edu.masseyuniversity.cinema.util;

import edu.masseyuniversity.cinema.model.Movie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing movie data from/to files.
 * Demonstrates file I/O and exception handling.
 *
 * @author Deepna & Tasfia
 * @version 1.0
 */
public class MovieFileHandler {

    /**
     * Loads movies from a file.
     * Each line in the file should be formatted as:
     * Category, MovieID, Title, Director, Duration, Price, ShowTime, ExtraAttribute, AvailableTickets
     *
     * @param filename Name of the file to load (relative to project root)
     * @return List of Movie objects parsed from the file
     * @throws IOException if file cannot be read or doesn't exist
     */
    public static List<Movie> loadMovies(String filename) throws IOException {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    // Split by comma and parse
                    String[] parts = line.split(",");

                    // Use MovieFactory to create appropriate movie type
                    Movie movie = MovieFactory.createMovie(parts);
                    movies.add(movie);

                } catch (NumberFormatException e) {
                    // This must come before IllegalArgumentException since it’s a subclass
                    System.err.println("Invalid number format on line " + lineNumber + ": " + line);
                    System.err.println("Error details: " + e.getMessage());

                } catch (IllegalArgumentException e) {
                    // Catch any other argument-related issues
                    System.err.println("Error parsing line " + lineNumber + ": " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }

            System.out.println("✅ Successfully loaded " + movies.size() + " movies from " + filename);

        } catch (FileNotFoundException e) {
            throw new IOException(
                "❌ File not found: " + filename +
                "\nPlease ensure movies.txt is in the project root directory.", e
            );

        } catch (IOException e) {
            throw new IOException(
                "❌ Error reading file: " + filename +
                "\nDetails: " + e.getMessage(), e
            );
        }

        return movies;
    }

    /**
     * Exports movies to a file in the same format as movies.txt.
     *
     * @param movies List of movies to export
     * @param filename Name of the file to save to
     * @throws IOException if file cannot be written
     */
    public static void exportMovies(List<Movie> movies, String filename) throws IOException {
        if (movies == null || movies.isEmpty()) {
            throw new IllegalArgumentException("Cannot export empty movie list");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Movie movie : movies) {
                // Use Movie's toFileString() for consistent formatting
                writer.write(movie.toFileString());
                writer.newLine();
            }

            System.out.println("✅ Successfully exported " + movies.size() + " movies to " + filename);

        } catch (IOException e) {
            throw new IOException(
                "❌ Error writing to file: " + filename +
                "\nDetails: " + e.getMessage(), e
            );
        }
    }

    /**
     * Validates if a file exists and is readable.
     *
     * @param filename Name of the file to check
     * @return true if file exists and is readable, false otherwise
     */
    public static boolean isFileValid(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }

        File file = new File(filename);
        return file.exists() && file.isFile() && file.canRead();
    }
}
