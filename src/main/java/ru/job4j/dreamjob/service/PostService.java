package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Класс описывает бизнесс логику работы приложения с модель POST.
 * Работа с хранилищем через сквозные вызовы классов персистенции.
 */
@Service @ThreadSafe
public class PostService {

    private AtomicReference<String> date = new AtomicReference<>(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

    /**
     * Работа с БД через слой персистенции.
     */
    private final PostDBStore postDBStore;
    /**
     * Сервис городов для установки в обьекты post
     */
    private final CityService cityService;

    public PostService(PostDBStore postDBStore, CityService cityService) {
        this.postDBStore = postDBStore;
        this.cityService = cityService;
    }

    /**
     * Предоставляет все значения хранилища.
     * В кагдое предаставленое значение Post
     * устанавливаем свой город по id.
     * @return List<Post>
     */
    public List<Post> findAllPosts() {
        List<Post> posts = postDBStore.findAllPosts();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId()))
        );
        return posts;
    }

    /**
     * Создать post.
     * Добавить во внутренее хранилище.
     * @param post
     */
    public void create(Post post) {
        post.setCreated(date.get());
        postDBStore.addPost(post);
    }

    /**
     * Найти post по id
     * @param id
     * @return Post
     */
    public Post findPostById(int id) {
        return postDBStore.findPostById(id);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param post
     */
    public void update(Post post) {
        postDBStore.update(post);
    }

}
