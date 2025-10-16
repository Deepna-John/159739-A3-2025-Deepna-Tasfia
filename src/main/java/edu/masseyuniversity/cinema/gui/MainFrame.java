package edu.masseyuniversity.cinema.gui;

import javax.swing.*;
import java.awt.*;
import edu.masseyuniversity.cinema.repository.MovieRepository;
import edu.masseyuniversity.cinema.service.AuthService;

public class MainFrame extends JFrame {
    private final MovieRepository repo;
    private final AuthService auth;

    public MainFrame(MovieRepository repo) {
        this.repo = repo;
        this.auth = new AuthService();

        setTitle("Cinema Ticket Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        showLoginPanel();
        setVisible(true);
    }

    public void showLoginPanel() {
        setContentPane(new LoginPanel(this, auth, repo));
        revalidate();
        repaint();
    }

    public void showBrowsePanel(String role) {
        setContentPane(new BrowsePanel(this, repo, role));
        revalidate();
        repaint();
    }

    public void showManagePanel() {
        setContentPane(new ManagePanel(this, repo));
        revalidate();
        repaint();
    }
}
