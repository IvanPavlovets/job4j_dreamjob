package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контролер (Блок управления) обьектами Post
 * контролер для работы с вакансиями.
 */
@Controller @ThreadSafe
public class PostController {
    /**
     * Работа с PostStore через промежуточный слой PostService
     */
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    /**
     * Обрабатывает переход на posts.html
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * Загружаем из текущей ссесии User и добовляем в Model
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @return String
     */
    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.findAllPosts());
        return "posts";
    }

    /**
     * Обрабатывает переход на addPost.html
     * Загружаем из текущей ссесии User и добовляем в Model
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @param model
     * @return String
     */
    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        model.addAttribute("post", new Post(0, "Заполните поле", "", date, false));
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    /**
     * Обрабатывает добавление данных в post
     * и их сохранение в store.
     * Города в обьекте post не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @param post
     * @return String
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        int id = post.getCity().getId();
        City city = cityService.findById(id);
        post.setCity(city);
        postService.create(post);
        return "redirect:/posts";
    }

    /**
     * Обрабатывает переход на updatePost.html
     * Загружаем из текущей ссесии User и добовляем в Model
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, HttpSession session,
                                 @PathVariable("postId") int id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("post", postService.findPostById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    /**
     * Сохраняет данные в post после редактирования.
     * Города в обьекте post не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @param post
     * @return String
     */
    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        int id = post.getCity().getId();
        City city = cityService.findById(id);
        post.setCity(city);
        postService.update(post);
        return "redirect:/posts";
    }
}
