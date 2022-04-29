package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Candidate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {

    /**
     * Внутри создаются коннекты к базе данных,
     * которые находятся в многопоточной очереди
     */
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Достает все значения из хранилища (БД)
     * добавляються обьекты городов с полем id,
     * название городов добавляется в слое сервисов.
     * @return List<Candidate>
     */
    public List<Candidate> findAllCandidate() {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidate = new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("descr"),
                            it.getString("created"),
                            it.getBoolean("visible"),
                            it.getBytes("photo")
                    );
                    candidate.setCity(new City(it.getInt("city_id")));
                    candidates.add(candidate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    /**
     * Создание кандидата.
     * Метод добавляет post во БД
     * ps.getGeneratedKeys() - получаем все сгененрированые ключи
     * в цикле проходим по ResultSet и получаем ключ необходимой колонки
     * @param post
     * @return Post
     */
    public Candidate addCandidate(Candidate candidate) {
        String insertStmnt = "INSERT INTO candidate(name, descr, created, visible, city_id, photo) VALUES (?,?,?,?,?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(insertStmnt, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescr());
            ps.setString(3, candidate.getCreated());
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCity().getId());
            ps.setBytes(6, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого post.
     * @param post
     */
    public void update(Candidate candidate) {
        String updateQuery = "UPDATE candidate SET name = ?, descr = ?, created = ?, visible = ?, city_id = ?, photo = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(updateQuery)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescr());
            ps.setString(3, candidate.getCreated());
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCity().getId());
            ps.setBytes(6, candidate.getPhoto());
            ps.setInt(7, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Находит запись в БД по id
     * и формирует на основе этой
     * записи новый обьект Candidate.
     * @param id
     * @return Candidate
     */
    public Candidate findCandidateById(int id) {
        Candidate candidate;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    candidate = new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("descr"),
                            it.getString("created"),
                            it.getBoolean("visible"),
                            it.getBytes("photo")
                    );
                    candidate.setCity(new City(it.getInt("city_id")));
                    return candidate;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
