package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.store.CandidateStore;

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
}
