package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

/**
 * Класс описывает бизнесс логику работы приложения с моделью Candidate.
 * Работа с хранилищем через сквозные вызовы классов персистенции.
 */
@Service
public class CandidateService {

    private final CandidateStore store;

    public CandidateService(CandidateStore store) {
        this.store = store;
    }

    /**
     * Предоставляет все значения хранилища.
     * @return Collection<Candidate>
     */
    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    /**
     * Создать candidate.
     * Добавить во внутренее хранилище.
     * @param candidate
     */
    public void create(Candidate candidate) {
        store.create(candidate);
    }

    /**
     * Найти post по id
     * @param id
     * @return Candidate
     */
    public Candidate findById(int id) {
        return store.findById(id);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param candidate
     */
    public void update(Candidate candidate) {
        store.update(candidate);
    }

}
