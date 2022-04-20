package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;

/**
 * Класс описывает бизнесс логику работы приложения с модель POST.
 * Работа с хранилищем через сквозные вызовы классов персистенции.
 */
public class PostService {

    /**
     * singleton через final
     * Работа контроллеров с персистенцией идет через промежуточный
     * слой Service. POST_STORE - констатна для работы с PostStore дублируеться
     * что бы не связывать логику контроллеров и персистенции.
      */
    private static final PostStore POST_STORE = PostStore.instOf();

    private static final PostService POST_SERVICE = new PostService();

    /**
     * Доступ к singleton-экземпляру PostService
     * @return PostService
     */
    public static PostService instOf() {
        return POST_SERVICE;
    }

    /**
     * Предоставляет все значения хранилища.
     * @return Collection<Post>
     */
    public Collection<Post> findAll() {
        return POST_STORE.findAll();
    }

    /**
     * Создать post.
     * Добавить во внутренее хранилище.
     * @param post
     */
    public void create(Post post) {
        POST_STORE.create(post);
    }

    /**
     * Найти post по id
     * @param id
     * @return Post
     */
    public Post findById(int id) {
        return POST_STORE.findById(id);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param post
     */
    public void update(Post post) {
        POST_STORE.update(post);
    }

}
