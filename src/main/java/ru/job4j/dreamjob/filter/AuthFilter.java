package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс отвечает за то что только авторизованный
 * пользователь можно смотреть и добавлять вакансии
 * и кандидатов.
 */
@Component
public class AuthFilter implements Filter {

    /**
     * Через этот метод будут проходить запросы к сервлетам.
     * - Если запрос идет к адресам loginPage или login,
     * то мы их пропускаем сразу - возможность залогинеться.
     * - Если запросы идут к другим адресам, то проверяем
     * наличие пользователя в HttpSessio - тут только для авторизированых
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage") || uri.endsWith("login")
                || uri.endsWith("formAddUser") || uri.endsWith("registration")) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }
}
