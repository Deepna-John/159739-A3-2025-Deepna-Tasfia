package edu.masseyuniversity.cinema;

import edu.masseyuniversity.cinema.model.*;
import edu.masseyuniversity.cinema.model.staff.*;
import edu.masseyuniversity.cinema.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Cinema Management System GUI
 * Handles login, movie browsing, searching, booking, and management
 * 
 * @author Deepna & Tasfia
 * @version 1.0
 */
public class CinemaSystem extends JFrame {
    // Data storage
    private List<Movie> movies;
    private List<Staff> staffList;
    private Staff currentUser;

    // GUI Components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Login Panel Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    // Main Screen Components
    private JTabbedPane tabbedPane;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryComboBox;
    private JTextField searchField;
    private JLabel userLabel;
    
    // Manager Panel Components
    private JTextField idField, titleField, directorField, durationField;
    private JTextField priceField, showTimeField, extraField, ticketsField;
    private JComboBox<String> categoryComboManager;

    /**
     * Constructor - Initializes the system
     */
    public CinemaSystem() {
        initializeData();
        initializeGUI();
    }

    /**
     * Initialize staff and load movies from file
     */
    private void initializeData() {
        // Initialize staff members as per requirement
        staffList = new ArrayList<>();
        staffList.add(new TicketSeller("s1", "s1"));
        staffList.add(new TicketSeller("s2", "s2"));
        staffList.add(new TicketSeller("s3", "s3"));
        staffList.add(new Manager("m1", "m1"));
        staffList.add(new Manager("m2", "m2"));

        // Load movies from file
        try {
            movies = MovieFileHandler.loadMovies("movies.txt");
            System.out.println("Successfully loaded " + movies.size() + " movies");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading movies.txt: " + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE);
            movies = new ArrayList<>();
        }
    }

