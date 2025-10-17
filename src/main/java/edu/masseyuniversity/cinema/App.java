package edu.masseyuniversity.cinema;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point for Cinema Ticket Management System
 * 
 * @author Deepna & Tasfia
 * @version 1.0
 */
public class App {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                CinemaSystem system = new CinemaSystem();
                system.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to start Cinema System: " + e.getMessage());
            }
        });
    }
}