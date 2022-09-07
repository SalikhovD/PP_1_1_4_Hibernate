package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String TABLE_NAME = "user";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String LASTNAME_FIELD = "lastname";
    private static final String AGE_FIELD = "age";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_FIELD + " INT PRIMARY KEY AUTO_INCREMENT, "
                + NAME_FIELD + " VARCHAR(255) NOT NULL, "
                + LASTNAME_FIELD + " VARCHAR(255) NOT NULL, "
                + AGE_FIELD + " INT NOT NULL"
                + ")").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS " + TABLE_NAME).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        User user = new User(name, lastName, age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
    }
}
