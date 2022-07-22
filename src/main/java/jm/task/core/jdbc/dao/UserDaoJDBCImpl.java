package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String tableName = "users";
    private static final String tableField1 = "id";
    private static final String tableField2 = "name";
    private static final String tableField3 = "lastname";
    private static final String tableField4 = "age";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE " + tableName + " ("
                + tableField1 + " INT PRIMARY KEY AUTO_INCREMENT, "
                + tableField2 + " VARCHAR(255) NOT NULL, "
                + tableField3 + " VARCHAR(255) NOT NULL, "
                + tableField4 + " INT NOT NULL"
                + ")";
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже существует");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE " + tableName;
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы с таким именем не существует");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + tableName + "(" + tableField2 + "," + tableField3 + "," + tableField4 + ")"
                + "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setString(3, String.valueOf(age));
            prSt.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + tableName + " WHERE " + tableField1 + "=" + id;
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ResultSet resSet = null;
        String query = "SELECT * FROM " + tableName;
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<User> usersList = new ArrayList<>();

        try {
            while (resSet.next()) {
                usersList.add(new User(resSet.getInt(tableField1),
                        resSet.getString(tableField2),
                        resSet.getString(tableField3),
                        resSet.getByte(tableField4)));
            }
        } catch (SQLException e) {
            return null;
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE " + tableName;
        try {
            PreparedStatement prSt = Util.getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы с таким именем не существует");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
