package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Dmitry", "Salikhov", (byte) 25);
        userService.saveUser("Anna", "Ozhigova", (byte) 24);
        userService.saveUser("Artem", "Lebedev", (byte) 28);
        userService.saveUser("Rollands", "Maslo", (byte) 32);

        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
