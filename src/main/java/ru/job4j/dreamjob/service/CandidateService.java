package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

/**
 * Класс описывает бизнесс логику работы приложения с моделью Candidate.
 * Работа с хранилищем через сквозные вызовы классов персистенции.
 */
public class CandidateService {

    /**
     * singleton через final
     * Работа контроллеров с персистенцией идет через промежуточный
     * слой Service. POST_STORE - констатна для работы с PostStore дублируеться
     * что бы не связывать логику контроллеров и персистенции.
     */
    private static final CandidateStore CANDIDATE_STORE = CandidateStore.instOf();

    private static final CandidateService CANDIDATE_SERVICE = new CandidateService();

    /**
     * Доступ к singleton-экземпляру CandidateService
     * @return CandidateService
     */
    public static CandidateService instOf() {
        return CANDIDATE_SERVICE;
    }

    /**
     * Предоставляет все значения хранилища.
     * @return Collection<Candidate>
     */
    public Collection<Candidate> findAll() {
        return CANDIDATE_STORE.findAll();
    }

    /**
     * Создать candidate.
     * Добавить во внутренее хранилище.
     * @param candidate
     */
    public void create(Candidate candidate) {
        CANDIDATE_STORE.create(candidate);
    }

    /**
     * Найти post по id
     * @param id
     * @return Candidate
     */
    public Candidate findById(int id) {
        return CANDIDATE_STORE.findById(id);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param candidate
     */
    public void update(Candidate candidate) {
        CANDIDATE_STORE.update(candidate);
    }

}
