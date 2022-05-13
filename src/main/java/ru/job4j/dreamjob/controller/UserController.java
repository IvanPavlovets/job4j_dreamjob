package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * загружает страницу addUser.html.
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @return String
     */
    @GetMapping("/formAddUser")
    public String addUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", new User());
        return "addUser";
    }

    /**
     * Обрабатывает добавление данных
     * из полеей ввода в обьект user и
     * и последующеее сохранение в UserDB.
     * В условии проверка на пустой Optional
     * - При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на formAddUser.
     * @param model
     * @param user
     * @return String
     */
    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/loginPage";
    }

    /**
     * Загружает страницу login.html
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @param fail
     * @return String
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Метод авторизации.
     * Делает обработку действий на странице login.html
     * В условии проверка на пустой Optional.
     * Из HttpServletRequest получаем обьект
     * HttpSession - текущая ссесия в данном браузере,
     * внутри используется ConcurrentHashMap, в котором
     * можно хранить текущего user, добовляем его в map
     * с помощью setAttribute().
     * При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на loginPage.
     * @param user
     * @param req
     * @return String
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Обработки нажатия ссылки "Выход"
     * Удаляет все данные связанные с текущем пользователем
     * и завершает текущую сессию.
     * @param session
     * @return String
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

}
