package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Модель данных.
 * Класс хранилище кандидатов.
 * Синглтон.
 */
public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public CandidateStore() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        candidates.put(1, new Candidate(1, "Carey Mahoney", "description of Mahoney", date));
        candidates.put(2, new Candidate(2, "Moses Hightower", "description of Hightower", date));
        candidates.put(3, new Candidate(3, "Eugene Tackleberry", "description of Tackleberry", date));
    }

    /**
     * Геттер экземпляра класса
     * @return CandidateStore
     */
    public static CandidateStore instOf() {
        return INST;
    }

    /**
     * Геттер всех значений хранилища
     * @return Collection<Candidate>
     */
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
