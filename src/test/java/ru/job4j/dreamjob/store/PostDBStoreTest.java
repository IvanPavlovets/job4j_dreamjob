package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    @Test
    public void whenCreatePostThenFindById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "descriotion", "30.04.2022", true);
        store.addPost(post);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreatePostThenFindAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(1, "Java Job1", "descriotion1", "30.04.2022", true);
        Post post2 = new Post(2, "Java Job2", "descriotion2", "30.04.2022", true);
        store.addPost(post1);
        store.addPost(post2);
        List<Post> expected = List.of(post1, post2);
        List<Post> aLLPosts = store.findAllPosts();
        List<Post> result = aLLPosts.subList(aLLPosts.size() - 2, aLLPosts.size());
        assertThat(result, is(expected));
    }


    @Test
    public void whenUpdatePostThenFindById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "descriotion", "30.04.2022", true);
        store.addPost(post);
        Post post2 = new Post(post.getId(), "111", "222", "01.05.2022", false);
        store.update(post2);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb, is(post2));
    }
}
