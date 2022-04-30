package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {

    @Test
    public void whenCreateCandidateThenFindById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Petr", "descriotion", "30.04.2022", true, new byte[]{0});
        store.addCandidate(candidate);
        Candidate candidateStore = store.findCandidateById(candidate.getId());
        assertThat(candidateStore.getName(), is(candidate.getName()));
    }

    @Test
    public void whenCreateCandidateThenFindAll() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(1, "Ivan", "descriotion1", "30.04.2022", true, new byte[]{0});
        Candidate candidate2 = new Candidate(2, "Dima", "descriotion2", "30.04.2022", true, new byte[]{0});
        store.addCandidate(candidate1);
        store.addCandidate(candidate2);
        List<Candidate> expected = List.of(candidate1, candidate2);
        List<Candidate> aLLPosts = store.findAllCandidate();
        List<Candidate> result = aLLPosts.subList(aLLPosts.size() - 2, aLLPosts.size());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUpdateCandidateThenFindById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Mike", "descriotion", "30.04.2022", true, new byte[]{0});
        store.addCandidate(candidate);
        Candidate candidate2 = new Candidate(candidate.getId(), "111", "222", "01.05.2022", false, new byte[]{0});
        store.update(candidate2);
        Candidate postInDb = store.findCandidateById(candidate.getId());
        assertThat(postInDb, is(candidate2));
    }
}
