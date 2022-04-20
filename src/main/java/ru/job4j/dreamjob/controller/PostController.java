package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.PostService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контролер (Блок управления) обьектами Post
 */
@Controller
public class PostController {

    /**
     * Работа с PostStore через промежуточный слой PostService
     */
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Обрабатывает переход на posts.html
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * @return String
     */
    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    /**
     * Обрабатывает переход на addPost.html
     * @param model
     * @return String
     */
    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        model.addAttribute("post", new Post(0, "Заполните поле", "", date));
        return "addPost";
    }

    /**
     * Обрабатывает добавление данных в post
     * и их сохранение в store.
     * @param post
     * @return String
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postService.create(post);
        return "redirect:/posts";
    }

    /**
     * Обрабатывает переход на updatePost.html
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        return "updatePost";
    }

    /**
     * Сохраняет данные в post после редактирования.
     * @param post
     * @return String
     */
    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/posts";
    }
}
