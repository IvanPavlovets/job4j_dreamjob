package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контролер (Блок управления) обьектами Candidate
 */
@Controller
public class CandidateController {

    private final CandidateStore store = CandidateStore.instOf();

    /**
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде (View).
     * @return String
     */
    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    /**
     * Обрабатывает переход на addCandidate.html
     * @param model
     * @return
     */
    @GetMapping("/formAddCandidate")
    public String addPost(Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        model.addAttribute("candidate", new Candidate(0, "Заполните поле", "", date));
        return "addCandidate";
    }
}
