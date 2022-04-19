package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     * @return String
     */
    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        model.addAttribute("candidate", new Candidate(0, "Заполните поле", "", date));
        return "addCandidate";
    }

    /**
     * Обрабатывает добавление данных в candidate
     * и их сохранение в store.
     * @param candidate
     * @return String
     */
    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        store.create(candidate);
        return "redirect:/candidates";
    }

    /**
     * Обрабатывает переход на updateCandidate.html
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", store.findById(id));
        return "updateCandidate";
    }

    /**
     * Сохраняет данные в candidate после редактирования.
     * @param candidate
     * @return String
     */
    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        store.update(candidate);
        return "redirect:/candidates";
    }

}
