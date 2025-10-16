package edu.masseyuniversity.cinema.model.staff;

/**
 * Represents a Manager staff member.
 * Has all privileges including movie management (add, update, delete).
 * Demonstrates inheritance and polymorphism.
 */
public class Manager extends Staff {

    public Manager(String username, String password) {
        super(username, password, "Manager");
    }

    @Override
    public boolean canManageMovies() {
        return true;
    }
}