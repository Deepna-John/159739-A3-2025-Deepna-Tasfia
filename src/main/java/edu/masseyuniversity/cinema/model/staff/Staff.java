package edu.masseyuniversity.cinema.model.staff;

/**
 * Abstract base class representing staff members in the cinema system.
 * Demonstrates encapsulation and provides foundation for staff inheritance hierarchy.
 */
public abstract class Staff {
    private String username;
    private String password;
    private String role;

    public Staff(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    /**
     * Validates credentials
     */
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Abstract method to check if staff can manage movies.
     * Demonstrates polymorphism.
     */
    public abstract boolean canManageMovies();

    @Override
    public String toString() {
        return role + ": " + username;
    }
}
