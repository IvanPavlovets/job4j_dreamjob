package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostControllerTest {
    /**
     * проверка метода PostController.posts()
     * - возвращает все обьекты post.
     * В тесте создаем mock-обьекты на все зависимсоти:
     * - postService, cityService, session
     */
    @Test
    public void whenPosts() {
        List<Post> posts1 = Arrays.asList(
                new Post(1, "Java Job1", "descriotion1", "25.05.2022", true),
                new Post(2, "Java Job2", "descriotion2", "25.05.2022", true)
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);

        when(postService.findAllPosts()).thenReturn(posts1);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts1);
        assertThat(page, is("posts"));
    }

    /**
     * 1) Тестируем метод addPost у PostController - метод должен
     * вернуть страницу "addPost" - проверяем сделав вызов у
     * собраного из mock-обектов PostController.
     * 2) Также проверяем что список городов cities не изменился
     * при вызове addPost().
     * 3) В тесте создаем mock-обьекты на все зависимсоти.
     */
    @Test
    public void whenAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Красноярск")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);

        when(cityService.getAllCities()).thenReturn(cities);

        PostController postController = new PostController(postService, cityService);

        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    /**
     * 1) Проверяем метод formUpdatePost у PostController - метод
     * должен вернуть страницу "updatePost" - проверяем сделав
     * вызов у собранного из mock-обектов PostController.
     * 2) Также проверяем что список городов cities не изменился
     * при вызове formUpdatePost().
     * 3) В отличии от addPost тут присутсвете бизнссс логика по
     * получению обьекта post - postService.findPostById(id),
     * проверяем ее в when() и verify().
     * 4) В тесте создаем mock-обьекты на все зависимсоти.
     */
    @Test
    public void whenFormUpdatePost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Красноярск")
        );
        Post post =  new Post(1, "Java Job1", "descriotion1", "25.05.2022", true);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);

        when(cityService.getAllCities()).thenReturn(cities);
        when(postService.findPostById(anyInt())).thenReturn(post);

        PostController postController = new PostController(postService, cityService);

        String page = postController.formUpdatePost(model, session, post.getId());
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("post", post);
        assertThat(page, is("updatePost"));
    }

    /**
     * Проверяем метод создания вакансии.
     * В методе нет посторонних вызывов -
     * нет необходимости в when, просто
     * проверяем вызов create() и сверяем
     * с возвращеной строкой.
     */
    @Test
    public void whenCreatePost() {
        Post post =  new Post(1, "Java Job1", "descriotion1", "25.05.2022", true);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(post);
        verify(postService).create(post);
        assertThat(page, is("redirect:/posts"));
    }

    /**
     * Аналогичен addPost().
     * Проверяем метод изменения вакансии
     * В методе нет посторонних вызывов -
     * нет необходимости в when, просто
     * проверяем вызов update() и сверяем
     * с возвращеной строкой.
     */
    @Test
    public void whenUpdate() {
        Post post =  new Post(1, "Java Job1", "descriotion1", "25.05.2022", true);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);

        String page = postController.updatePost(post);
        verify(postService).update(post);
        assertThat(page, is("redirect:/posts"));
    }
}
