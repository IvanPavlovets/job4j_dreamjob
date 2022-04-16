package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контролер (Блок управления) обьектами Post
 */
@Controller
public class PostController {

    private final PostStore store = PostStore.instOf();

    /**
     * Обрабатывает переход на posts.html
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * @return String
     */
    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", store.findAll());
        return "posts";
    }

    /**
     * Обрабатывает переход на addPost.html
     * @param model
     * @return
     */
    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        model.addAttribute("post", new Post(0, "Заполните поле", "", date));
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        store.add(post);
        return "redirect:/posts";
    }
}
