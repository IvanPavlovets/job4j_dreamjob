package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Модель данных.
 * Класс хранилище вакансий.
 */
public class PostStore {
    private static final PostStore INST = new PostStore();
    private AtomicInteger count = new AtomicInteger();

    /**
     * Внутреняя карта для работы с многопоточностью.
     */
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    public PostStore() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        posts.put(1, new Post(1, "Junior Java Job", "desc Junior", date));
        posts.put(2, new Post(2, "Middle Java Job", "desc Middle", date));
        posts.put(3, new Post(3, "Senior Java Job", "desc Senior", date));
    }

    /**
     * Геттер экземпляра класса.
     * @return PostStore
     */
    public static PostStore instOf() {
        return INST;
    }

    /**
     * Геттер всех значений хранилища
     * @return Collection<Post>
     */
    public Collection<Post> findAll() {
        return posts.values();
    }

    /**
     * Метод добавляет post во внутрение
     * хранилище posts(map) по ключу.
     * @param post
     */
    public void add(Post post) {
        post.setId(count.getAndIncrement());
        posts.put(post.getId(), post);
    }
}
