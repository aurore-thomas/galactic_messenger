package main.controller;

import lombok.Getter;
import main.model.User;

import java.util.ArrayList;
import java.util.List;

public class ActiveUsers {
    @Getter
    private static List<User> activeUsers = new ArrayList<>();

    public static void addUser(User user) {
        activeUsers.add(user);
        System.out.println("User " + user.getUsername() + " logged in.");
    }

    public static void removeUser(User user) {
        activeUsers.remove(user);
        System.out.println("User " + user.getUsername() + " logged out.");
    }

    public static boolean isUserActive(User user) {
        if (activeUsers.contains(user)) {
            System.out.println("User " + user.getUsername() + " is active.");
        } else {
            System.out.println("User " + user.getUsername() + " is not active.");
        }
        return activeUsers.contains(user);
    }
}
