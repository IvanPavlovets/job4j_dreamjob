package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;

/**
 * Класс описывает бизнесс логику работы приложения с модель POST.
 * Работа с хранилищем через сквозные вызовы классов персистенции.
 */
@Service @ThreadSafe
public class PostService {

    /**
     * singleton через final
     * Работа контроллеров с персистенцией идет через промежуточный
     * слой Service. POST_STORE - констатна для работы с PostStore дублируеться
     * что бы не связывать логику контроллеров и персистенции.
      */
    private final PostStore postStore;

    public PostService(PostStore postStore) {
        this.postStore = postStore;
    }

    /**
     * Предоставляет все значения хранилища.
     * @return Collection<Post>
     */
    public Collection<Post> findAll() {
        return postStore.findAll();
    }

    /**
     * Создать post.
     * Добавить во внутренее хранилище.
     * @param post
     */
    public void create(Post post) {
        postStore.create(post);
    }

    /**
     * Найти post по id
     * @param id
     * @return Post
     */
    public Post findById(int id) {
        return postStore.findById(id);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param post
     */
    public void update(Post post) {
        postStore.update(post);
    }

}
