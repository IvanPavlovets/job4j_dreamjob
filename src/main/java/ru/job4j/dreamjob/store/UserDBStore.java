package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавляет User в бд.
     * На поле email ноложено ограничение UNIQUE,
     * при добавлении дубликата email в бд, возникнет
     * исключение и метод вернет пустой Optional.
     * @param user
     * @return Optional<User>
     */
    public Optional<User> add(User user) {
        String insertStmnt = "INSERT INTO users(email, name, password) VALUES (?,?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(insertStmnt, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Находит запись в БД по условию.
     * Возвращает найденую запись или пустой Optional.
     * @param email
     * @param password
     * @return Optional<User>
     */
    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        User user;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ?"
                     + " AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User(it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
