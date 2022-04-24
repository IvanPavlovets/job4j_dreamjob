package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контролер (Блок управления) обьектами Candidate
 */
@Controller @ThreadSafe
public class CandidateController {

    /**
     * Работа с CandidateStore через промежуточный слой CandidateService
     */
    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    /**
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде (View).
     * @return String
     */
    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
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
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    /**
     * Обрабатывает добавление данных в candidate
     * и их сохранение в store.
     * Города в обьекте candidate не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @param candidate
     * @return String
     */
    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        int id = candidate.getCity().getId();
        City city = cityService.findById(id);
        candidate.setCity(city);
        candidateService.create(candidate);
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
        model.addAttribute("candidate", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    /**
     * Сохраняет данные в candidate после редактирования.
     * Города в обьекте candidate не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @param candidate
     * @return String
     */
    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        int id = candidate.getCity().getId();
        City city = cityService.findById(id);
        candidate.setCity(city);
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

}