    /**
     * Initialize GUI components
     */
    private void initializeGUI() {
        setTitle("Cinema Ticket Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMainPanel(), "MAIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    /**
     * Create login panel
     */
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Cinema Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setPreferredSize(new Dimension(120, 30));
        loginBtn.addActionListener(e -> handleLogin());
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        // Enter key support
        passwordField.addActionListener(e -> handleLogin());

        return panel;
    }

    /**
     * Handle login action
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Authenticate
        currentUser = null;
        for (Staff staff : staffList) {
            if (staff.authenticate(username, password)) {
                currentUser = staff;
                break;
            }
        }

        if (currentUser != null) {
            userLabel.setText("Logged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
            updateGUIForUser();
            cardLayout.show(mainPanel, "MAIN");
            usernameField.setText("");
            passwordField.setText("");
            refreshMovieTable();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Create main panel with tabs
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel with user info and logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        userLabel = new JLabel("Logged in as: Guest");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(userLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        topPanel.add(logoutBtn, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Browse Movies", createBrowsePanel());
        tabbedPane.addTab("Check/Update Movies", createManagePanel());

        panel.add(tabbedPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create browse panel for viewing and searching movies
     */
    private JPanel createBrowsePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Category:"));
        
        categoryComboBox = new JComboBox<>(new String[]{
            "All", "Action", "Comedy", "Romance", "Science Fiction"
        });
        searchPanel.add(categoryComboBox);

        searchPanel.add(new JLabel("Title:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> performSearch());
        searchPanel.add(searchBtn);

        JButton refreshBtn = new JButton("Show All");
        refreshBtn.addActionListener(e -> refreshMovieTable());
        searchPanel.add(refreshBtn);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Movie table
        String[] columns = {"ID", "Title", "Director", "Duration", "Price", "Show Time", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        movieTable = new JTable(tableModel);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(movieTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton detailsBtn = new JButton("View Details");
        detailsBtn.addActionListener(e -> showMovieDetails());
        buttonPanel.add(detailsBtn);

        JButton bookBtn = new JButton("Book Ticket");
        bookBtn.addActionListener(e -> bookTicket());
        buttonPanel.add(bookBtn);

        JButton exportBtn = new JButton("Export Movies");
        exportBtn.addActionListener(e -> exportMovies());
        buttonPanel.add(exportBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Create manage panel for adding/updating/deleting movies (Manager only)
     */
    private JPanel createManagePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fields
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryComboManager = new JComboBox<>(new String[]{
            "Action", "Comedy", "Romance", "Science Fiction"
        });
        formPanel.add(categoryComboManager, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Movie ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        formPanel.add(idField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(15);
        formPanel.add(titleField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        directorField = new JTextField(15);
        formPanel.add(directorField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Duration (min):"), gbc);
        gbc.gridx = 1;
        durationField = new JTextField(15);
        formPanel.add(durationField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Price ($):"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(15);
        formPanel.add(priceField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Show Time:"), gbc);
        gbc.gridx = 1;
        showTimeField = new JTextField(15);
        formPanel.add(showTimeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Extra Attribute:"), gbc);
        gbc.gridx = 1;
        extraField = new JTextField(15);
        formPanel.add(extraField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Available Tickets:"), gbc);
        gbc.gridx = 1;
        ticketsField = new JTextField(15);
        formPanel.add(ticketsField, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> addMovie());
        buttonPanel.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> updateMovie());
        buttonPanel.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteMovie());
        buttonPanel.add(deleteBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearFields());
        buttonPanel.add(clearBtn);

        JButton loadBtn = new JButton("Load Selected");
        loadBtn.addActionListener(e -> loadSelectedMovie());
        buttonPanel.add(loadBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Update GUI based on user role
     */
    private void updateGUIForUser() {
        if (currentUser != null && !currentUser.canManageMovies()) {
            // Disable manage tab for ticket sellers
            tabbedPane.setEnabledAt(1, false);
        } else {
            tabbedPane.setEnabledAt(1, true);
        }
    }

    /**
     * Refresh movie table with all movies
     */
    private void refreshMovieTable() {
        tableModel.setRowCount(0);
        for (Movie movie : movies) {
            Object[] row = {
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getDuration() + " min",
                "$" + String.format("%.2f", movie.getTicketPrice()),
                movie.getShowTime(),
                movie.getAvailableTickets()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Perform search based on category and title
     */
    private void performSearch() {
        String category = (String) categoryComboBox.getSelectedItem();
        String searchText = searchField.getText().trim().toLowerCase();

        List<Movie> filtered = movies.stream()
            .filter(m -> {
                boolean matchCategory = category.equals("All") || 
                    m.getCategory().equalsIgnoreCase(category) ||
                    (category.equals("Science Fiction") && m.getCategory().equals("ScienceFiction"));
                boolean matchTitle = searchText.isEmpty() || 
                    m.getTitle().toLowerCase().contains(searchText);
                return matchCategory && matchTitle;
            })
            .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (Movie movie : filtered) {
            Object[] row = {
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getDuration() + " min",
                "$" + String.format("%.2f", movie.getTicketPrice()),
                movie.getShowTime(),
                movie.getAvailableTickets()
            };
            tableModel.addRow(row);
        }

        if (filtered.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No movies found matching your search criteria",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Show detailed information about selected movie
     */
    private void showMovieDetails() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a movie first",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movieId = (String) tableModel.getValueAt(selectedRow, 0);
        Movie movie = findMovieById(movieId);

        if (movie != null) {
            String details = String.format(
                "Movie ID: %s\n" +
                "Title: %s\n" +
                "Director: %s\n" +
                "Duration: %d minutes\n" +
                "Price: $%.2f\n" +
                "Show Time: %s\n" +
                "Category: %s\n" +
                "Extra Info: %s\n" +
                "Available Tickets: %d",
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getDuration(),
                movie.getTicketPrice(),
                movie.getShowTime(),
                movie.getCategory(),
                movie.getExtraAttribute(),
                movie.getAvailableTickets()
            );

            JOptionPane.showMessageDialog(this,
                details,
                "Movie Details",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Book a ticket for selected movie
     */
    private void bookTicket() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a movie first",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movieId = (String) tableModel.getValueAt(selectedRow, 0);
        Movie movie = findMovieById(movieId);

        if (movie != null) {
            try {
                movie.sellTicket();
                JOptionPane.showMessageDialog(this,
                    "Ticket booked successfully!\n" +
                    "Movie: " + movie.getTitle() + "\n" +
                    "Remaining tickets: " + movie.getAvailableTickets(),
                    "Booking Successful",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshMovieTable();
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Booking Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Export movies to file
     */
    private void exportMovies() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Movies");
        fileChooser.setSelectedFile(new java.io.File("movies_exported.txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                MovieFileHandler.exportMovies(movies, filename);
                JOptionPane.showMessageDialog(this,
                    "Movies exported successfully to:\n" + filename,
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error exporting movies: " + e.getMessage(),
                    "Export Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Add a new movie (Manager only)
     */
    private void addMovie() {
        if (!validateManagerAccess()) return;

        try {
            String movieId = idField.getText().trim();
            
            // Check if movie ID already exists
            if (findMovieById(movieId) != null) {
                JOptionPane.showMessageDialog(this,
                    "Movie ID already exists. Please use a unique ID.",
                    "Duplicate ID",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Movie movie = createMovieFromFields();
            movies.add(movie);
            
            JOptionPane.showMessageDialog(this,
                "Movie added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            refreshMovieTable();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error adding movie: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Update existing movie (Manager only)
     */
    private void updateMovie() {
        if (!validateManagerAccess()) return;

        try {
            String movieId = idField.getText().trim();
            Movie existingMovie = findMovieById(movieId);
            
            if (existingMovie == null) {
                JOptionPane.showMessageDialog(this,
                    "Movie ID not found",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update movie details (cannot change ID)
            existingMovie.setTitle(titleField.getText().trim());
            existingMovie.setDirector(directorField.getText().trim());
            existingMovie.setDuration(Integer.parseInt(durationField.getText().trim()));
            existingMovie.setTicketPrice(Double.parseDouble(priceField.getText().trim()));
            existingMovie.setShowTime(showTimeField.getText().trim());
            existingMovie.setExtraAttribute(extraField.getText().trim());
            existingMovie.setAvailableTickets(Integer.parseInt(ticketsField.getText().trim()));

            JOptionPane.showMessageDialog(this,
                "Movie updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            refreshMovieTable();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating movie: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete selected movie (Manager only)
     */
    private void deleteMovie() {
        if (!validateManagerAccess()) return;

        String movieId = idField.getText().trim();
        if (movieId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a Movie ID to delete",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie movie = findMovieById(movieId);
        if (movie == null) {
            JOptionPane.showMessageDialog(this,
                "Movie ID not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete:\n" + movie.getTitle() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            movies.remove(movie);
            JOptionPane.showMessageDialog(this,
                "Movie deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            refreshMovieTable();
        }
    }

    /**
     * Load selected movie into form fields
     */
    private void loadSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a movie from the Browse tab first",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movieId = (String) tableModel.getValueAt(selectedRow, 0);
        Movie movie = findMovieById(movieId);

        if (movie != null) {
            String category = movie.getCategory();
            if (category.equals("ScienceFiction")) {
                category = "Science Fiction";
            }
            categoryComboManager.setSelectedItem(category);
            
            idField.setText(movie.getMovieId());
            titleField.setText(movie.getTitle());
            directorField.setText(movie.getDirector());
            durationField.setText(String.valueOf(movie.getDuration()));
            priceField.setText(String.valueOf(movie.getTicketPrice()));
            showTimeField.setText(movie.getShowTime());
            extraField.setText(movie.getExtraAttribute());
            ticketsField.setText(String.valueOf(movie.getAvailableTickets()));
        }
    }

    /**
     * Clear all form fields
     */
    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        directorField.setText("");
        durationField.setText("");
        priceField.setText("");
        showTimeField.setText("");
        extraField.setText("");
        ticketsField.setText("");
    }

    /**
     * Create movie object from form fields
     */
    private Movie createMovieFromFields() throws IllegalArgumentException {
        String category = (String) categoryComboManager.getSelectedItem();
        String movieId = idField.getText().trim();
        String title = titleField.getText().trim();
        String director = directorField.getText().trim();
        int duration = Integer.parseInt(durationField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());
        String showTime = showTimeField.getText().trim();
        String extra = extraField.getText().trim();
        int tickets = Integer.parseInt(ticketsField.getText().trim());

        if (movieId.isEmpty() || title.isEmpty() || director.isEmpty() || showTime.isEmpty()) {
            throw new IllegalArgumentException("All required fields must be filled");
        }

        switch (category) {
            case "Action":
                return new ActionMovie(movieId, title, director, duration, price, showTime, extra, tickets);
            case "Comedy":
                return new ComedyMovie(movieId, title, director, duration, price, showTime, tickets);
            case "Romance":
                return new RomanceMovie(movieId, title, director, duration, price, showTime, extra, tickets);
            case "Science Fiction":
                return new ScienceFictionMovie(movieId, title, director, duration, price, showTime, extra, tickets);
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

    /**
     * Find movie by ID
     */
    private Movie findMovieById(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Validate manager access
     */
    private boolean validateManagerAccess() {
        if (currentUser == null || !currentUser.canManageMovies()) {
            JOptionPane.showMessageDialog(this,
                "Only managers can perform this action",
                "Access Denied",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Handle logout
     */
    private void handleLogout() {
        currentUser = null;
        cardLayout.show(mainPanel, "LOGIN");
        tabbedPane.setSelectedIndex(0);
    }
}