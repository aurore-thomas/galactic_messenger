package main.controller;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class ActiveUsers {
    @Getter
    private static List<String> activeUsers = new ArrayList<>();

    public static void addUser(String user) {
        activeUsers.add(user);
        System.out.println("User " + user + " logged in.");
    }

    public static void removeUser(String user) {
        activeUsers.remove(user);
        System.out.println("User " + user + " logged out.");
    }

    public static boolean isUserActive(String user) {
        if (activeUsers.contains(user)) {
            System.out.println("User " + user + " is active.");
        } else {
            System.out.println("User " + user + " is not active.");
        }
        return activeUsers.contains(user);
    }
}
