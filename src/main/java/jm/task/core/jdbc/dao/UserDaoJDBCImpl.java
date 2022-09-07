package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String TABLE_NAME = "user";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String LASTNAME_FIELD = "lastname";
    private static final String AGE_FIELD = "age";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_FIELD + " INT PRIMARY KEY AUTO_INCREMENT, "
                + NAME_FIELD + " VARCHAR(255) NOT NULL, "
                + LASTNAME_FIELD + " VARCHAR(255) NOT NULL, "
                + AGE_FIELD + " INT NOT NULL"
                + ")";
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже существует");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE " + TABLE_NAME;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы с таким именем не существует");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + TABLE_NAME + "(" + NAME_FIELD + "," + LASTNAME_FIELD + "," + AGE_FIELD + ")"
                + "VALUES(?,?,?)";
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setString(3, String.valueOf(age));
            prSt.executeUpdate();
            conn.commit();
            conn.close();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=" + id;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ResultSet resSet = null;
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }

        List<User> usersList = new ArrayList<>();

        try {
            while (resSet.next()) {
                usersList.add(new User(resSet.getInt(ID_FIELD),
                        resSet.getString(NAME_FIELD),
                        resSet.getString(LASTNAME_FIELD),
                        resSet.getByte(AGE_FIELD)));
            }
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE " + TABLE_NAME;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы с таким именем не существует");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
}
