package edu.masseyuniversity.cinema.controller;

import edu.masseyuniversity.cinema.model.staff.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationController {
    private List<Staff> staffList;
    private Staff currentUser;

    public AuthenticationController() {
        staffList = new ArrayList<>();
        initializeStaff();
    }

    private void initializeStaff() {
        staffList.add(new TicketSeller("s1", "s1"));
        staffList.add(new TicketSeller("s2", "s2"));
        staffList.add(new TicketSeller("s3", "s3"));
        staffList.add(new Manager("m1", "m1"));
        staffList.add(new Manager("m2", "m2"));
    }

    public boolean login(String username, String password) {
        for (Staff staff : staffList) {
            if (staff.authenticate(username, password)) {
                currentUser = staff;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public Staff getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}